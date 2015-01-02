package pl.setblack.paint.server

import akka.actor.{Actor, Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import pl.setblack.paint.api.PaintService
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask
/**
 * Created by Kanapka on 1/2/2015.
 */
class MainService extends  Actor with PaintService {


def actorRefFactory = context

def receive = runRoute(paintRoute)
}

object Main {

  def main(args: Array[String]): Unit = {
    println("Hello, world!");

    implicit val system = ActorSystem("on-spray-can");


    val service = system.actorOf(Props[MainService], "httpService")

    implicit val timeout = Timeout(5.seconds)

    IO(Http) ? Http.Bind(service, interface = "localhost", port = 8081)

  }

}