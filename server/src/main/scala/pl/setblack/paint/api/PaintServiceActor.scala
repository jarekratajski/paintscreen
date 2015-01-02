package pl.setblack.paint.api

import akka.actor.{ActorSystem, Actor}
import spray.http.MediaTypes._
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.HttpService
import scala.concurrent.Future

import spray.routing.HttpService

/**
 * Created by Kanapka on 1/2/2015.
 */
class PaintServiceActor extends Actor with PaintService {
  def actorRefFactory = context

  def receive = runRoute(paintRoute)
}


trait PaintService extends HttpService {


  val paintRoute = path("paint") {
     get {
        complete {
          "1"
        }
     }
  }

}