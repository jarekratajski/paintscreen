package pl.setblack.paint.model

import java.awt.Color
import java.util.concurrent.CopyOnWriteArrayList
import pl.setblack.airomem.core.sequnce.Sequence
import pl.setblack.paint.api.{SetColorEvent, PutPixelEvent, InputEvent}

import scala.collection.JavaConversions._
import scala.collection.Seq


class Room(val name: String) extends Serializable {
 var objects : CopyOnWriteArrayList[GraphicObject] = new CopyOnWriteArrayList[GraphicObject]()
 val sessionsSequence = new Sequence()
 val objectsSequence = new Sequence()

 def toView = new RoomView(name, objects.toIndexedSeq, sessionsSequence.generateId())


 def processEvent(event:InputEvent):Seq[GraphicObject] = {
  event match {
   case PutPixelEvent(x,y,r,ses) =>

    val pixel = new Pixel(objectsSequence.generateId(),  x ,y,r, "#ffffff")
    System.out.println("event!" + x + "," + y)
    objects.add(pixel)
    Seq(pixel)
   case SetColorEvent(c,ses) =>
    System.out.println("clor:"+c)
      Nil
  }
 }
}

case class RoomView(val name: String, val objects: Seq[GraphicObject], val sessionId:Long)