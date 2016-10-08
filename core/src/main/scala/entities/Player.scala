package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import event.{CreateBullet, EventSystem}

/**
  * Created by julien on 21/09/16.
  */
class Player extends Entity {

  var bulletDirection = new Vector2()
  var nextShot = 0f

  override def width(): Float = Player.width
  override def speed(): Float = Player.speed
  override def density(): Float = Player.density
  override def friction(): Float = Player.friction
  override def restitution(): Float = Player.restitution
  override def bodyType(): BodyType = BodyType.KinematicBody

  def fire() = {
    bulletDirection.nor()
    EventSystem.event(new CreateBullet(position, bulletDirection))
  }

  override def act(delta: Float) = {
    dir.set(0, 0)
    if (Gdx.input.isKeyPressed(Keys.Z))      dir.y += 1
    if (Gdx.input.isKeyPressed(Keys.S))      dir.y -= 1
    if (Gdx.input.isKeyPressed(Keys.Q))      dir.x -= 1
    if (Gdx.input.isKeyPressed(Keys.D))      dir.x += 1

    bulletDirection.set(Vector2.Zero)
    if (Gdx.input.isKeyPressed(Keys.UP))     bulletDirection.y = 1
    if (Gdx.input.isKeyPressed(Keys.DOWN))   bulletDirection.y = -1
    if (Gdx.input.isKeyPressed(Keys.LEFT))   bulletDirection.x = -1
    if (Gdx.input.isKeyPressed(Keys.RIGHT))  bulletDirection.x = 1

    if (nextShot < Squarehole.time && (bulletDirection.x != 0 || bulletDirection.y != 0)) {
      nextShot = Squarehole.time + Player.firerate
      fire()
    }
    body.setLinearVelocity(dir.scl(speed() * delta))
    Player.position.set(body.getPosition)
  }

  def draw(shapeRender: ShapeRenderer): Unit = {
    shapeRender.circle(position.x - Player.halfWidth, position.y - Player.halfWidth, width, 8)
  }

}

object Player {
  val width = 5f
  val halfWidth = width / 2
  val firerate = 0.1f
  val speed = 10000f
  val density = 0.05f
  val friction = 2f
  val restitution = 0.6f
  var position = new Vector2()
}
