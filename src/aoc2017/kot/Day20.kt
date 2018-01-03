package aoc2017.kot

import getNumbers
import java.io.File

object Day20 {
  data class Vec3d(var x: Int, var y: Int, var z: Int) {
    operator fun plus(other: Vec3d) = Vec3d(this.x + other.x, this.y + other.y, this.z + other.z)
  }

  class Particle(val id: Int, s: String) {
    val values = s.getNumbers()
    var pos = Vec3d(values[0], values[1], values[2])
    var vel = Vec3d(values[3], values[4], values[5])
    val acc = Vec3d(values[6], values[7], values[8])
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
      particles.groupBy { it.pos }.values.filter { it.size > 1 }.forEach { particles.removeAll(it) }
    }
    return particles.size
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day20_input.txt").readLines()
  println("Part One = ${Day20.partOne(input)}")
  println("Part Two = ${Day20.partTwo(input)}")
}
