package pl.setblack.paint.api

import org.scalatest.{Matchers, FlatSpec}
import pl.setblack.paint.api.SetColorEvent


class EventJsonSupportTest extends FlatSpec with Matchers {
  import EventJsonSupport._
  import org.json4s.native.Serialization.{read, write}

  "PutPixelEvent" should "be serialized " in {
    val event = PutPixelEvent(7,5,2,1)
    val serialized = write(event);
    serialized should equal ("""{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0}""")
  }

  "PutPixelEvent" should "be deserialized " in {

    val serialized = """{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0}"""
    val event = read[InputEvent](serialized)
    event should equal (PutPixelEvent(7,5,2,1))
  }

  "SetColorEvent" should "be serialized" in {
    val event = SetColorEvent("#ffff00",1)
    val serialized = write(event);
    serialized should equal ("""{"jsonClass":"SetColorEvent","c":"#ffff00"}""")
  }
  "SetColorEvent" should "be deserialized" in {
    val serialized = """{"jsonClass":"SetColorEvent","c":"#ffff00"}"""
    val event = read[InputEvent](serialized)
    event should equal (SetColorEvent("#ffff00",1))
  }
}
