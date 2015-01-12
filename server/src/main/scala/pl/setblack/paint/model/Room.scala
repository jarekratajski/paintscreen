package pl.setblack.paint.model

import java.util.concurrent.CopyOnWriteArrayList
import scala.collection.JavaConversions._
import scala.collection.Seq


class Room(val name: String) extends Serializable {
 var events : CopyOnWriteArrayList[GraphicObject] = new CopyOnWriteArrayList[GraphicObject]()
 def toView = new RoomView(name, events.toIndexedSeq)

 def addEvent(ev:GraphicObject) = {
  events.add(ev)
 }
}

case class RoomView(val name: String, val events: Seq[GraphicObject])