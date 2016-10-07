package event

/**
  * Created by julien on 07/10/16.
  */
trait Listener {

  def receive(event: Event) = {}
  def receive(createBullet: CreateBullet) = {}

}
