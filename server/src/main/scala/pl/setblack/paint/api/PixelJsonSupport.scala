package pl.setblack.paint.api

import org.json4s.{Serialization, ShortTypeHints}
import org.json4s.native.Serialization
import pl.setblack.paint.model.{RoomView, WaveView, GraphicObjectView, PixelView}


/**
 * Created by Kanapka on 1/13/2015.
 */

class PixelJsonSupport

object PixelJsonSupport {
  implicit val formatsPixel = Serialization.formats(ShortTypeHints(List(
    classOf[GraphicObjectView],
    classOf[PixelView],
    classOf[WaveView],
    classOf[RoomView]
  )))
}
