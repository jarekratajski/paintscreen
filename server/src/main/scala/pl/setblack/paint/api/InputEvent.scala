package pl.setblack.paint.api

abstract class InputEvent {
  def session:Long
}
case class PutPixelEvent(x:Double, y:Double,  radius: Double,session:Long)
    extends InputEvent
case class WaveEvent(  wave:Array[Float],   session:Long)
  extends InputEvent
