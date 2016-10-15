package physic

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d._
import lights.RayHandler

/**
  * Created by julien on 08/10/16.
  */
class Physic() {

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
    r
  }
  val timestep = 1/60f
  val velocityIteration = 6
  val positionIteration = 2

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