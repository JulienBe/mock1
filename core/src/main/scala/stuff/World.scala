package stuff

import scala.collection.mutable

/**
  * Created by julien on 06/10/16.
  */
class World(val size: Vector2i) {
  val blocks = new Array[Byte](size.product)

  def hasBlockAt(x: Int, y: Int) = getBlockAt(x, y) > 0
  def getBlockAt(x: Int, y: Int): Byte = blocks(blockPosition(x, y))
  def setBlockAt(x: Int, y: Int, b: Byte) = blocks(blockPosition(x, y)) = b

  private def blockPosition(x: Int, y: Int): Int = size.y * x + y
}

object World {
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

case class Vector2i(var x: Int, var y: Int) {
  def product = x * y

  def :=(other: Vector2i) {
    x = other.x
    y = other.y
  }
}
