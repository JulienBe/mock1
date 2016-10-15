package physic

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.maps.objects.{CircleMapObject, EllipseMapObject, PolygonMapObject, RectangleMapObject}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import lights.RayHandler
import stuff.{MyWorld, Wall}

/**
  * Created by julien on 08/10/16.
  */
class Physic(squarehole: Squarehole) {

  Physic.world.setContactListener(new CollisionMaster)

  var accumulator = 0f

  def render(delta: Float, cam: OrthographicCamera) = {
    Physic.rayHandler.setCombinedMatrix(cam)
    Physic.rayHandler.updateAndRender()
  }

  def doPhysicsStep(deltaTime: Float) {
    // fixed time step
    // max frame time to avoid spiral of death (on slow devices)
    val frameTime = Math.min(deltaTime, 0.25f)
    accumulator += frameTime
    while (accumulator >= Physic.timestep) {
      Physic.world.step(Physic.timestep, Physic.velocityIteration, Physic.positionIteration)
      accumulator -= Physic.timestep
    }
  }

  def bodyFromMap(tiledMap: TiledMap, pixelPerTile: Float = 16): Unit = {
    val obstacles = tiledMap.getLayers.get("Obstacles").getObjects.iterator()
    while (obstacles.hasNext) {
      val obstacle = obstacles.next()
      var shape: Shape = null
      obstacle match {
        case rect:RectangleMapObject => shape = getRectangle(rect.getRectangle, pixelPerTile)
        case c:CircleMapObject => shape = getCircle(c.getCircle.radius, c.getCircle.x, c.getCircle.y, pixelPerTile)
        case poly:PolygonMapObject => shape = getPolygon(poly.getPolygon.getVertices)
        case c:EllipseMapObject => shape = getCircle(c.getEllipse.width / 2, c.getEllipse.x, c.getEllipse.y, pixelPerTile)
        case _ => {
          println("couldn't match obstacles : " + obstacle)
        }
      }
      val bd = new BodyDef()
      bd.`type` = BodyType.StaticBody
      val body = Physic.world.createBody(bd)
      val fixture = body.createFixture(shape, 1)
      fixture.setUserData(new Wall)
      val filter = new Filter
      filter.categoryBits = Physic.otherCategory
      filter.maskBits = Physic.otherMask
      fixture.setFilterData(filter)
      shape.dispose()
    }
  }

  def getRectangle(rectangle: Rectangle, ppt: Float): Shape = {
    val polygon = new PolygonShape()
    val center = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt, (rectangle.y + rectangle.height * 0.5f) / ppt)
    polygon.setAsBox((rectangle.width * 0.5f) / ppt, (rectangle.height * 0.5f) / ppt, center, 0.0f)
    polygon
  }

  def getCircle(radius: Float, x: Float, y: Float, ppt: Float): Shape = {
    val circleShape = new CircleShape()
    circleShape.setRadius(radius / ppt)
    circleShape.setPosition(new Vector2((x + radius) / ppt, (y + radius) / ppt))
    circleShape
  }

  def getPolygon(vertices: scala.Array[Float]): Shape = {
    val polygon = new PolygonShape()
    polygon.set(vertices)
    polygon
  }

}

object Physic {
  val playerCategory: Short = 0x0001
  val otherCategory: Short = 0x0002
  val playerMask: Short = otherCategory
  val otherMask: Short = (otherCategory | playerCategory).toShort

  val world = new World(Vector2.Zero, true)
  val rayHandler = {
    val r = new RayHandler(Physic.world)
    r.setAmbientLight(1f, 1f, 1f, 0.05f)
//    r.setShadows(false)
//    r.setCulling(false)
//    r.setBlur(false)
//    RayHandler.setGammaCorrection(true)
    r
  }
  val timestep = 1/60f
  val velocityIteration = 6
  val positionIteration = 2
}