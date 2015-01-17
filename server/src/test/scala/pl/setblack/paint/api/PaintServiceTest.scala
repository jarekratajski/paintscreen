package pl.setblack.paint.api

import org.json4s.native.Serialization._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import pl.setblack.airomem.core.disk.PersistenceDiskHelper
import pl.setblack.paint.model.GraphicObject
import spray.testkit.ScalatestRouteTest

class PaintServiceTest extends FlatSpec
 with Matchers
  with PaintService
  with ScalatestRouteTest
  with BeforeAndAfter {
  def actorRefFactory = null
  override def propagate(ev: Seq[GraphicObject]): Unit = {
  }
  before {
    PersistenceDiskHelper.delete("room")
  }



  "Post Event" should "Call room processing " in {
    Post("/services/paint", """{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0,"session":7}""") ~> paintRoute ~> check {
      responseAs[String] should include("ok")
    }

    "PostedEvent" should "be visible in return " in {
      Post("/services/paint", """{"jsonClass":"PutPixelEvent","x":7.0,"y":5.0,"radius":2.0,"session":7}""") ~> paintRoute

      Get("/services/paint") ~> paintRoute ~> check {
        responseAs[String] should include("radius")
      }
    }

  }



}
