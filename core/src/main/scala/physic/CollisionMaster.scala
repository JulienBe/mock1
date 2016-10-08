package physic

import com.badlogic.gdx.physics.box2d.{Contact, ContactImpulse, ContactListener, Manifold}
import entities.Entity
import event.{Collision, EventSystem}

/**
  * Created by julien on 08/10/16.
  */
class CollisionMaster extends ContactListener {
  override def preSolve(contact: Contact, oldManifold: Manifold) = {}
  override def postSolve(contact: Contact, impulse: ContactImpulse): Unit = {}
  override def endContact(contact: Contact): Unit = {}

  override def beginContact(c: Contact): Unit = {
    if (c.getFixtureA.getUserData.isInstanceOf[Entity] && c.getFixtureB.getUserData.isInstanceOf[Entity]) {
      EventSystem.event(new Collision(c.getFixtureA.getUserData.asInstanceOf[Entity], c.getFixtureB.getUserData.asInstanceOf[Entity]))
    }
  }

}