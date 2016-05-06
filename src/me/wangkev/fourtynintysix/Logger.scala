package me.wangkev.fourtynintysix

/**
 * Created by Kevin on 3/7/2016.
 */
class Logger(origin:Class[_])  {


  def debug(info:String):Unit={
    println("Debug: %s, from: %s".format(info,origin.toString))

  }

  def info(info:String):Unit={
    println("Info: %s, from: %s".format(info,origin.toString))

  }

  def error(info:String):Unit={
    System.err.println("Error: %s, from: %s".format(info,origin.toString))

  }
}
