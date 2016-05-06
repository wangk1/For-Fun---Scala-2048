package me.wangkev.fourtynintysix

import scala.util.Random

/**
 * Created by Kevin on 4/13/2016.
 */
object Util {
  val random=new Random()

  def generateRandomNum(end:Int): Int = {
    random.nextInt(end)

  }

  def generateRandomNum(): Int = {
    generateRandomNum(Constants.GRID_SIZE)

  }

}
