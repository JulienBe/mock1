package physic

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.objects.{CircleMapObject, EllipseMapObject, PolygonMapObject, RectangleMapObject}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.{Circle, Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.utils.Array
import stuff.Wall

/**
  * Created by julien on 08/10/16.
  */
class Physic(squarehole: Squarehole) {

  Physic.world.setContactListener(new CollisionMaster)

  var accumulator = 0f

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
        case c:CircleMapObject => shape = getCircle(c.getCircle.radius, c.getCircle.x, c.getCircle.y)
        case poly:PolygonMapObject => shape = getPolygon(poly.getPolygon.getVertices)
        case c:EllipseMapObject => shape = getCircle(c.getEllipse.width, c.getEllipse.x, c.getEllipse.y)
        case _ => {
          println("couldn't match obstacles : " + obstacle)
        }
      }
      val bd = new BodyDef()
      bd.`type` = BodyType.StaticBody
      val body = Physic.world.createBody(bd)
      body.createFixture(shape, 1).setUserData(new Wall)
      shape.dispose()
    }
  }

  def getRectangle(rectangle: Rectangle, ratio: Float): Shape = {
    val polygon = new PolygonShape()
    val center = new Vector2((rectangle.x + rectangle.width * 0.5f) / ratio, (rectangle.y + rectangle.height * 0.5f) / ratio)
    polygon.setAsBox((rectangle.width * 0.5f) / ratio, (rectangle.height * 0.5f) / ratio, center, 0.0f)
    polygon
  }

  def getCircle(radius: Float, x: Float, y: Float): Shape = {
    val circleShape = new CircleShape()
    circleShape.setRadius(radius)
    circleShape.setPosition(new Vector2(x, y))
    circleShape
  }

  def getPolygon(vertices: scala.Array[Float]): Shape = {
    val polygon = new PolygonShape()
    polygon.set(vertices)
    polygon
  }

}

object Physic {
  val world = new World(Vector2.Zero, true)
  val timestep = 1/60f
  val velocityIteration = 6
  val positionIteration = 2
}