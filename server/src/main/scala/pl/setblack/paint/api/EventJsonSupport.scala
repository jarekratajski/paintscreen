package pl.setblack.paint.api

import org.json4s.ShortTypeHints
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import pl.setblack.paint.model.RoomView
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol


class EventJsonSupport {

}

object EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val formatsEvents = Serialization.formats(ShortTypeHints(List(
    classOf[InputEvent],
    classOf[PutPixelEvent],
    classOf[SetColorEvent],
    classOf[WaveEvent]
  )))

}