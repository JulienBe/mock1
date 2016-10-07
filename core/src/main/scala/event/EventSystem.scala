package event

import com.badlogic.gdx.utils.Array

import scala.collection.mutable

/**
  * Created by julien on 07/10/16.
  */
object EventSystem {

  val events: Array[Event] = new Array[Event]()
  val listeners: Map[Int, mutable.MutableList[Listener]] = Map(
    Events.objectCreation -> new mutable.MutableList[Listener]
  )

  def heyListen(listener: Listener, eventType: Int) = {
    val listenersList = listeners.get(eventType)
    if (listenersList.isDefined)
      listenersList.get += listener
  }

  def event(event: Event) = events.add(event)

  def act() = {
    for (i <- 0 until events.size)
      dispatch(events.get(i))
    events.clear()
  }

  def dispatch(event: Event) = event match {
    case ObjectCreation() => println("ccc!")
    case _ => println("prout")
  }

}
