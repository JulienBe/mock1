package be.julien.squarehole

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.{Game, Gdx}
import event.{Events, Listener, MyEvent, ObjectCreation}
import stuff.{Bullet, Creator, Player}

import scala.collection.mutable

class Squarehole extends Game {

  val worldWidth = 160
  val worldHeight = 100
  val player = new Player
  var cam: OrthographicCamera = null
  var shapeRender: ShapeRenderer = null
  val events: Array[MyEvent] = new Array[MyEvent]()
  val listeners: Map[Int, mutable.MutableList[Listener]] = Map(
    Events.objectCreation -> new mutable.MutableList[Listener]
  )

  override def create(): Unit = {
    shapeRender = new ShapeRenderer()
    shapeRender.setAutoShapeType(true)
    cam = new OrthographicCamera(worldWidth, worldHeight)
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
    cam.update()
    Bullet.squarehole = this
    new Creator().link(this)
  }

  def dispatch(event: MyEvent) = event match {
    case ObjectCreation() => println("ccc!")
    case _ => println("prout")
  }

  override def render(): Unit = {
    for (i <- 0 until events.size)
      dispatch(events.get(i))
    events.clear()

    val delta = Gdx.graphics.getDeltaTime
    Squarehole.time += delta
    cam.update()
    shapeRender.setProjectionMatrix(cam.combined)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    player.act(delta)
//    for (bullet <- )
//      bullet.act(delta)

    shapeRender.begin()
    player.draw(shapeRender)
//    for (bullet <- enemyBullets)
//      bullet.draw(shapeRender)
    shapeRender.end()
  }

  def heyListen(listener: Listener, eventType: Int) = {
    val listenersList = listeners.get(eventType)
    if (listenersList.isDefined)
      listenersList.get += listener
  }

}

object Squarehole {
  var time = 0f
  def event(event: MyEvent) = events.add(event)
}
