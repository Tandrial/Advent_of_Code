package aoc2017.kot

import getWords
import java.io.File

object Day12 {
  fun partOne(input: Map<String, List<String>>, start: String): Set<String> {
    var vis = setOf(start)
    while (true) {
      val next = vis.toMutableSet()
      for (elem in vis) {
        next.addAll(input[elem]!!)
      }
      if (next == vis) break
      vis = next
    }
    return vis
  }

  fun partTwo(input: List<String>): Int {
    val pipes = parse(input)
    val vis = mutableSetOf<Set<String>>()
    pipes.keys.mapTo(vis) { partOne(pipes, it) }
    return vis.size
  }

  fun parse(input: List<String>): Map<String, List<String>> {
    return input.map { val all = it.getWords(); all[0] to all.drop(1) }.toMap()
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day12_input.txt").readLines()
  println("Part One = ${Day12.partOne(Day12.parse(input), "0").size}")
  println("Part Two = ${Day12.partTwo(input)}")
}
