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
 def toView = new RoomView(name, objects.map( x=>x.toView).takeRight(1000).toArray, sessionsSequence.generateId())
 System.out.println("Room created")

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

    objects.add(pixel)
    Seq(pixel)
   case SetColorEvent(c,ses) =>
    colors.put(ses,c)
      Nil
   case WaveEvent(data,ses) =>
      val wave = new Wave(objectsSequence.generateId(),data)
     objects.add(wave)
     Seq(wave)
   case _ =>
      Nil
  }
 }
}

case class RoomView(val name: String, val objects: Array[GraphicObjectView], val sessionId:Long)