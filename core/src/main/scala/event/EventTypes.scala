package event

import com.badlogic.gdx.math.Vector2
import entities.{Enemy, Entity}

/**
  * Created by julien on 07/10/16.
  */
trait Event

case class RemoveEnemy(enemy: Enemy) extends Event
case class CreateObject(pos: Vector2) extends Event
case class CreateBullet(pos: Vector2, dir: Vector2) extends Event
case class Collision(entityA: Entity, entityB: Entity) extends Event
case class WallCollision(entityA: Entity) extends Event
case class CreateEnemy(enemy: Class[Enemy], pos: Vector2) extends Event

object EventTypes {
  val createBullet = 0
  val createEnemy = 1
  val collision = 2
  val removeEnemy = 3
  val wallCollision = 4
}
