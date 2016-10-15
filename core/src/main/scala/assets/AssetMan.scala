package assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TmxMapLoader

/**
  * Created by julien on 15/10/16.
  */
class AssetMan(mapName: String) {

  val assetManager = new AssetManager()
  val tiledMap = new TmxMapLoader().load("tmx/" + mapName + ".tmx")
  val atlas = {
    assetManager.load("pack.atlas", classOf[TextureAtlas])
    assetManager.finishLoading()
    assetManager.get("pack.atlas", classOf[TextureAtlas])
  }
  val square = atlas.findRegion("square")

}
