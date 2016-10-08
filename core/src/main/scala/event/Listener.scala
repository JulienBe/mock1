package event

import com.badlogic.gdx.math.Vector2

/**
  * Created by julien on 07/10/16.
  */
trait Listener {

  def receive(event: Event) = {}
  def receive(createBullet: CreateBullet) = {}
  def receive(createEnemy: CreateEnemy, pos: Vector2) = {}

}
