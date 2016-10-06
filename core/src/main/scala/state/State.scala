package state

/**
  * Created by julien on 22/09/16.
  */
import com.badlogic.gdx.math.Vector2

import collection.mutable

class World(val size: Vector2i) {
  val blocks = new Array[Byte](size.product)
  val entities = new mutable.HashMap[Int, Entity]()

  def hasBlockAt(x: Int, y: Int) = getBlockAt(x, y) > 0
  def getBlockAt(x: Int, y: Int): Byte = blocks(blockPosition(x, y))
  def setBlockAt(x: Int, y: Int, b: Byte) = blocks(blockPosition(x, y)) = b

  private def blockPosition(x: Int, y: Int): Int = size.y * x + y
}

object World {
  def takeDamage(damage: Int)(l: Living): Living = l.copy(floor(l.health - damage, 0, 100))

  def floor(n: Int, min: Int, max: Int) = {
    if (n < min)      min
    else if (n > max) max
    else              n
  }

  def assignBorders(world: World, b: Byte) {
    for { x <- 0 until world.size.x } {
      world.setBlockAt(x, 0, b)
      world.setBlockAt(x, world.size.y - 1, b)
    }
    for { y <- 0 until world.size.y } {
      world.setBlockAt(0, y, b)
      world.setBlockAt(world.size.x - 1, y, b)
    }
  }
}

abstract class Entity(val pos: Vector2) {
  var alive = true // needed to track an object being picked up while still processing all of it's events
  val events = new mutable.Queue[event.ToEntityEvent]()
  def glyph: Char
}

class Living(pos: Vector2, val health: Int = 100) extends Entity(pos) {
  def copy(newHealth: Int) = new Living(pos, newHealth)
  override def glyph: Char = 0x0001
}

class Item(pos: Vector2, val itemType: Int) extends Entity(pos) {
  def glyph = 0x2663
}

object Item {
  val sword = 0
  val crate = 1
}

case class Vector2i(var x: Int, var y: Int) {
  def product = x * y

  def :=(other: Vector2i) {
    x = other.x
    y = other.y
  }
}