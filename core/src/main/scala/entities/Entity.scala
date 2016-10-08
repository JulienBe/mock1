package entities

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
  * Created by julein on 08/10/16.
  */
abstract class Entity {

  var position = new Vector2
  var dir = new Vector2

  def speed(): Float
  def draw(shapeRenderer: ShapeRenderer)

  def act(delta: Float) = {
    position.mulAdd(dir, speed() * delta)
  }

}

object Entity {
  var squarehole: Squarehole = null

  def add(entities: Entity) = squarehole.add(entities)
}