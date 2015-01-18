package pl.setblack.paint.api

import java.util.function.Supplier
import pl.setblack.airomem.core.SimpleController
import pl.setblack.airomem.core.SimpleController.loadOptional
import pl.setblack.paint.model._
import spray.http.MediaTypes._
import spray.httpx.{SprayJsonSupport, Json4sSupport}
import spray.routing.HttpService
import pl.setblack.paint.util.JavaIntegration._

trait PaintService extends HttpService {

  val room = new Room("mainRoom")


  def propagate(ev: Seq[GraphicObject])

  val paintRoute = path("services" / "paint") {


    import org.json4s.native.Serialization.{read, write}
    get {
      respondWithMediaType(`application/json`) {
        complete {
          import PixelJsonSupport._
          write[RoomView]( room.toView)(formatsPixel).toString

        }
      }
    } ~
      post {

        respondWithMediaType(`application/json`) {
          entity(as[String]) { serialized =>
            complete {
              import EventJsonSupport._
              val event = deserializeEvent(serialized)
              val processed: Seq[GraphicObject] = room.processEvent(event)
              propagate(processed)
              "{\"status\": \"ok\"}"
            }
          }
        }
      }


  }
}