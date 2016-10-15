package be.julien.squarehole

import assets.{AssetMan, MapMan}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.{Game, Gdx}
import entities.{Enemy, Entity}
import event.{CreateEnemy, EventSystem}
import physic.Physic
import stuff.{Creator, MyWorld}

import scala.collection.mutable
import scala.util.Random

class Squarehole extends Game {

  var cam: OrthographicCamera = null
  var debugRenderer: Box2DDebugRenderer = null
  var physic: Physic = null
  var world: MyWorld = null
  var assetMan: AssetMan = null
  var mapMan: MapMan = null

  override def create() = {
    cam = new OrthographicCamera(MyWorld.width, MyWorld.height)
    cam.setToOrtho(false, MyWorld.width, MyWorld.height)
    cam.update()
    debugRenderer = new Box2DDebugRenderer()
    physic = new Physic()
    world = new MyWorld()
    Entity.init(this)
    new Creator().init()
    assetMan = new AssetMan(MapMan.map1)
    mapMan = new MapMan(assetMan)
    mapMan.bodyFromMap(assetMan.tiledMap)
  }

  override def render() = {
    val delta = Gdx.graphics.getDeltaTime
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    cam.update()

    physic.doPhysicsStep(delta)
    EventSystem.act()
    world.act(delta)
    mapMan.render(cam)
    physic.render(delta, cam)
    debugRenderer.render(Physic.world, cam.combined)

    if (Physic.world.getBodyCount < 8)
      EventSystem.event(
        new CreateEnemy(classOf[Enemy], new Vector2(Random.nextGaussian().toFloat, Random.nextGaussian().toFloat))
      )
  }

  def add(entity: Entity) = world.add(entity)

}