package event

/**
  * Created by julien on 07/10/16.
  */

trait Event

case class ObjectCreation() extends Event

object Events {
  val objectCreation = 0
}
