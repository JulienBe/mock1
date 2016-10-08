package entities
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import event.{EventSystem, RemoveEnemy}

/**
  * Created by julein on 08/10/16.
  */
class Enemy extends Entity {

  override def width(): Float = Enemy.width
  override def speed(): Float = Enemy.speed
  override def damping(): Float = Enemy.damping
  override def density(): Float = Enemy.density
  override def friction(): Float = Enemy.friction
  override def restitution(): Float = Enemy.restitution

  override def draw(shapeRenderer: ShapeRenderer) = {
  }

  override def act(delta: Float) = {
    dir = new Vector2(Player.position)
    dir.sub(body.getPosition).nor
    super.act(delta)
  }

  override def touchedBy(entityA: Entity) = {
    if (entityA.isInstanceOf[PlayerBullet]) {
      EventSystem.event(new RemoveEnemy(this))
      super.destroy()
    }
  }
}

object Enemy {
  val speed = 0.5f
  val damping = 5f
  val friction = 4f
  val density = 0.05f
  val restitution = 0.6f
  val width = .3f
  val halfWidth = width / 2f

  def add(enemy: Class[Enemy], pos: Vector2) = {
    Entity.add(enemy.newInstance().init(pos))
  }
}