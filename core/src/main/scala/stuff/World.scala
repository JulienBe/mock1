package stuff

import com.badlogic.gdx.utils.Array
import entities.{Entity, Player}
import event.{EventSystem, EventTypes, Listener, RemoveEnemy}

/**
  * Created by julie  n on 08/10/16.
  */
class World extends Listener{

  val player = new Player
  val entities = new Array[Entity]()

  EventSystem.heyListen(this, EventTypes.removeEnemy)

  override def receive(removeEnemy: RemoveEnemy) = {
    entities.removeValue(removeEnemy.enemy, true)
  }

  def add(entity: Entity) = entities.add(entity)

  def act(delta: Float) = {
    World.time += delta
    player.act(delta)
    for (i <- 0 until entities.size) {
      val entity = entities.get(i)
      entity.act(delta)
      //      entity.draw(shapeRender)
    }
  }

}

object World {
  val width = 16
  val height = 10
  var time = 0f
}
