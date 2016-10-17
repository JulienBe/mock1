package event

import com.badlogic.gdx.math.Vector2
import entities.{Enemy, Entity}

/**
  * Created by julien on 07/10/16.
  */
trait Listener {


  def receive(enemy: Enemy) = {}
  def receive(event: Event) = {}
  def receive(createBullet: CreateBullet) = {}
  def receive(createEnemy: CreateEnemy, pos: Vector2) = {}
  def receive(removeEnemy: RemoveEnemy) = {}
  def receive(entityA: Entity, entityB: Entity) = {}
  def receive(entity: Entity) = {}

}
