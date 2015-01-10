package pl.setblack.paint.api

import java.awt.Color


import akka.actor.{ActorSystem, Actor}
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

  def propagate(ev:Event ) = {
    context.actorSelection("/event/ws") ! EventsActor.Send(ev)
  }
}



object EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val eventFormats = jsonFormat3(AnyEvent)
}

trait PaintService extends HttpService {
  val room = new Room("default")

 val normalRoute  = pathPrefix("") {
   System.out.println("I am here")
   getFromDirectory("C:/dev/prj/painscreen/client/app/")
 }

  def propagate(ev:Event)

  val paintRoute = path("services" / "paint") {
    import EventJsonSupport._
    get {
      respondWithMediaType(`application/json`) {
        complete {
          import org.json4s._
          import org.json4s.native.Serialization
          import org.json4s.native.Serialization.{read, write}
          implicit val formats = Serialization.formats(NoTypeHints)

          write(room.toView).toString

        }
      }
    } ~
      post {
        respondWithMediaType(`application/json`) {
          entity(as[AnyEvent]) { event =>
            complete {
              System.out.println("event!" + event.x + "," + event.y)
              val user = new User("irek")
              val ev = new PaintEvent(1, user, event.x, event.y, event.radius, Color.BLACK)
              room.addEvent(ev)
              propagate(ev)


              "{\"status\": \"ok\"}"
            }
      }
  }
      }


  }
}