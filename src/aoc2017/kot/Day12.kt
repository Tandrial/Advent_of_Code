package aoc2017.kot

import getNumbers
import java.io.File

object Day12 {
  private lateinit var ids: IntArray
  private lateinit var sizes: IntArray
  private var groupCount: Int = 0

  fun solve(input: List<String>): Pair<Int, Int> {
    ids = IntArray(input.size) { it }
    sizes = IntArray(input.size) { 1 }
    groupCount = ids.size
    input.map { it.getNumbers() }.forEach { it.drop(1).forEach { dst -> join(it[0], dst) } }
    return Pair(sizes[find(0)], groupCount)
  }

  private fun find(x: Int): Int {
    if (ids[x] == x) return x
    ids[x] = find(ids[x])
    return ids[x]
  }

  private fun join(x: Int, y: Int) {
    val xID = find(x)
    val yID = find(y)
    if (xID != yID) {
      ids[xID] = yID
      sizes[yID] += sizes[xID]
      groupCount--
    }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day12_input.txt").readLines()
  val (partOne, partTwo) = Day12.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
