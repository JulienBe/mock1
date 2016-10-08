package entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

/**
  * Created by julien on 21/09/16.
  */
class Bullet extends Entity {
  override def width(): Float = Bullet.width
  override def speed(): Float = Bullet.speed
  override def density(): Float = Bullet.density
  override def friction(): Float = Bullet.friction
  override def restitution(): Float = Bullet.restitution

  def init(position: Vector2, bulletDirection: Vector2) = {
    this.position.set(position.x - Bullet.halfWidth, position.y - Bullet.halfWidth)
    this.dir.set(bulletDirection)
    this
  }

  def draw(shapeRender: ShapeRenderer) = {
    shapeRender.circle(position.x - Bullet.halfWidth, position.y - Bullet.halfWidth, Bullet.width, 8)
  }

  override def createBody(): Body = {null}

}

case class BulletCreation(pos: Vector2, dir: Vector2)

object Bullet {
  val speed = 800f
  val density = 0.05f
  val friction = 2f
  val restitution = 0.6f
  val width = 1f
  val halfWidth = width / 2

  def add(position: Vector2, bulletDirection: Vector2) = Entity.add(new Bullet().init(position, bulletDirection))
}
