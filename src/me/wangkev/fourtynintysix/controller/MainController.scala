package me.wangkev.fourtynintysix.controller

import java.util.Observable
import javafx.event.EventHandler
import javafx.scene.input
import javafx.scene.input.{KeyCode, KeyEvent}

import me.wangkev.fourtynintysix.Loggable



import me.wangkev.fourtynintysix.{Constants,Util}

import scala.List
import scala.collection.immutable.LinearSeq

/**
 * Created by Kevin on 3/12/2016.
 *
 * Responsible for capturing the main event from UI. Calls subcontroller for some operations
 */
object MainController extends Observable with Loggable with EventHandler[input.KeyEvent]{

  val gameBoard=Array.fill[Int](Constants.GRID_SIZE,Constants.GRID_SIZE)(0)


  //Initialize board here
  /*Generate at least 2 squares with 2 or 4

   */

  val number:Int=2
  var won=false

  def init(): Unit = {
    //place 2 squares
    randomlyPlaceSquares(2)

    boardChanged()

  }

  def randomlyPlaceSquares(numSquares:Int=1): Unit = {
    /**Random place numSquares on the gameGrid
     * THiS METHOD DOES NOT NOTIFY THE UI
     */

    //Find open slots
    var openSlots:LinearSeq[(Int, Int)]=LinearSeq()

    for(row:Int<-0 until Constants.GRID_SIZE;col:Int<-0 until Constants.GRID_SIZE) {
      if(this.gameBoard(row)(col)==Constants.DEFAULT_GRID_VALUE) {
          openSlots=openSlots:+ new Tuple2[Int,Int](row,col)

      }

    }

    var choices:LinearSeq[(Int, Int)]=LinearSeq()
    for (_ <- 0 until numSquares) {
      val ele=Util.generateRandomNum(openSlots.length)
      choices=choices :+ openSlots(ele)

    }

    for(p:Tuple2[Int,Int] <- choices.iterator) {
      this.gameBoard(p._1)(p._2)=Util.generateRandomNum(10) match {
        case n if n <= 6 => 2
        case n if n <= 8 => 4
        case default => 8

      }

    }


  }

  override def handle(event: KeyEvent): Unit = {
    /**Main Handler for KeyEvent from the view
     *
     */
    logger.info(String.format("Captured %s",event.getCode.toString))

    var dX=0
    var dY=0

    //the direction is inverted b/w dX and dY. dY should be row and dX should be col. I have no clue why this is the case
    event.getCode match {
      case KeyCode.UP=>
        dX= -1

      case KeyCode.DOWN=>
        dX= 1

      case KeyCode.LEFT=>
        dY= -1

      case KeyCode.RIGHT=>
        dY= 1


    }

    boardMove(dX,dY)

  }


  def boardMove(dX:Int,dY:Int): Unit ={
    /** Moves each tile on the board to different direction depending on dX, dY.
      *
      *
     */
    var changed:Boolean = false
    var moveMade=false
    //while change is still happening
    do {
      changed=false

      //Must start on different edge depending on direction
      val rowIter = if (dY > 0) Constants.GRID_SIZE-1 to(0, -1) else 0 until Constants.GRID_SIZE
      val colIter = if (dX > 0) Constants.GRID_SIZE-1 to(0, -1) else 0 until Constants.GRID_SIZE

      for (row: Int <- rowIter; col: Int <- colIter) {

        if (this.gameBoard(row)(col) != Constants.DEFAULT_GRID_VALUE)
          changed |= shiftTile(dX, dY, row, col)


      }

      //redraw the board
      boardChanged()

      //TODO:tell javafx ui to refresh?
      if(changed) {
        moveMade = true

      }

    } while(changed)

    //if movemade, generate new tiles
    if(moveMade){
      randomlyPlaceSquares()

      boardChanged()

    }

    //check win condition
    if(!won ) {

      if(hasWon()) {
        System.out.println("You have won!!!")
        won = true
        notifyWin()
      } else if (hasLost()){
        //check lose condition
        notifyLost()

      }
    }




  }

  def hasLost(): Boolean ={
    var hasLost=false

    val coords=Array((0,1),(1,0),(0,-1),(-1,0))

    var valid=false
    for (row: Int <- 0 until Constants.GRID_SIZE; col: Int <- 0 until Constants.GRID_SIZE) {
      val curr_val=this.gameBoard(row)(col)
      //there exists a valid move
      valid=coords exists (coord_pair=>{
        var valid=false
        try {
          valid=valid || this.gameBoard(row + coord_pair._1)(col + coord_pair._2) == curr_val || this.gameBoard(row + coord_pair._1)(col + coord_pair._2) == 0

        } catch {
          //do nothing on outofbounds
          case e:ArrayIndexOutOfBoundsException=>


        }

        valid
      })

      if (valid)
        return false

    }

    //all squares are non valid, can't move after this
    true
  }

  def hasWon(): Boolean ={
    //iterate through each square and check if their value is greater than 2048
    !(gameBoard forall((row:Array[Int])=>row forall((sq_val:Int)=>sq_val<Constants.OBJECTIVE_SCORE )))

  }

  def shiftTile(dX:Int, dY:Int,row:Int,column:Int): Boolean = {
    /**Shift the current tile to the specified direction. See the keyboard listener.
      *
      * returns : Whether the grid value was updated or not.
     *
     */

    //Must start on different edge depending on direction
    val newRow=row+dY
    val newCol=column+dX

    var changed=false

    if (newRow >= 0 && newRow < Constants.GRID_SIZE && newCol >=0 && newCol < Constants.GRID_SIZE) {
      //check if the grid is occupied
      if (this.gameBoard(newRow)(newCol) != Constants.DEFAULT_GRID_VALUE) {
        //occupied!!, same value. Combine them
        if(this.gameBoard(newRow)(newCol)==this.gameBoard(row)(column)) {
          this.gameBoard(newRow)(newCol) *= 2
          changed=true
        }
        //else, different pieces. No change

      } else {
        //move
        this.gameBoard(newRow)(newCol)=this.gameBoard(row)(column)
        changed=true
      }

      //if the grid has been changed, update the old grid's value to 0, or Constants.DEFAUTL_GRID_VALUE
      if(changed) {
        this.gameBoard(row)(column)=Constants.DEFAULT_GRID_VALUE

      }

    }

    changed

  }

  def boardChanged(): Unit ={
    /*Notify Observers that the board changed

     */
    setChanged()

    notifyObservers(gameBoard)
  }

  def notifyWin(): Unit = {
    setChanged()

    notifyObservers(Constants.GAME_WON)
  }

  def notifyLost(): Unit = {
    setChanged()

    notifyObservers(Constants.GAME_LOSE)
  }

}
