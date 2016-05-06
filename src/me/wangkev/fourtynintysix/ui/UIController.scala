package me.wangkev.fourtynintysix.ui

import java.util.{Observable, Observer}
import javafx.application.Application
import javafx.stage.Stage

import me.wangkev.fourtynintysix.Loggable
import me.wangkev.fourtynintysix.Constants

/**
 * Created by Kevin on 4/12/2016.
 */
class UIController extends Observer with Loggable{
  val ui:GameUI=new GameUI

  def startUI(primaryStage: Stage): Unit = {
    logger.info("Init ui...")

    ui.initUI(primaryStage)


  }

  def restart(): Unit = {


  }

  def winOrLose(cond:String): Unit = {
    this.logger.info("Engaging game win or game lost dialog")

    if(cond.equals(Constants.GAME_WON)) {
      this.ui.showPopUp("You have won!!!")

    } else {
      this.ui.showPopUp("You have lost.")

    }

  }

  override def update(o: Observable, arg: scala.Any): Unit = {


    try {
      winOrLose(arg.asInstanceOf[String])

    } catch {
      case e:ClassCastException=>
        val board=arg.asInstanceOf[Array[Array[Int]]]

        for(row:Int <- 0 until Constants.GRID_SIZE;col:Int <- 0 until Constants.GRID_SIZE)
          ui.gameTiles(row)(col).setValue(board(row)(col))

    }


  }
}
