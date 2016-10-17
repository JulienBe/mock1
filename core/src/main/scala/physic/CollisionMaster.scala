package physic

import com.badlogic.gdx.physics.box2d.{Contact, ContactImpulse, ContactListener, Manifold}
import entities.{Entity, PlayerBullet}
import event.{Collision, EventSystem}
import world.WallTag

/**
  * Created by julien on 08/10/16.
  */
class CollisionMaster extends ContactListener {
  override def preSolve(contact: Contact, oldManifold: Manifold) = {}
  override def postSolve(contact: Contact, impulse: ContactImpulse): Unit = {}
  override def endContact(contact: Contact): Unit = {}

  override def beginContact(c: Contact): Unit = {
    if (c.getFixtureA.getUserData.isInstanceOf[Entity] && c.getFixtureB.getUserData.isInstanceOf[Entity])
      EventSystem.event(new Collision(c.getFixtureA.getUserData.asInstanceOf[Entity], c.getFixtureB.getUserData.asInstanceOf[Entity]))

    // let's try breaking the messages and use the physic
    if (c.getFixtureB.getUserData.isInstanceOf[WallTag]) {
      c.getFixtureA.getUserData match {
        case pb:PlayerBullet => pb.hitWall()
        case _ =>
      }
    }
    if (c.getFixtureA.getUserData.isInstanceOf[WallTag]) {
      c.getFixtureB.getUserData match {
        case pb:PlayerBullet => pb.hitWall()
        case _ =>
      }
    }
//      EventSystem.event(new WallCollision(c.getFixtureA.getUserData.asInstanceOf[Entity]))

  }

}