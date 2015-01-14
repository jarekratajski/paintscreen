package pl.setblack.paint.api

import java.awt.Color
import java.util.function.Supplier

import akka.actor.{ActorSystem, Actor}
import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import pl.setblack.airomem.core.SimpleController
import pl.setblack.airomem.core.SimpleController.loadOptional
import pl.setblack.paint.model._
import pl.setblack.paint.ws.EventsActor
import spray.http.MediaTypes._
import spray.httpx.{SprayJsonSupport, Json4sSupport}
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import spray.routing._
import pl.setblack.paint.util.JavaIntegration._

trait PaintService extends HttpService {

   val roomSupplier : Supplier[Room] = (() => new Room("default") )
  val roomController:SimpleController[Room]  = loadOptional("room", roomSupplier)
  val room = new Room("default")



  def propagate(ev:Seq[GraphicObject])

  val paintRoute = path("services" / "paint") {
    import EventJsonSupport._

    import org.json4s.native.Serialization.{read, write}
    get {
      respondWithMediaType(`application/json`) {
        complete {
          import PixelJsonSupport._
          write[RoomView](roomController.query( (r:Room)=>r.toView)
             )( formatsPixel ).toString

        }
      }
    } ~
      post {
        respondWithMediaType(`application/json`) {
          entity(as[String]) { serialized =>
            complete {
              val event = read[InputEvent](serialized)
              val  processed:Seq[GraphicObject] = roomController.executeAndQuery(
                (r:Room) => r.processEvent(event))
              propagate ( processed)
              "{\"status\": \"ok\"}"
            }
      }
  }
      }


  }
}