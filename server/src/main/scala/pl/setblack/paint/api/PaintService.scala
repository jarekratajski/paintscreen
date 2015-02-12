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

  val roomSupplier: Supplier[Room] = (() => new Room("default"))
  var roomController: SimpleController[Room] = loadOptional("room", roomSupplier)


  def propagate(ev: Seq[GraphicObject])

  val paintRoute = path("services" / "paint") {


    import org.json4s.native.Serialization.{read, write}
    get {
      respondWithMediaType(`application/json`) {
        complete {
          import PixelJsonSupport._
          write[RoomView](roomController.query((r: Room) => r.toView)
          )(formatsPixel).toString

        }
      }
    } ~
      post {

        respondWithMediaType(`application/json`) {
          entity(as[String]) { serialized =>
            complete {
              println("posted")
              import EventJsonSupport._
              val event = deserializeEvent(serialized)
              val processed: Seq[GraphicObject] = roomController.executeAndQuery(
                (r: Room) => r.processEvent(event))
              propagate(processed)
              "{\"status\": \"ok\"}"
            }
          }
        }
      }


  }
}