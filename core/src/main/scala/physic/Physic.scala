package physic

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d._
import lights.RayHandler

/**
  * Created by julien on 08/10/16.
  */
object Physic {

  val playerCategory: Short = 0x0001
  val otherCategory: Short = 0x0002
  val playerMask: Short = otherCategory
  val otherMask: Short = (otherCategory | playerCategory).toShort
  val bodiesToClean = new Array[Body]()

  val world = new World(Vector2.Zero, true)
  world.setContactListener(new CollisionMaster)
  val rayHandler = new RayHandler(world)

  val timestep = 1/60f
  val velocityIteration = 6
  val positionIteration = 2

  var accumulator = 0f

  def render(delta: Float, cam: OrthographicCamera) = {
    Physic.rayHandler.setCombinedMatrix(cam)
    Physic.rayHandler.updateAndRender()
  }

  def cleanBodies() = {
    for (i <- 0 until bodiesToClean.size)
      world.destroyBody(bodiesToClean.get(i))
    bodiesToClean.clear()
  }

  def doPhysicsStep(deltaTime: Float) {
    cleanBodies()
    // fixed time step
    // max frame time to avoid spiral of death (on slow devices)
    val frameTime = Math.min(deltaTime, 0.25f)
    accumulator += frameTime
    while (accumulator >= timestep) {
      world.step(timestep, velocityIteration, positionIteration)
      accumulator -= timestep
    }
  }

  def bodyToClean(body: Body) = bodiesToClean.add(body)

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
