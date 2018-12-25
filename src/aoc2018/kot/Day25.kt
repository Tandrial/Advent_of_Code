package aoc2018.kot

import getNumbers
import java.io.File
import kotlin.math.abs

object Day25 {
  data class Location(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun dist(other: Location) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(w - other.w)
  }

  fun partOne(input: List<String>): Int {
    val locations = parse(input)
    val constellations = mutableSetOf<MutableSet<Location>>()

    locations.forEach { location ->
      val possible = constellations.filter { it.any { it.dist(location) <= 3 } }.toMutableList()
      constellations.removeAll(possible)
      possible.add(mutableSetOf(location))
      constellations.add(possible.flatten().toMutableSet())
    }

    return constellations.size
  }

  fun parse(input: List<String>) = input.map {
    val (x, y, z, w) = it.getNumbers()
    Location(x, y, z, w)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day25_input.txt").readLines()
  println("Part One = ${Day25.partOne(input)}")
}
