package be.julien.squarehole

import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.{Game, Gdx}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import stuff.{Bullet, Player}

import scala.collection.mutable

class Squarehole extends Game {

  val worldWidth = 160
  val worldHeight = 100
  val player = new Player
  var cam: OrthographicCamera = null
  var shapeRender: ShapeRenderer = null
  val enemyBullets = new mutable.MutableList[Bullet]()

  override def create(): Unit = {
    shapeRender = new ShapeRenderer()
    shapeRender.setAutoShapeType(true)
    cam = new OrthographicCamera(worldWidth, worldHeight)
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
    cam.update()
    Bullet.squarehole = this
  }

  override def render(): Unit = {
    val delta = Gdx.graphics.getDeltaTime
    Squarehole.time += delta
    cam.update()
    shapeRender.setProjectionMatrix(cam.combined)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    player.act(delta)
    for (bullet <- enemyBullets)
      bullet.act(delta)

    shapeRender.begin()
    player.draw(shapeRender)
    for (bullet <- enemyBullets)
      bullet.draw(shapeRender)
    shapeRender.end()
  }

  def addBullet(bullet: Bullet) = {
    enemyBullets += bullet
  }

}

object Squarehole {
  var time = 0f
}
