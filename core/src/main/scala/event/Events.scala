package event

/**
  * Created by julien on 07/10/16.
  */

trait MyEvent

case class ObjectCreation() extends MyEvent

object Events {
  val objectCreation = 0
}
