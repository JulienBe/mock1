package world

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
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

  val height = .25f

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

      walls.add(new Wall(origin, end, textureRegion))
    }
    walls
  }

  //4--7--3
  //|     |
  //5     6
  //|     |
  //1--8--2
  def instantiate(shape: PolygonShape, square: AtlasRegion): Wall = {
    val v1 = new Vector2()
    val v2 = new Vector2()
    val v3 = new Vector2()
    val v4 = new Vector2()
    shape.getVertex(0, v1)
    shape.getVertex(1, v2)
    shape.getVertex(2, v3)
    shape.getVertex(3, v4)
    val v5 = new Vector2((v1.x + v4.x) / 2, (v1.y + v4.y) / 2)
    val v6 = new Vector2((v2.x + v3.x) / 2, (v1.y + v3.y) / 2)
    val horizontal = new Vector2(v6).sub(v5)
    val v7 = new Vector2((v4.x + v3.x) / 2, (v4.y + v3.y) / 2)
    val v8 = new Vector2((v1.x + v2.x) / 2, (v1.y + v2.y) / 2)
    val vertical = new Vector2(v7).sub(v8)

    if (vertical.len2() > horizontal.len2()) {
      v8.x += height / 2
      v7.x += height / 2
      new Wall(v8, v7, square)
    } else {
      v5.y -= height / 2
      v6.y -= height / 2
      new Wall(v5, v6, square)
    }
  }

}