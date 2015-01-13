package pl.setblack.paint.api

import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import pl.setblack.paint.model.{GraphicObjectView, PixelView}
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by Kanapka on 1/13/2015.
 */

class PixelJsonSupport

object PixelJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val formatsPixel = Serialization.formats(ShortTypeHints(List(
    classOf[GraphicObjectView],
    classOf[PixelView]

  )))
}
