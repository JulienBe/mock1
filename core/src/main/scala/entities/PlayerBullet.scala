package entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

/**
  * Created by julien on 21/09/16.
  */
class PlayerBullet extends Entity {
  override def width(): Float = PlayerBullet.width
  override def speed(): Float = PlayerBullet.speed
  override def density(): Float = PlayerBullet.density
  override def friction(): Float = PlayerBullet.friction
  override def restitution(): Float = PlayerBullet.restitution
  override def bodyType(): BodyType = BodyType.KinematicBody

  def init(position: Vector2, bulletDirection: Vector2) = {
    body.setTransform(position.x - PlayerBullet.halfWidth, position.y - PlayerBullet.halfWidth, body.getAngle)
    body.setLinearVelocity(bulletDirection.scl(speed()))
    this
  }

  def draw(shapeRender: ShapeRenderer) = {
  }

}

case class BulletCreation(pos: Vector2, dir: Vector2)

object PlayerBullet {
  val speed = 24f
  val density = 0.05f
  val friction = 2f
  val restitution = 0.6f
  val width = .1f
  val halfWidth = width / 2

  def add(position: Vector2, bulletDirection: Vector2) = Entity.add(new PlayerBullet().init(position, bulletDirection))
}