package be.julien.squarehole

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.{Game, Gdx}
import entities.{Enemy, Entity, Player}
import event.{CreateEnemy, EventSystem}
import stuff.{Creator, Physic}

import scala.collection.mutable
import scala.util.Random

class Squarehole extends Game {

  val worldWidth = 160
  val worldHeight = 100
  val player = new Player
  var cam: OrthographicCamera = null
  var shapeRender: ShapeRenderer = null
  val entities = new Array[Entity]()
  var debugRenderer: Box2DDebugRenderer = null
  var physic: Physic = null

  override def create() = {
    shapeRender = new ShapeRenderer()
    shapeRender.setAutoShapeType(true)
    cam = new OrthographicCamera(worldWidth, worldHeight)
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
    cam.update()
    debugRenderer = new Box2DDebugRenderer()
    physic = new Physic(this)
    Entity.squarehole = this
    new Creator().init()
  }

  override def render() = {
    val delta = Gdx.graphics.getDeltaTime
    Squarehole.time += delta
    cam.update()
    shapeRender.setProjectionMatrix(cam.combined)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    EventSystem.act()
    physic.doPhysicsStep(delta)

    player.act(delta)

    shapeRender.begin()
    player.draw(shapeRender)
    for (i <- 0 until entities.size) {
      val entity = entities.get(i)
      entity.act(delta)
//      entity.draw(shapeRender)
    }
    shapeRender.end()
    debugRenderer.render(Physic.world, cam.combined)

    if (Physic.world.getBodyCount < 10)
      EventSystem.event(
        new CreateEnemy(classOf[Enemy], new Vector2(Random.nextGaussian().toFloat * 10, Random.nextGaussian().toFloat * 10))
      )
  }

  def add(entity: Entity) = entities.add(entity)

}

object Squarehole {
  var time = 0f
}
