package pl.setblack.paint.model

import scala.collection.Seq


class Room(val name: String) extends Serializable {
 var events : Seq[Event] = Nil

}