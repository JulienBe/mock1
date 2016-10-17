package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.utils.Pool
import event.{EventSystem, EventTypes, Listener}
import physic.Physic

/**
  * Created by julien on 08/10/16.
  */
abstract class Entity() {

  val body = createBody()
  var dir = new Vector2

  def free()
  def mask(): Short = Physic.otherMask
  def category(): Short = Physic.otherCategory
  def speed(): Float
  def width(): Float
  def damping(): Float = 1f
  def density(): Float
  def friction(): Float
  def restitution(): Float
  def bodyType(): BodyType = BodyType.DynamicBody
  def draw(shapeRenderer: ShapeRenderer) = {}
  def touchedBy(entityA: Entity) = {}

  def init(pos: Vector2) = {
    body.setTransform(pos.x, pos.y, body.getAngle)
    this
  }

  def destroy() = {
    Physic.bodyToSleep(body)
    free()
  }

  def createBody(): Body = {
    val b = Physic.world.createBody(createBodyDef)
    val shape: Shape = createShape
    createFixture(b, shape)
    shape.dispose()
    bodyCreated()
    b
  }
  def createBodyDef: BodyDef = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = bodyType()
    bodyDef.linearDamping = damping()
    bodyDef
  }
  def createFixture(b: Body, shape: Shape) = {
    val fixtureDef = new FixtureDef()
    fixtureDef.shape = shape
    fixtureDef.density = density()
    fixtureDef.friction = friction()
    fixtureDef.restitution = restitution()
    fixtureDef.filter.categoryBits = category()
    fixtureDef.filter.maskBits = mask()
    val fixture = b.createFixture(fixtureDef)
    fixture.setUserData(this)
  }
  def createShape: CircleShape = {
    val shape = new CircleShape()
    shape.setRadius(width())
    shape
  }
  def bodyCreated() = {}

  def act(delta: Float): Unit = {
    body.applyForceToCenter(dir.scl(speed), true)
  }

}

object Entity extends Listener {
  var squarehole: Squarehole = null

  def init(squarehole: Squarehole) = {
    this.squarehole = squarehole
    EventSystem.heyListen(this, EventTypes.collision)
  }
  def add(entity: Entity) = squarehole.add(entity)

  override def receive(entityA: Entity, entityB: Entity) = {
    entityA.touchedBy(entityB)
    entityB.touchedBy(entityA)
  }
}