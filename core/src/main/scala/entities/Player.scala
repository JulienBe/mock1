package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import event.{CreateBullet, EventSystem}
import stuff.World

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
    EventSystem.event(new CreateBullet(body.getPosition, bulletDirection))
  }

  override def act(delta: Float) = {
    mapVectorToDir(Keys.UP, Keys.DOWN, Keys.LEFT, Keys.RIGHT, bulletDirection)

    if (nextShot < World.time && (bulletDirection.x != 0 || bulletDirection.y != 0)) {
      nextShot = World.time + Player.firerate
      fire()
    }
    mapVectorToDir(Keys.Z, Keys.S, Keys.Q, Keys.D, dir)
    body.setLinearVelocity(dir.scl(speed() * delta))
    Player.position.set(body.getPosition)
  }

  def draw(shapeRender: ShapeRenderer): Unit = {
  }

  def mapVectorToDir(up: Int, down: Int, left: Int, right: Int, vector2: Vector2) = {
    vector2.set(0, 0)
    if (Gdx.input.isKeyPressed(up))      vector2.y += 1
    if (Gdx.input.isKeyPressed(down))    vector2.y -= 1
    if (Gdx.input.isKeyPressed(left))    vector2.x -= 1
    if (Gdx.input.isKeyPressed(right))   vector2.x += 1
  }

}

object Player {
  val width = .5f
  val halfWidth = width / 2
  val firerate = 0.1f
  val speed = 1000f
  val density = 0.05f
  val friction = 2f
  val restitution = 0.6f
  var position = new Vector2()
}
