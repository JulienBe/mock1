package stuff

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import event.{EventSystem, ObjectCreation}

/**
  * Created by julien on 21/09/16.
  */
class Bullet {

  val speed = 800f
  val width = 1f
  val halfWidth = width / 2
  var position = new Vector2
  var dir = new Vector2

  def act(delta: Float) = {
    position.mulAdd(dir, speed * delta)
  }

  def init(position: Vector2, bulletDirection: Vector2) = {
    this.position.set(position.x - halfWidth, position.y - halfWidth)
    this.dir.set(bulletDirection)
    this
  }

  def draw(shapeRender: ShapeRenderer): Unit = {
    shapeRender.circle(position.x - halfWidth, position.y - halfWidth, width, 8)
  }
}

object Bullet {
  var squarehole: Squarehole = null

  def add(position: Vector2, bulletDirection: Vector2) = {
    val bullet = new Bullet().init(position, bulletDirection)
    EventSystem.event(new ObjectCreation)
  }
}
