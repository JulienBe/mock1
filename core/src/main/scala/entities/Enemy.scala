package entities
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
  * Created by julein on 08/10/16.
  */
class Enemy extends Entity {
  override def width(): Float = Enemy.width
  override def speed(): Float = Enemy.speed
  override def density(): Float = Enemy.density
  override def friction(): Float = Enemy.friction
  override def restitution(): Float = Enemy.restitution

  def init(pos: Vector2) = {
    position.set(pos)
    body.setTransform(pos.x, pos.y, body.getAngle)
    this
  }

  override def draw(shapeRenderer: ShapeRenderer) = {
    shapeRenderer.circle(position.x - Enemy.halfWidth, position.y - Enemy.halfWidth, Enemy.width, 5)
  }

  override def act(delta: Float) = {
    val diff = new Vector2(Player.position)
    diff.sub(position).nor
    dir.set(diff)
    super.act(delta)
  }

}

object Enemy {
  val speed = 1000f
  val density = 0.05f
  val friction = 2f
  val restitution = 0.6f
  val width = 3f
  val halfWidth = width / 2f

  def add(enemy: Class[Enemy], pos: Vector2) = {
    Entity.add(enemy.newInstance().init(pos))
  }
}
