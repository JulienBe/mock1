package event

import com.badlogic.gdx.utils.Array

import scala.collection.mutable

/**
  * Created by julien on 07/10/16.
  */
object EventSystem {

  val events: Array[Event] = new Array[Event]()
  val listeners: Map[Int, mutable.MutableList[Listener]] = Map(
    EventTypes.createBullet -> new mutable.MutableList[Listener],
    EventTypes.createEnemy -> new mutable.MutableList[Listener],
    EventTypes.collision -> new mutable.MutableList[Listener],
    EventTypes.wallCollision -> new mutable.MutableList[Listener],
    EventTypes.removeEnemy -> new mutable.MutableList[Listener]
  )

  def heyListen(listener: Listener, eventType: Int) = {
    val listenersList = listeners.get(eventType)
    if (listenersList.isDefined)
      listenersList.get += listener
  }

  def event(event: Event) = {
    events.add(event)
  }

  def act() = {
    for (i <- 0 until events.size)
      dispatch(events.get(i))
    events.clear()
  }

  def dispatch(event: Event) = event match {
    case CreateObject(pos) => println("ccc!")
    case RemoveEnemy(enemy) => listeners(EventTypes.removeEnemy).foreach(_.receive(enemy))
    case CreateBullet(pos, dir) => listeners(EventTypes.createBullet).foreach(_.receive(event.asInstanceOf[CreateBullet]))
    case CreateEnemy(clazz, pos) => listeners(EventTypes.createEnemy).foreach(_.receive(event.asInstanceOf[CreateEnemy], pos))
    case Collision(entityA, entityB) => listeners(EventTypes.collision).foreach(_.receive(entityA, entityB))
    case WallCollision(entity) => listeners(EventTypes.wallCollision).foreach(_.receive(entity))
    case _ => println("??? " + event)
  }

}
