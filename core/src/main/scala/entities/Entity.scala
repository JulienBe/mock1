package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import stuff.Physic

/**
  * Created by julein on 08/10/16.
  */
abstract class Entity(val position: Vector2 = new Vector2()) {

  val body: Body = createBody()
  var dir = new Vector2

  def speed(): Float
  def width(): Float
  def density(): Float
  def friction(): Float
  def restitution(): Float
  def bodyType(): BodyType = BodyType.DynamicBody
  def draw(shapeRenderer: ShapeRenderer)

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
    bodyDef
  }

  def createFixture(b: Body, shape: Shape): Unit = {
    val fixtureDef = new FixtureDef()
    fixtureDef.shape = shape
    fixtureDef.density = density()
    fixtureDef.friction = friction()
    fixtureDef.restitution = restitution()
    val fixture = b.createFixture(fixtureDef)
  }

  def createShape: CircleShape = {
    val shape = new CircleShape()
    shape.setRadius(width())
    shape
  }

  def act(delta: Float): Unit = {
    body.applyForceToCenter(dir.scl(speed() * delta), true)
  }

}

object Entity {
  var squarehole: Squarehole = null

  def add(entity: Entity) = squarehole.add(entity)
}