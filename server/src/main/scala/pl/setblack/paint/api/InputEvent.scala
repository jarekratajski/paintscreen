package pl.setblack.paint.api

trait InputEvent
case class PutPixelEvent(val x:Double, val y:Double, val radius: Double) extends InputEvent
case class SetColorEvent(val  c:Long) extends InputEvent

