package entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import lights.PointLight
import physic.Physic

/**
  * Created by julien on 21/09/16.
  */
class PlayerBullet extends Entity {

  var bounceLeft = 2

  val pointLight = new PointLight(Physic.rayHandler, PlayerBullet.rays, PlayerBullet.color, PlayerBullet.lightLength, 0, 0)

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
    pointLight.attachToBody(body)
    this
  }

  def hitWall() = {
    bounceLeft = bounceLeft - 1
    println("bounce : " + bounceLeft)
    if (bounceLeft <= 0)
      destroy()
  }

  override def destroy() = {
    pointLight.setActive(false)
    super.destroy()
  }
}

object PlayerBullet {
  val rays = 6
  val color = new Color(.0f, .5f, 1, 1)
  val lightLength = .5f

  val speed = 24f
  val density = 0f
  val friction = 0f
  val restitution = 1f
  val width = .1f
  val halfWidth = width / 2

  def add(position: Vector2, bulletDirection: Vector2) = Entity.add(new PlayerBullet().init(position, bulletDirection))
}