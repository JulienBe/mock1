package stuff

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable

/**
  * Created by julien on 06/10/16.
  */
abstract class Entity(pos: Vector2, val health: Int = 100, val entityId: Int) {
  def copy(newHealth: Int)
  def display(shapeRender: ShapeRenderer)
  def width: Float
  def height: Float
}