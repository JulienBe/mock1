package assets

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.objects.{CircleMapObject, EllipseMapObject, PolygonMapObject, RectangleMapObject}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.{BodyDef, Filter, Shape}
import physic.Physic
import stuff.Wall

/**
  * Created by julien on 11/10/16.
  */
class MapMan(assetMan: AssetMan) {

  val tiledMapRenderer = new OrthogonalTiledMapRenderer(assetMan.tiledMap, MapMan.scale)

  def bodyFromMap(tiledMap: TiledMap, pixelPerTile: Float = 16): Unit = {
    val obstacles = tiledMap.getLayers.get("Obstacles").getObjects.iterator()
    while (obstacles.hasNext) {
      val obstacle = obstacles.next()
      var shape: Shape = null
      obstacle match {
        case rect:RectangleMapObject => shape = Physic.getRectangle(rect.getRectangle, pixelPerTile)
        case c:CircleMapObject => shape = Physic.getCircle(c.getCircle.radius, c.getCircle.x, c.getCircle.y, pixelPerTile)
        case poly:PolygonMapObject => shape = Physic.getPolygon(poly.getPolygon.getVertices)
        case c:EllipseMapObject => shape = Physic.getCircle(c.getEllipse.width / 2, c.getEllipse.x, c.getEllipse.y, pixelPerTile)
        case _ => {
          println("couldn't match obstacles : " + obstacle)
        }
      }
      val bd = new BodyDef()
      bd.`type` = BodyType.StaticBody
      val body = Physic.world.createBody(bd)
      val fixture = body.createFixture(shape, 1)
      fixture.setUserData(new Wall)
      val filter = new Filter
      filter.categoryBits = Physic.otherCategory
      filter.maskBits = Physic.otherMask
      fixture.setFilterData(filter)
      shape.dispose()
    }
  }

  def render(camera: OrthographicCamera): Unit = {
    tiledMapRenderer.setView(camera)
    tiledMapRenderer.render()
  }

}

object MapMan {
  val scale = 1f/16f
  val map1 = "mock1"
}