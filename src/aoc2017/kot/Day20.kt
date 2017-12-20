package aoc2017.kot

import getNumbers
import java.io.File

object Day20 {
  data class Vec3d(var x: Long, var y: Long, var z: Long) {
    operator fun plus(other: Vec3d) = Vec3d(this.x + other.x, this.y + other.y, this.z + other.z)
  }

  class Particle(val id: Int, s: String) {
    var pos: Vec3d
    var vel: Vec3d
    var acc: Vec3d

    init {
      val values = s.getNumbers()
      pos = Vec3d(values[0].toLong(), values[1].toLong(), values[2].toLong())
      vel = Vec3d(values[3].toLong(), values[4].toLong(), values[5].toLong())
      acc = Vec3d(values[6].toLong(), values[7].toLong(), values[8].toLong())
    }
  }

  fun partOne(input: List<String>): Int {
    val particles = input.mapIndexed { index, s -> Particle(index, s) }
    repeat(1000) {
      particles.forEach {
        it.vel += it.acc
        it.pos += it.vel
      }
    }
    return particles.sortedBy { Math.abs(it.pos.x) + Math.abs(it.pos.y) + Math.abs(it.pos.z) }.first().id
  }

  fun partTwo(input: List<String>): Int {
    val particles = input.mapIndexed { index, s -> Particle(index, s) }.toMutableList()
    repeat(1000) {
      particles.forEach {
        it.vel += it.acc
        it.pos += it.vel
      }
      // We're grouping by position, and remove everything in groups with size > 1
      val collisions = particles.groupBy { it.pos }.values.filter { it.size > 1 }.flatten()
      particles.removeAll(collisions)
    }
    return particles.size
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day20_input.txt").readLines()
  println("Part One = ${Day20.partOne(input)}")
  println("Part Two = ${Day20.partTwo(input)}")
}
