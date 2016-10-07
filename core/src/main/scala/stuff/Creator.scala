package stuff

import event.{CreateBullet, EventSystem, EventTypes, Listener}

/**
  * Created by julien on 07/10/16.
  */
class Creator extends Listener {

  def link() = EventSystem.heyListen(this, EventTypes.objectCreation)

  override def receive(createBullet: CreateBullet): Unit = {
    println("lest shoot")
  }

}
