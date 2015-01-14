package pl.setblack.paint.api

import org.json4s.native.Serialization._
import org.scalatest.{FlatSpec, Matchers}
import pl.setblack.paint.model.GraphicObject
import spray.testkit.ScalatestRouteTest

class PaintServiceTest extends FlatSpec
 with Matchers
  with PaintService
  with ScalatestRouteTest {
  def actorRefFactory = null
  override def propagate(ev: Seq[GraphicObject]): Unit = {

  }



  "Post Event" should "Call room processing " in {
    Post("/services/paint", """{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0,"session":7}""") ~> paintRoute ~> check {
      responseAs[String] should include("ok")
    }
  }


}
