package stuff

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
  * Created by julien on 11/10/16.
  * For the moment, it loads and reader. yeah
  */
class MapMaker(val mapName: String) {

  val tiledMap = new TmxMapLoader().load("tmx/" + mapName + ".tmx")
  val tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, MapMaker.scale)

  def render(camera: OrthographicCamera): Unit = {
    tiledMapRenderer.setView(camera)
    tiledMapRenderer.render()
  }

}

object MapMaker {
  val scale = 1f/16f
}
