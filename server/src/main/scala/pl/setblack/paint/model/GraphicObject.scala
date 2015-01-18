package pl.setblack.paint.model

import pl.setblack.paint.api.{PutPixelEvent, InputEvent}


abstract class GraphicObject(val id: Long) extends Serializable {
 def toView:GraphicObjectView ;

}

