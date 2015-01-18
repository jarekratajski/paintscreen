package pl.setblack.paint.model


class Wave(id : Long, wave : Array[Float]) extends GraphicObject(id) {
  override def toView: GraphicObjectView = {
    WaveView(id, wave)
  }
}
case class WaveView(id : Long, wave : Array[Float])  extends GraphicObjectView