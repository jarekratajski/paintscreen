package pl.setblack.paint.server



import akka.actor.{Actor, Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import pl.setblack.paint.api.PaintService
import pl.setblack.paint.ws.{EventsActor, ReactiveServer}
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask

class MainService extends  Actor with PaintService {


def actorRefFactory = context

def receive = runRoute(paintRoute ~ normalRoute)
}

object Main {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("on-spray-can");
    lazy val events = system.actorOf(Props[EventsActor], "events")

     val rs = new ReactiveServer(Configuration.portWs)
    rs.forResource("/events/ws", Some(events))
    rs.start
    sys.addShutdownHook({ system.shutdown; rs.stop })

    val service = system.actorOf(Props[MainService], "httpService")

    implicit val timeout = Timeout(5.seconds)

    IO(Http) ? Http.Bind(service, interface = Configuration.host, port = Configuration.portHttp)

  }

}

object Configuration {
  import com.typesafe.config.ConfigFactory

  private val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  val host = config.getString("paint.host")
  val portHttp = config.getInt("paint.ports.http")
  val portTcp = config.getInt("paints.ports.tcp")
  val portWs = config.getInt("paint.ports.ws")
}