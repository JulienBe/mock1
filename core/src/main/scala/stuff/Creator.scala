package stuff

import entities.Bullet
import event.{CreateBullet, EventSystem, EventTypes, Listener}

/**
  * Created by julien on 07/10/16.
  */
class Creator extends Listener {

  def link() = EventSystem.heyListen(this, EventTypes.objectCreation)

  override def receive(createBullet: CreateBullet) = {
    Bullet.add(createBullet.pos, createBullet.dir)
  }

}
