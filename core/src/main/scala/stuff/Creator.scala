package stuff

import com.badlogic.gdx.math.Vector2
import entities.{Enemy, PlayerBullet}
import event._

/**
  * Created by julien on 07/10/16.
  */
class Creator extends Listener {

  def init() = {
    EventSystem.heyListen(this, EventTypes.createBullet)
    EventSystem.heyListen(this, EventTypes.createEnemy)
  }

  override def receive(createBullet: CreateBullet) = {
    PlayerBullet.add(createBullet.pos, createBullet.dir)
  }

  override def receive(createEnemy: CreateEnemy, pos: Vector2) = {
    Enemy.add(createEnemy.enemy, pos)
  }
}
