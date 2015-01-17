package pl.setblack.paint.api

abstract class InputEvent {
  def session:Long
}
case class PutPixelEvent(val x:Double, val y:Double, val radius: Double, val session:Long)
    extends InputEvent
case class SetColorEvent(val  c:String,  val session:Long)
  extends InputEvent
case class WaveEvent(val  wave:Array[Float],  val session:Long)
  extends InputEvent
