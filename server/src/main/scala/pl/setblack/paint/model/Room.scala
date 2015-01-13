package pl.setblack.paint.model

import java.awt.Color
import java.util.concurrent.{ConcurrentSkipListMap, ConcurrentNavigableMap, CopyOnWriteArrayList}
import pl.setblack.airomem.core.sequnce.Sequence
import pl.setblack.paint.api.{WaveEvent, SetColorEvent, PutPixelEvent, InputEvent}

import scala.collection.JavaConversions._
import scala.collection.Seq


class Room(val name: String) extends Serializable {
 val objects : CopyOnWriteArrayList[GraphicObject] = new CopyOnWriteArrayList[GraphicObject]()
 val sessionsSequence = new Sequence()
 val objectsSequence = new Sequence()
 val colors  = new ConcurrentSkipListMap[Long,String]()
 def toView = new RoomView(name, objects.toIndexedSeq, sessionsSequence.generateId())

def getColor( ses:Long):String = {
 if ( colors.containsKey(ses))  {
  return colors.get(ses);
 } else {
  "#ffffff"
 }
}
 def processEvent(event:InputEvent):Seq[GraphicObject] = {
  event match {
   case PutPixelEvent(x,y,r,ses) =>

    val pixel = new Pixel(objectsSequence.generateId(),  x ,y,r, getColor(ses))
    System.out.println("event!" + x + "," + y)
    objects.add(pixel)
    Seq(pixel)
   case SetColorEvent(c,ses) =>
    colors.put(ses,c)
      Nil
   case WaveEvent(data,ses) =>

   case _ =>
      Nil
  }
 }
}

case class RoomView(val name: String, val objects: Seq[GraphicObject], val sessionId:Long)