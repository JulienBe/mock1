package assets

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.objects.{EllipseMapObject, PolygonMapObject, RectangleMapObject}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.utils.Array
import physic.Physic
import world.Wall

/**
  * Created by julien on 11/10/16.
  */
class MapMan(assetMan: AssetMan) {

  val walls = new Array[Wall]()
  val wallTexture = assetMan.square
  val tiledMapRenderer = new OrthogonalTiledMapRenderer(assetMan.tiledMap, MapMan.scale)

  def bodyFromMap(tiledMap: TiledMap, pixelPerTile: Float = 16): Unit = {
    processLayer(tiledMap, pixelPerTile, "Obstacles", addObstacles)
    processLayer(tiledMap, pixelPerTile, "Wall", addWall)
  }

  def processLayer(tiledMap: TiledMap, pixelPerTile: Float, name: String, tag: (Fixture, Shape) => Unit) = {
    val obstacles = tiledMap.getLayers.get(name).getObjects.iterator()
    while (obstacles.hasNext) {
      val obstacle = obstacles.next()
      var shape: Shape = null
      obstacle match {
        case rect: RectangleMapObject => shape = Physic.getRectangle(rect.getRectangle, pixelPerTile)
        case poly: PolygonMapObject => shape = Physic.getPolygon(poly.getPolygon.getVertices)
        case c: EllipseMapObject => shape = Physic.getCircle(c.getEllipse.width / 2, c.getEllipse.x, c.getEllipse.y, pixelPerTile)
        case _ => {
          println("couldn't match obstacles : " + obstacle)
        }
      }
      val bd = new BodyDef()
      bd.`type` = BodyType.StaticBody
      val body = Physic.world.createBody(bd)

      handleShape(shape, body, tag)
    }
  }

  def addObstacles(fixture: Fixture, shape: Shape) = fixture.setUserData("Nothing")

  def addWall(fixture: Fixture, shape: Shape) = {
    val wall = Wall.instantiate(shape.asInstanceOf[PolygonShape], assetMan.square)
    walls.add(wall)
    fixture.setUserData(wall)
  }

  def handleShape(shape: Shape, body: Body, tag: (Fixture, Shape) => Unit) = {
    println("body : " + body + " shape : " + shape)
    val fixture = body.createFixture(shape, 1)
    val filter = new Filter
    filter.categoryBits = Physic.otherCategory
    filter.maskBits = Physic.otherMask
    fixture.setFilterData(filter)
    shape.dispose()
  }

  def render(camera: OrthographicCamera, spriteBatch: SpriteBatch, delta: Float): Unit = {
//    tiledMapRenderer.setView(camera)
//    tiledMapRenderer.render()
    for (i <- 0 until walls.size)
      walls.get(i).draw(spriteBatch, delta)
  }

}

object MapMan {
  val scale = 1f/16f
  val map1 = "mock1"
}
