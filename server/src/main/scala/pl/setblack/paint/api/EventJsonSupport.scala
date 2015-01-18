package pl.setblack.paint.api

import org.json4s.ShortTypeHints
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

class EventJsonSupport {


}

object EventJsonSupport {
  implicit val formatsEvents = Serialization.formats(ShortTypeHints(List(
    classOf[WaveEvent],
    classOf[PutPixelEvent]

  )))


   def deserializeEvent( s: String):InputEvent = {
      Serialization.read[InputEvent](s)
   }
}