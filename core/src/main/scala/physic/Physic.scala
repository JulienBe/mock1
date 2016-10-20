package physic

import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.utils.Array
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
  val bodiesToDeactivate = new Array[Body]()

  val world = new World(Vector2.Zero, true)
  world.setContactListener(new CollisionMaster)
  val rayHandler = new RayHandler(world)
  rayHandler.setAmbientLight(Color.DARK_GRAY)

  val timestep = 1/60f
  val velocityIteration = 6
  val positionIteration = 2

  var accumulator = 0f

  def render(delta: Float, cam: OrthographicCamera) = {
    Physic.rayHandler.setCombinedMatrix(cam)
    Physic.rayHandler.updateAndRender()
  }

  def doForAllBodies(bodyToUnit: (Body) => Unit, bodies: Array[Body]) = {
    for (i <- 0 until bodies.size)
      bodyToUnit(bodies.get(i))
    bodies.clear()
  }

  def sleepAllBodies(bodies: Array[Body]) = {
    for (i <- 0 until bodies.size)
      bodies.get(i).setActive(false)
    bodies.clear()
  }

  def doPhysicsStep(deltaTime: Float) {
    // fixed time step
    // max frame time to avoid spiral of death (on slow devices)
    val frameTime = Math.min(deltaTime, 0.25f)
    accumulator += frameTime
    while (accumulator >= timestep) {
      world.step(timestep, velocityIteration, positionIteration)
      accumulator -= timestep
    }
    doForAllBodies(world.destroyBody(_), bodiesToClean)
    sleepAllBodies(bodiesToDeactivate)
    val bodies = new Array[Body]()
    world.getBodies(bodies)
  }

  def bodyToClean(body: Body) = bodiesToClean.add(body)
  def bodyToSleep(body: Body) = bodiesToDeactivate.add(body)

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

  // even are supposed to be horizontal and the following is the corresponding vertical
  // will slice the rectangle into 4 shapes
  // Could more efficiently sliced by sharing walls, but seems easier to understand. tbd if it works with polygon shape vertices
  // 7\----------------/5
  // | 8--------------6 |
  // | |              | |
  // | |              | |
  // | 4______________3 |
  // 1/________________\2
  // and it ends up being useless. But I like it :( Don't code when you're tired (which is exactly what I'm currently doing)
  def getPolygons(r: Rectangle, ppt: Float, offset: Float): Array[Shape] = {
    val x = r.x / ppt
    val y = r.y / ppt
    val w = r.width / ppt
    val h = r.height / ppt
    val p1 = (x,                  y)
    val p2 = (x + w,              y)
    val p3 = (x + w * (1-offset), y + h * offset)
    val p4 = (x + w * offset,     y + h * offset)
    val p5 = (x + w,              y + h)
    val p6 = (x + w * (1-offset), y + h * (1-offset))
    val p7 = (x,                  y + h)
    val p8 = (x + w * offset,     y + h * (1-offset))
    getPolygons(
      scala.Array(
        p1._1, p1._2, p2._1, p2._2, p3._1, p3._2, p4._1, p4._2,
        p2._1, p2._2, p5._1, p5._2, p6._1, p6._2, p3._1, p3._2,
        p5._1, p5._2, p7._1, p7._2, p8._1, p8._2, p6._1, p6._2,
        p7._1, p7._2, p1._1, p1._2, p4._1, p4._2, p8._1, p8._2
        ),
      ppt
    )
  }

  def getPolygons(vertices: scala.Array[Float], ppt: Float): Array[Shape] = {
    val shapes = new Array[Shape]()
    for (i <- 0 until vertices.length by 8) {
      val polygon = new PolygonShape()
      polygon.set(scala.Array(
        vertices(i), vertices(i + 1), vertices(i + 2), vertices(i + 3),
        vertices(i + 4), vertices(i + 5), vertices(i + 6), vertices(i + 7)
      ))
      shapes.add(polygon)
    }
    shapes
  }

}
