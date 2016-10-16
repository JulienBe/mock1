package stuff

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import world.MyWorld

/**
  * Created by julien on 15/10/16.
  */
class Cameraman(val width: Float, val height: Float) {

  val cam = new OrthographicCamera(MyWorld.width, MyWorld.height)
  cam.setToOrtho(false, MyWorld.width, MyWorld.height)
  cam.update()

  def update(target: Vector2, delta: Float) = {
    val current = new Vector2(cam.position.x, cam.position.y)
    current.lerp(target, Math.min(delta * current.len2(), 1))
    cam.position.set(current, cam.position.z)
    cam.update()
  }
}

object Cameraman {
  val speed = 5
}