package pl.setblack.paint.model

/**
 * Created by Kanapka on 1/13/2015.
 */
class Wave(id : Long, wave : Seq[Float]) extends GraphicObject(id) {
  override def toView: GraphicObjectView = {
    WaveView(id, wave)
  }
}
case class WaveView(id : Long, wave : Seq[Float])  extends GraphicObjectView