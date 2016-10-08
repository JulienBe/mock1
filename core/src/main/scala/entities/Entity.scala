package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import event.{EventSystem, EventTypes, Listener}
import physic.Physic

/**
  * Created by julien on 08/10/16.
  */
abstract class Entity() {

  val body: Body = createBody()
  var dir = new Vector2

  def speed(): Float
  def width(): Float
  def damping(): Float = 1f
  def density(): Float
  def friction(): Float
  def restitution(): Float
  def bodyType(): BodyType = BodyType.DynamicBody
  def draw(shapeRenderer: ShapeRenderer)
  def touchedBy(entityA: Entity) = {}

  def init(pos: Vector2) = {
    body.setTransform(pos.x, pos.y, body.getAngle)
    this
  }

  def destroy() = {
    Physic.world.destroyBody(body)
  }

  def createBody(): Body = {
    val b = Physic.world.createBody(createBodyDef)
    val shape: Shape = createShape
    createFixture(b, shape)
    shape.dispose()
    b
  }

  def createBodyDef: BodyDef = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = bodyType()
    bodyDef.linearDamping = damping()
    bodyDef
  }

  def createFixture(b: Body, shape: Shape): Unit = {
    val fixtureDef = new FixtureDef()
    fixtureDef.shape = shape
    fixtureDef.density = density()
    fixtureDef.friction = friction()
    fixtureDef.restitution = restitution()
    val fixture = b.createFixture(fixtureDef)
    fixture.setUserData(this)
  }

  def createShape: CircleShape = {
    val shape = new CircleShape()
    shape.setRadius(width())
    shape
  }

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