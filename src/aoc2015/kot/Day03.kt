package aoc2015.kot

import java.io.File
import java.util.*

object Day03 {
  fun partOne(input: String): Set<Pair<Int, Int>> {
    val locations = HashSet<Pair<Int, Int>>()
    var location = Pair(0, 0)
    locations.add(location)
    for (c in input) {
      when (c) {
        '^' -> location = Pair(location.first - 1, location.second)
        'v' -> location = Pair(location.first + 1, location.second)
        '<' -> location = Pair(location.first, location.second - 1)
        '>' -> location = Pair(location.first, location.second + 1)
      }
      locations.add(location)
    }
    return locations
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2015/Day03_input.txt").readText()
  println("Part One = ${Day03.partOne(input).size}")
  val santa = Day03.partOne(input.filterIndexed { idx, _ -> idx % 2 == 0 })
  val santa2 = Day03.partOne(input.filterIndexed { idx, _ -> idx % 2 == 1 })
  println("Part Two = ${santa.union(santa2).size}")
}
