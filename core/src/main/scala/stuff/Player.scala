package stuff

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
  * Created by julien on 21/09/16.
  */
class Player {

  val width = 5f
  val halfWidth = width / 2
  val speed = 100f
  var position = new Vector2()

  def act(delta: Float) = {
    if (Gdx.input.isKeyPressed(Keys.Z))      position.y += speed * delta
    if (Gdx.input.isKeyPressed(Keys.S))      position.y -= speed * delta
    if (Gdx.input.isKeyPressed(Keys.Q))      position.x -= speed * delta
    if (Gdx.input.isKeyPressed(Keys.D))      position.x += speed * delta
  }

  def draw(shapeRender: ShapeRenderer): Unit = {
    shapeRender.circle(position.x - halfWidth, position.y - halfWidth, width, 8)
  }
}
