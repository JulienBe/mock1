package world

import entities.Entity

/**
  * Created by julein on 16/10/16.
  */
class WallTag extends Entity{
  override def free(): Unit = {}
  override def speed(): Float = 0
  override def width(): Float = 0
  override def density(): Float = 0
  override def friction(): Float = 0
  override def restitution(): Float = 0
}
