package me.wangkev.fourtynintysix.events


/**
 * Created by Kevin on 4/13/2016.
 *
 * Event used for communicating b/w
 */
class Event(eType:Int,row:Int,col:Int,value:Int) {
  object EventType {
    val INIT_BLOCK=0
    val MOVE_BLOCK=1
    val CLEAR_BLOCK=2
    val SET_BLOCK=3

  }



}
