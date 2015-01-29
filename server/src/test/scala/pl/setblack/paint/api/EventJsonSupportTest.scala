package pl.setblack.paint.api

import org.scalatest.{Matchers, FlatSpec}



class EventJsonSupportTest extends FlatSpec with Matchers {
  import EventJsonSupport._
  import org.json4s.native.Serialization.{read, write}

  "PutPixelEvent" should "be serialized " in {
    val event = PutPixelEvent(7,5,2,1)
    val serialized = write(event);
    serialized should equal ("""{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0,"session":1}""")
  }

  "PutPixelEvent" should "be deserialized " in {

    val serialized = """{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0,"session":1}"""
    val event = read[InputEvent](serialized)
    event should equal (PutPixelEvent(7,5,2,1))
  }


  "WaveEvent" should "be deserialized" in {
    val serialized =
      """{"jsonClass":"WaveEvent","wave":[-0.14,-0.16,-0.17,-0.28,-0.13,0.1,0,0.18,0.41,0.1],"session":2}""".stripMargin
    val event = read[InputEvent](serialized)
    val waveEvent:WaveEvent = event.asInstanceOf[WaveEvent]
      waveEvent.wave(0).toString should equal ("-0.14")
    }

  "deserializeEvent" should "deserialized wave" in {
    val serialized =
      """{"jsonClass":"WaveEvent","wave":[-0.14,-0.16,-0.17,-0.28,-0.13,0.1,0,0.18,0.41,0.1],"session":2}""".stripMargin
    for( a <- 1 until 10) {
      val event = EventJsonSupport.deserializeEvent(serialized)
      val waveEvent:WaveEvent = event.asInstanceOf[WaveEvent]
      waveEvent.wave(0).toString should equal ("-0.14")
    }

  }
}
