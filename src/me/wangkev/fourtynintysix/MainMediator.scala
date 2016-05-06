package me.wangkev.fourtynintysix

import javafx.application.Application
import javafx.stage.Stage

import me.wangkev.fourtynintysix.controller.MainController
import me.wangkev.fourtynintysix.ui.{UIController, GameUI}

/**
 * Created by Kevin on 3/12/2016.
 */
class MainMediator extends Application with Loggable{



  override def start(primaryStage: Stage): Unit = {
    val uiController=new UIController()
    uiController.startUI(primaryStage)

    //must INIT UI first

    MainController.addObserver(uiController)

    //must add observer before starting the controller
    MainController.init()


  }
}
