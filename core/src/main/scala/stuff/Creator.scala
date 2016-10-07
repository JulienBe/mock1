package stuff

import event.{EventSystem, Events, Listener}

/**
  * Created by julien on 07/10/16.
  */
class Creator extends Listener {

  def link() = EventSystem.heyListen(this, Events.objectCreation)

}
