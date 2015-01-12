package pl.setblack.paint.model

import pl.setblack.paint.api.{PutPixelEvent, InputEvent}

/**
 * Created by Kanapka on 1/2/2015.
 */
class GraphicObject(val id: Long, val author: User) extends Serializable {
 def toView  = {
   PutPixelEvent(0,0,0)
 }
}
