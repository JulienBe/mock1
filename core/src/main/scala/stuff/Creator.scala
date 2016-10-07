package stuff

import be.julien.squarehole.Squarehole
import event.{Events, Listener}

/**
  * Created by julien on 07/10/16.
  */
class Creator extends Listener {

  def link(squarehole: Squarehole) = squarehole.heyListen(this, Events.objectCreation)

}
