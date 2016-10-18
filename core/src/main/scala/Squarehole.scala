package be.julien.squarehole

import assets.{AssetMan, MapMan}
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.{Game, Gdx}
import entities.{Enemy, Entity}
import event.{CreateEnemy, EventSystem}
import physic.Physic
import stuff.{Cameraman, Creator}
import world.MyWorld

import scala.collection.mutable
import scala.util.Random

class Squarehole extends Game {

  var cameraman: Cameraman = null
  var debugRenderer: Box2DDebugRenderer = null
  var world: MyWorld = null
  var assetMan: AssetMan = null
  var mapMan: MapMan = null
  var spriteBatch: SpriteBatch = null

  override def create() = {
    cameraman = new Cameraman(MyWorld.width, MyWorld.height)
    debugRenderer = new Box2DDebugRenderer()
    world = new MyWorld()
    Entity.init(this)
    new Creator().init()
    assetMan = new AssetMan(MapMan.map1)
    mapMan = new MapMan(assetMan)
    mapMan.bodyFromMap(assetMan.tiledMap)
    spriteBatch = new SpriteBatch()
    spriteBatch.setProjectionMatrix(cameraman.cam.combined)
  }

  override def render() = {
    val delta = Gdx.graphics.getDeltaTime
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    cameraman.update(world.player.body.getPosition, delta)
    spriteBatch.setProjectionMatrix(cameraman.cam.combined)

    Physic.doPhysicsStep(delta)
    EventSystem.act()
    world.act(delta)
    spriteBatch.begin()
    mapMan.render(cameraman.cam, spriteBatch, delta)
    spriteBatch.end()
    Physic.render(delta, cameraman.cam)
    debugRenderer.render(Physic.world, cameraman.cam.combined)

    if (Physic.world.getBodyCount < 8)
      EventSystem.event(
        new CreateEnemy(classOf[Enemy], new Vector2(Random.nextGaussian().toFloat, Random.nextGaussian().toFloat))
      )
  }

  def add(entity: Entity) = world.add(entity)

}