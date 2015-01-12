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


class PaintServiceActor extends Actor with PaintService {
  def actorRefFactory = context

  def receive = runRoute(paintRoute ~ normalRoute)

  def propagate(ev:GraphicObject ) = {
    context.actorSelection("/event/ws") ! EventsActor.Send(ev)
  }
}





trait PaintService extends HttpService {
  val room = new Room("default")

 val normalRoute  = pathPrefix("") {
   getFromDirectory("../client/app/")
 }

  def propagate(ev:GraphicObject)

  val paintRoute = path("services" / "paint") {
    import EventJsonSupport._
    import org.json4s.native.Serialization.{read, write}
    get {
      respondWithMediaType(`application/json`) {
        complete {
          write(room.toView).toString

        }
      }
    } ~
      post {
        respondWithMediaType(`application/json`) {
          entity(as[String]) { serialized =>
            complete {
              val event = read[PutPixelEvent](serialized)
              System.out.println("event!" + event.x + "," + event.y)
              val user = new User("irek")
              val ev = new Pixel(1, user, event.x, event.y, event.radius, Color.BLACK)
              room.addEvent(ev)
              propagate(ev)
              "{\"status\": \"ok\"}"
            }
      }
  }
      }


  }
}