package stuff

import be.julien.squarehole.Squarehole
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
  * Created by julien on 08/10/16.
  */
class Physic(squarehole: Squarehole) {

  var accumulator = 0f

  def doPhysicsStep(deltaTime: Float) {
    // fixed time step
    // max frame time to avoid spiral of death (on slow devices)
    val frameTime = Math.min(deltaTime, 0.25f)
    accumulator += frameTime
    while (accumulator >= Physic.timestep) {
      Physic.world.step(Physic.timestep, Physic.velocityIteration, Physic.positionIteration)
      accumulator -= Physic.timestep
    }
  }
}

object Physic {
  val world = new World(Vector2.Zero, true)
  val timestep = 1/60f
  val velocityIteration = 6
  val positionIteration = 2
}