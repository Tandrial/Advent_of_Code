package aoc2017.kot

import getWords
import java.io.File

object Day12 {
  fun partOne(input: Map<String, List<String>>, start: String): Set<String> {
    var vis = setOf(start)
    while (true) {
      val next = vis.toMutableSet()
      vis.forEach { next.addAll(input[it]!!) }
      if (next == vis) break
      vis = next
    }
    return vis
  }

  fun partTwo(input: Map<String, List<String>>): Int = input.keys.map { partOne(input, it) }.toSet().size
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day12_input.txt").readLines()
  val connections = input.map { val all = it.getWords(); all[0] to all.drop(1) }.toMap()
  println("Part One = ${Day12.partOne(connections, "0").size}")
  println("Part Two = ${Day12.partTwo(connections)}")
}
