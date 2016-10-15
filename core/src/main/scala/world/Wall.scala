package world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.utils.Array

/**
  * Created by julien on 11/10/16.
  */
class Wall(val origin: Vector2, val end: Vector2, val textureRegion: TextureRegion) {

  val length = Vector2.len(end.x - origin.x, end.y - origin.y)
  val originToEnd = new Vector2(end).sub(origin)
  val angle = originToEnd.angle()

  def draw (spriteBatch: SpriteBatch, delta: Float) = {
    spriteBatch.draw(textureRegion,
      origin.x, origin.y,
      0, 0,
      length, Wall.height,
      1, 1,
      angle, true
    )
  }

}

object Wall {

  val height = 10f

  def fromBox(polygon: PolygonShape, textureRegion: TextureRegion) = {
    val walls = new Array[Wall]()
    for (i <- 0 until polygon.getVertexCount) {
      val origin = new Vector2()
      polygon.getVertex(i, origin)
      val end = new Vector2()
      if (i + 1 == polygon.getVertexCount)
        polygon.getVertex(0, end)
      else
        polygon.getVertex(i + 1, end)

      origin.scl(Gdx.graphics.getWidth / MyWorld.width, Gdx.graphics.getHeight / MyWorld.height)
      end.scl(Gdx.graphics.getWidth / MyWorld.width, Gdx.graphics.getHeight / MyWorld.height)
      walls.add(new Wall(origin, end, textureRegion))
    }
    walls
  }

}