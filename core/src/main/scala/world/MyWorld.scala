package world

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import entities.{Entity, Player}
import event.{EventSystem, EventTypes, Listener, RemoveEnemy}

/**
  * Created by julien on 08/10/16.
  */
class MyWorld extends Listener{

  val player = new Player().init(new Vector2(2,2))
  val entities = new Array[Entity]()

  EventSystem.heyListen(this, EventTypes.removeEnemy)

  override def receive(removeEnemy: RemoveEnemy) = {
    entities.removeValue(removeEnemy.enemy, true)
  }

  def add(entity: Entity) = entities.add(entity)

  def act(delta: Float) = {
    MyWorld.time += delta
    player.act(delta)
    for (i <- 0 until entities.size) {
      val entity = entities.get(i)
      entity.act(delta)
      //      entity.draw(shapeRender)
    }
  }

}

object MyWorld {
  val width = 16
  val height = 10
  var time = 0f
}
