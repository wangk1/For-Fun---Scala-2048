package me.wangkev.fourtynintysix.ui

import java.util.function.Consumer
import javafx.scene.paint.Paint
import javafx.scene.text.{Text, TextFlow}
import javafx.scene.Node
import me.wangkev.fourtynintysix.Constants

/**
 * Created by Kevin on 4/11/2016.
 */
class GridTile extends TextFlow(new Text("")){
  /**Representing a tile on the grid
   *
   * It can determine its own color from its value
   *
   *
   */

  this.getStyleClass.addAll("game_tile","game_tile_0")

  //change the tile's value
  var value:Int= 0
  setValue(0,true)

  def setValue(newVal:Int,ignore:Boolean=false) {
    if (newVal == this.value && !ignore)
      return

    this.value=newVal

    val textColor=getTextColor(this.value)

    //change tile color to the right css
    this.getStyleClass.remove(1)
    if (newVal <=4096)
      this.getStyleClass.add("game_tile_"+newVal)
    else
      this.getStyleClass.add("game_tile_8192")


    //piece of garbage java fx won't accept lambdas from scala
    this.getChildren.forEach(new Consumer[Node] {
      override def accept(t: Node): Unit = {
        val text:Text=t.asInstanceOf[Text]

        text.setFill(Paint.valueOf(textColor))
        text.setText(if (newVal>0) newVal+"" else "")

      }
    })

  }

  def getTextColor(value: Int): String = value match {
    /**Get the appropriate text color depending on the value in the text
     *
     */
    case x if x >= 8 =>
      Constants.UI.VALUE_TO_COLOR(16)

    //lt 16. We use the set color of 2
    case default =>
      Constants.UI.VALUE_TO_COLOR(2)
  }



}
