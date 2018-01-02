package aoc2017.kot

import java.io.File
import kotlin.system.measureTimeMillis

object Day24 {

  fun partOne(input: List<String>): Pair<Int, Int> {
    val (pairs, doubles) = input.map { val (l, r) = it.split("/").map { it.toInt() }; l to r }.partition { it.first != it.second }
    val (p1, p2) = dfs(listOf(0), pairs, doubles)
    return Pair(p1, p2.second)
  }

  fun dfs(bridge: List<Int>, pieces: List<Pair<Int, Int>>, doubles: List<Pair<Int, Int>>): Pair<Int, Pair<Int, Int>> {
    val matchDbl = doubles.filter { bridge.contains(it.first) }
    var strength = bridge.sumBy { it } + 2 * matchDbl.sumBy { it.first }
    val length = bridge.size + 2 * matchDbl.size
    var longest = Pair(length, strength)
    for ((l, r) in pieces) {
      if (l == bridge.last() || r == bridge.last()) {
        val newLink = if (l == bridge.last()) listOf(l, r) else listOf(r, l)
        val result = dfs(bridge + newLink, pieces.filter { it != Pair(l, r) }, doubles)
        strength = maxOf(strength, result.first)
        if (length > longest.first || (length == longest.first && strength > longest.second)) {
          longest = Pair(length, strength)
        }
      }
    }
    return Pair(strength, longest)
  }

  fun solve(input: List<String>): Pair<Int, Int> {
    val (pairs, doubles) = input.map { val (l, r) = it.split("/").map { it.toInt() }; l to r }.partition { it.first != it.second }
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
        // Find the doubles which fit into the bridge
        val matchDbl = doubles.filter { bridge.contains(it.first) }
        val strength = bridge.sumBy { it } + 2 * matchDbl.sumBy { it.first }
        val length = bridge.size + 2 * matchDbl.size

        maxStrength = maxOf(maxStrength, strength)
        // bridge is longer or same length but stronger
        if (length > longest.first || (length == longest.first && strength > longest.second)) {
          longest = Pair(length, strength)
        }
      }
    }

    return Pair(maxStrength, longest.second)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day24_input.txt").readLines()
//  val (partOne, partTwo) = Day24.solve(input)
//  println("Part One = $partOne")
//  println("Part Two = $partTwo")
  var dfsT = 0L
  var bfsT = 0L
  repeat(100) {
    print(it)
    bfsT += measureTimeMillis { Day24.solve(input) }
    dfsT += measureTimeMillis { Day24.partOne(input) }
  }
  println()
  println("DFS: $dfsT total ${dfsT/100} ms avr")
  println("BFS: $bfsT total ${bfsT/100} ms avr")
}
