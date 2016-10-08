package entities
import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
  * Created by julein on 08/10/16.
  */
class Enemy extends Entity {
  override def speed(): Float = 10

  def init(pos: Vector2) = {
    position.set(pos)
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

  val width = 3f
  val halfWidth = width / 2f

  def add(enemy: Class[Enemy], pos: Vector2) = {
    Entity.add(enemy.newInstance().init(pos))
  }
}
