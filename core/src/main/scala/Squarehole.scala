package be.julien.squarehole

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.{Game, Gdx}
import event.EventSystem
import stuff.{Bullet, Creator, Player}

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
    Bullet.squarehole = this
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
//    for (bullet <- )
//      bullet.act(delta)

    shapeRender.begin()
    player.draw(shapeRender)
//    for (bullet <- enemyBullets)
//      bullet.draw(shapeRender)
    shapeRender.end()
  }

}

object Squarehole {
  var time = 0f
}
