package event

import com.badlogic.gdx.math.Vector2
import state.{Entity, Living, Vector2i}

/**
  * Created by julien on 22/09/16.
  */
trait Event

// doesn't cause a state transition, just a flag to hold on to it rather than mutate mainly used for failures, or any event that doesn't transition state
trait FromEntityEvent extends Event {  def from: Int}
trait ToEntityEvent extends Event {  def to: Int}
trait ZoneEvent extends Event

case class DecisionOutput(events: Seq[Event], transitions: Seq[InstantStateTransition[Living]])
case class SimulationOutput(events: Seq[Event], transitions: Seq[StateTransition[Entity]])

// ROUTED EVENTS
case class PlaceItemAt(at: Vector2i, itemType: Int, from: Int) extends FromEntityEvent with ZoneEvent {
  def failure = PlaceItemFailed(itemType, from)
}
case class PlaceItemFailed(itemType: Int, to: Int) extends ToEntityEvent
case class PlaceItemSucceeded(itemType: Int, to: Int) extends ToEntityEvent

case class RequestPath(fromPosition: Vector2i, toPosition: Vector2i, from: Int) extends FromEntityEvent {
  def failure = PathRequestFailed(from)
}
case class PathRequestSuccessful(path: Seq[Int], to: Int) extends ToEntityEvent
case class PathRequestFailed(to: Int) extends ToEntityEvent

case class PickupItem(to: Int, from: Int) extends FromEntityEvent with ToEntityEvent {
  def failure = FailedPickup(to, from)
}
case class SuccessfulPickup(itemType: Int, to: Int) extends ToEntityEvent
case class FailedPickup(from: Int, to: Int) extends ToEntityEvent

case class DropItem(itemType: Int, from: Int) extends FromEntityEvent with ZoneEvent

case class TakeDamage(damage: Int, from: Int, to: Int) extends FromEntityEvent with ToEntityEvent

trait StateTransition[-T] {
  def applyTo(entity: T)
}

trait InstantStateTransition[T] extends StateTransition[T]

case object Death extends InstantStateTransition[Entity] {
  def applyTo(entity: Entity) = entity.alive = false
}

trait SimulatedStateTransition[T] extends StateTransition[T]

// only update is allowed to return simulated state transitions (it can also return instants)
case class ChangePosition(position: Vector2) extends SimulatedStateTransition[Entity] {
  def applyTo(entity: Entity) = entity.pos.set(position)
}
