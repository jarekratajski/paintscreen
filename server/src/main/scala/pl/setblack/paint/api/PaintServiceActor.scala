package pl.setblack.paint.api

import java.awt.Color

import akka.actor.{ActorSystem, Actor}
import pl.setblack.paint.model._
import spray.http.MediaTypes._
import spray.httpx.{SprayJsonSupport, Json4sSupport}
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import spray.routing._


class PaintServiceActor extends Actor with PaintService {
  def actorRefFactory = context

  def receive = runRoute(paintRoute ~ normalRoute)

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
              val ev = new PaintEvent(1, user, 9.0, 10.0, 0.2, Color.BLACK)
              room.addEvent(ev)
              "{\"status\": \"ok\"}"
            }
          }
        }
      }


  }
}