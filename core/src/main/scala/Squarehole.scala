package be.julien.squarehole

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.{Game, Gdx}
import event.EventSystem
import stuff.{Creator, Entity, Player}

import scala.collection.mutable

class Squarehole extends Game {

  val worldWidth = 160
  val worldHeight = 100
  val player = new Player
  var cam: OrthographicCamera = null
  var shapeRender: ShapeRenderer = null

  override def create(): Unit = {
    shapeRender = new ShapeRenderer()
    shapeRender.setAutoShapeType(true)
    cam = new OrthographicCamera(worldWidth, worldHeight)
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
    cam.update()
    Entity.squarehole = this
    new Creator().link()
  }

  override def render(): Unit = {
    val delta = Gdx.graphics.getDeltaTime
    Squarehole.time += delta
    cam.update()
    shapeRender.setProjectionMatrix(cam.combined)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    EventSystem.act()

    player.act(delta)

    shapeRender.begin()
    player.draw(shapeRender)
    for (i <- 0 until entities.size) {
      val entity = entities.get(i)
      entity.act(delta)
      entity.draw(shapeRender)
    }
    shapeRender.end()
  }

  def add(entity: Entity) = entities.add(entity)

}

object Squarehole {
  var time = 0f
}
