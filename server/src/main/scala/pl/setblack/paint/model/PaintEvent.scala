package pl.setblack.paint.model

import java.awt.Color

/**
 * Created by Kanapka on 1/2/2015.
 */
class PaintEvent( id: Long,  author: User,
                   val x:Double,
                   val y:Double,
                   val radius:Double,
                   val c: Color ) extends Event(id, author){
  override def toView  = {
    AnyEvent(x,y,radius)
  }
}
