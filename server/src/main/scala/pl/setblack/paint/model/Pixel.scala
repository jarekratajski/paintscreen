package pl.setblack.paint.model

import java.awt.Color

import pl.setblack.paint.api.{PutPixelEvent, InputEvent}


class Pixel( id: Long,  author: User,
                   val x:Double,
                   val y:Double,
                   val radius:Double,
                   val c: Color ) extends GraphicObject(id, author){
  override def toView  = {
    PutPixelEvent(x,y,radius)
  }
}
