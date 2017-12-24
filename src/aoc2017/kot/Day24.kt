package aoc2017.kot

import java.io.File

object Day24 {
  fun solve(input: List<String>): Pair<Int, Int> {
    val pairs = input.map { val (l, r) = it.split("/").map { it.toInt() }; l to r }
    val queue = mutableListOf(Pair(listOf(0), pairs))

    var maxStrength = 0
    var longest = Pair(0, 0)

    // BFS, add link if either the fst or snd matches the last element of the current bridge
    while (queue.isNotEmpty()) {
      val (bridge, pieces) = queue.removeAt(0)
      var bridgeDone = true
      for ((l, r) in pieces) {
        if (l == bridge.last() || r == bridge.last()) {
          bridgeDone = false
          val newLink = if (l == bridge.last()) listOf(l, r) else listOf(r, l)
          queue.add(Pair(bridge + newLink, pieces.filter { it != Pair(l, r) }))
        }
      }

      if (bridgeDone) {
        val strength = bridge.sumBy { it }
        maxStrength = maxOf(maxStrength, strength)
        // bridge is longer or same length but stronger
        if (bridge.size > longest.first || (bridge.size == longest.first && strength > longest.second)) {
          longest = Pair(bridge.size, strength)
        }
      }
    }

    return Pair(maxStrength, longest.second)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day24_input.txt").readLines()
  val (partOne, partTwo) = Day24.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
