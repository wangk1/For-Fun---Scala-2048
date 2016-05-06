package me.wangkev.fourtynintysix

import me.wangkev.fourtynintysix.Logger

/**
 * Created by Kevin on 3/12/2016.
 *
 * Convenient trait for those objects that needs logger
 */
trait Loggable {
  val logger=new Logger(this.getClass)

}
