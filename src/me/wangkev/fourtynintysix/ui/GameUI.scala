package me.wangkev.fourtynintysix.ui

import java.io.File
import javafx.application.Application
import javafx.geometry.{VPos, HPos}
import javafx.scene.Scene
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.{TilePane,RowConstraints, GridPane, StackPane}
import javafx.scene.paint.Paint
import javafx.scene.text.{Text, TextFlow}
import javafx.stage.{Popup, Stage}


import me.wangkev.fourtynintysix.{Constants, Loggable}
import me.wangkev.fourtynintysix.controller.MainController


import scalafx.scene.input.KeyEvent


/**
 * Created by Kevin on 3/5/2016.
 *
 * This class is the container for the entire UI of the game.
 *
 * UI Controller is responsible for controlling the actions of the UI in this class
 */
class GameUI extends Loggable{
  val gameGrid=new GridPane()
  var primaryStage:Stage=null

  setupGrid()

  //column to row index of gameTiles
  val gameTiles=Array.fill[GridTile](Constants.GRID_SIZE,Constants.GRID_SIZE)(new GridTile)

  //set up the grid
  //ignore compiler error here
  for (i:Int <- 0 until Constants.GRID_SIZE; j:Int<- 0 until Constants.GRID_SIZE) gameGrid.add(gameTiles(i)(j), i, j)


  private def setupGrid(): Unit = {
    /**Initialize the grid, setup the column constraints
     *
     */
    var r = new RowConstraints
    //gridPane initialization
    for (i:Int<-0 until Constants.GRID_SIZE) {
      var row=new javafx.scene.layout.RowConstraints
      row.setPercentHeight(100/Constants.GRID_SIZE)
      row.setValignment(VPos.CENTER)

      gameGrid.getRowConstraints.add(row)

    }

    //gridPane initialization
    for (i<-0 until Constants.GRID_SIZE) {
      var col=new ColumnConstraints
      col.setPercentWidth(100/Constants.GRID_SIZE)


      gameGrid.getColumnConstraints.add(col)

    }

    gameGrid.setHgap(Constants.GRID_MARGIN)
    gameGrid.setVgap(Constants.GRID_MARGIN)

    gameGrid.setId("game_grid")

  }

  def initUI(primaryStage: Stage): Unit = {
    this.primaryStage=primaryStage
    primaryStage.setTitle("4096-2^")

    //Addition to scene

    var scene=new Scene(gameGrid, 300, 300)
    scene.addEventFilter(KeyEvent.KeyReleased,MainController)

    val styleSheet=new File(Constants.STYLE_SHEET_PATH)
    scene.getStylesheets.add("file:///"+styleSheet.getAbsolutePath.replace("\\","/"))

    primaryStage.setScene(scene)

    primaryStage.show()


  }

  def showPopUp(text:String): Unit = {
    val popup:Popup=new Popup

    popup.getContent.addAll(new Text(text))

    popup.show(primaryStage)


  }

}
