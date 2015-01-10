package pl.setblack.paint.model

/**
 * Created by Kanapka on 1/2/2015.
 */
class Event(val id: Long, val author: User) extends Serializable {
 def toView  = {
   AnyEvent(0,0,0)
 }
}
