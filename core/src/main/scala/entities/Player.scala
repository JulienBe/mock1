package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import event.{CreateBullet, EventSystem}

/**
  * Created by julien on 21/09/16.
  */
class Player extends Entity {

  val width = 5f
  val halfWidth = width / 2
  val speed = 100f
  val firerate = 0.1f
  var bulletDirection = new Vector2()
  var nextShot = 0f

  def fire() = {
    bulletDirection.nor()
    EventSystem.event(new CreateBullet(position, bulletDirection))
  }

  override def act(delta: Float) = {
    if (Gdx.input.isKeyPressed(Keys.Z))      position.y += speed * delta
    if (Gdx.input.isKeyPressed(Keys.S))      position.y -= speed * delta
    if (Gdx.input.isKeyPressed(Keys.Q))      position.x -= speed * delta
    if (Gdx.input.isKeyPressed(Keys.D))      position.x += speed * delta

    bulletDirection.set(Vector2.Zero)
    if (Gdx.input.isKeyPressed(Keys.UP))     bulletDirection.y = 1
    if (Gdx.input.isKeyPressed(Keys.DOWN))   bulletDirection.y = -1
    if (Gdx.input.isKeyPressed(Keys.LEFT))   bulletDirection.x = -1
    if (Gdx.input.isKeyPressed(Keys.RIGHT))  bulletDirection.x = 1

    if (nextShot < Squarehole.time && (bulletDirection.x != 0 || bulletDirection.y != 0)) {
      nextShot = Squarehole.time + firerate
      fire()
    }
    Player.position.set(position)
  }

  def draw(shapeRender: ShapeRenderer): Unit = {
    shapeRender.circle(position.x - halfWidth, position.y - halfWidth, width, 8)
  }
}

object Player {
  var position = new Vector2()
}
