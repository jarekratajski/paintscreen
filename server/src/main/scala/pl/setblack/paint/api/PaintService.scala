package pl.setblack.paint.api

import java.awt.Color

import akka.actor.{ActorSystem, Actor}
import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import pl.setblack.paint.model._
import pl.setblack.paint.ws.EventsActor
import spray.http.MediaTypes._
import spray.httpx.{SprayJsonSupport, Json4sSupport}
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import spray.routing._

trait PaintService extends HttpService {
  val room = new Room("default")



  def propagate(ev:Seq[GraphicObject])

  val paintRoute = path("services" / "paint") {
    import EventJsonSupport._

    import org.json4s.native.Serialization.{read, write}
    get {
      respondWithMediaType(`application/json`) {
        complete {
          import PixelJsonSupport._
          write[RoomView]( room.toView )( formatsPixel ).toString
        }
      }
    } ~
      post {
        respondWithMediaType(`application/json`) {
          entity(as[String]) { serialized =>
            complete {
              val event = read[InputEvent](serialized)
              val  processed:Seq[GraphicObject] = room.processEvent(event);
              propagate ( processed)
              "{\"status\": \"ok\"}"
            }
      }
  }
      }


  }
}