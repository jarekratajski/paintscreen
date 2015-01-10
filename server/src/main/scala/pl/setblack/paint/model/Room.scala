package pl.setblack.paint.model

import java.util.concurrent.CopyOnWriteArrayList
import scala.collection.JavaConversions._
import scala.collection.Seq


class Room(val name: String) extends Serializable {
 var events : CopyOnWriteArrayList[Event] = new CopyOnWriteArrayList[Event]()
 def toView = new RoomView(name, events.toIndexedSeq)

 def addEvent(ev:Event) = {
  events.add(ev)
 }
}

case class RoomView(val name: String, val events: Seq[Event])