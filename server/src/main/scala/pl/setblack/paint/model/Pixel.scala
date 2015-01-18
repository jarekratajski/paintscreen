package pl.setblack.paint.model

import pl.setblack.paint.api.{PutPixelEvent, InputEvent}


class Pixel( id: Long,
                   val x:Double,
                   val y:Double,
                   val radius:Double,
                   val c: String ) extends GraphicObject(id){
  override def toView  = {
    PixelView(id,x,y,radius,c)
  }
}

case class PixelView( id: Long,
                      x:Double,
                      y:Double,
                      radius:Double,
                      c: String) extends GraphicObjectView
