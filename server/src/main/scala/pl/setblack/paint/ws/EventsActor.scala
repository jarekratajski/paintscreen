package pl.setblack.paint.ws

import akka.actor.{Actor, ActorLogging}
import org.java_websocket.WebSocket
import pl.setblack.paint.model.GraphicObject

import scala.collection.mutable

object EventsActor {

  sealed trait EventMessage
  case object Clear extends EventMessage
  case class Unregister(ws: WebSocket) extends EventMessage
  case class Send(ev: Seq[GraphicObject]) extends EventMessage

}

class EventsActor extends Actor with ActorLogging {
  import EventsActor._
  import pl.setblack.paint.ws.ReactiveServer._
  import pl.setblack.paint.api.PixelJsonSupport._
  val clients = mutable.ListBuffer[WebSocket]()


  override def receive = {
    case Open(ws, hs) =>
      clients += ws
      ws.send("Nic")
      log.debug("registered monitor for url {}", ws.getResourceDescriptor)

    case Close(ws, code, reason, ext) => self ! Unregister(ws)

    case Error(ws, ex) => self ! Unregister(ws)

    case Message(ws, msg) =>
      log.debug("url {} received msg '{}'", ws.getResourceDescriptor, msg)


    case Unregister(ws) =>
      if (null != ws) {
        clients -= ws
        log.debug("unregister monitor")
      }


    case Send(ev:Seq[GraphicObject]) => {

      import org.json4s._
      import org.json4s.native.Serialization
      import org.json4s.native.Serialization.{read, write}

      val output = ev.map(x => x.toView)

        for (client <- clients) client.send(write(output).toString)
    }
    case _ =>
      System.out.println("reszta");
  }


}
