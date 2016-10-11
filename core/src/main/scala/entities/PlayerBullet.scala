package entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
  * Created by julien on 21/09/16.
  */
class PlayerBullet extends Entity {
  override def width(): Float = PlayerBullet.width
  override def speed(): Float = PlayerBullet.speed
  override def density(): Float = PlayerBullet.density
  override def friction(): Float = PlayerBullet.friction
  override def restitution(): Float = PlayerBullet.restitution

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
  val friction = 0f
  val restitution = 1f
  val width = .1f
  val halfWidth = width / 2

  def add(position: Vector2, bulletDirection: Vector2) = Entity.add(new PlayerBullet().init(position, bulletDirection))
}