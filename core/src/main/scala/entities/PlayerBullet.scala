package entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import physic.Physic

/**
  * Created by julien on 21/09/16.
  */
class PlayerBullet extends Entity {

  override def damping(): Float = 0
  override def width(): Float = PlayerBullet.width
  override def speed(): Float = PlayerBullet.speed
  override def density(): Float = PlayerBullet.density
  override def friction(): Float = PlayerBullet.friction
  override def restitution(): Float = PlayerBullet.restitution
  override def mask(): Short = Physic.playerMask
  override def category(): Short = Physic.playerCategory

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
  val density = 0f
  val friction = 0f
  val restitution = 1f
  val width = .1f
  val halfWidth = width / 2

  def add(position: Vector2, bulletDirection: Vector2) = Entity.add(new PlayerBullet().init(position, bulletDirection))
}