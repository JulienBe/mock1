package event

import com.badlogic.gdx.math.Vector2

/**
  * Created by julien on 07/10/16.
  */

trait Event

case class CreateObject(pos: Vector2) extends Event
case class CreateBullet(pos: Vector2, dir: Vector2) extends Event

object EventTypes {
  val objectCreation = 0
}
