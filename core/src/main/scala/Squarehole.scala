package be.julien.squarehole

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.{Game, Gdx}
import entities.{Enemy, Entity}
import event.{CreateEnemy, EventSystem}
import physic.Physic
import stuff.{Creator, World}

import scala.collection.mutable
import scala.util.Random

class Squarehole extends Game {

  var cam: OrthographicCamera = null
  var shapeRender: ShapeRenderer = null
  var debugRenderer: Box2DDebugRenderer = null
  var physic: Physic = null
  val world = new World()

  override def create() = {
    shapeRender = new ShapeRenderer()
    shapeRender.setAutoShapeType(true)
    cam = new OrthographicCamera(World.width, World.height)
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0)
    cam.update()
    debugRenderer = new Box2DDebugRenderer()
    physic = new Physic(this)
    Entity.init(this)
    new Creator().init()
  }

  override def render() = {
    val delta = Gdx.graphics.getDeltaTime
    cam.update()
    shapeRender.setProjectionMatrix(cam.combined)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    physic.doPhysicsStep(delta)
    EventSystem.act()
    shapeRender.begin()
    world.act(delta)
    shapeRender.end()
    debugRenderer.render(Physic.world, cam.combined)

    if (Physic.world.getBodyCount < 8)
      EventSystem.event(
        new CreateEnemy(classOf[Enemy], new Vector2(Random.nextGaussian().toFloat, Random.nextGaussian().toFloat))
      )
  }

  def add(entity: Entity) = world.add(entity)

}