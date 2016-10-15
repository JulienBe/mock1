package assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
  * Created by julien on 15/10/16.
  */
class AssetMan(mapName: String) {

  val assetManager = new AssetManager()
  val tiledMap = new TmxMapLoader().load("tmx/" + mapName + ".tmx")

}
