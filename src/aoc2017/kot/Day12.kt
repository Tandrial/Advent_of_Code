package aoc2017.kot

import getNumbers
import java.io.File

object Day12 {
  fun solve(input: List<String>): Pair<Int, Int> {
    val ids = IntArray(input.size) { it }
    val sizes = IntArray(input.size) { 1 }
    var groupCount = ids.size
    input.forEach {
      val all = it.getNumbers()
      all.drop(1).forEach { groupCount = join(all[0], it, ids, sizes, groupCount) }
    }
    return Pair(sizes[find(0, ids)], groupCount)
  }

  private fun find(x: Int, id: IntArray): Int {
    if (id[x] == x) return x
    id[x] = find(id[x], id)
    return id[x]
  }

  private fun join(x: Int, y: Int, ids: IntArray, sizes: IntArray, groupCount: Int): Int {
    val xID = find(x, ids)
    val yID = find(y, ids)
    if (xID == yID) return groupCount
    ids[xID] = yID
    sizes[yID] += sizes[xID]
    return groupCount - 1
  }

}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day12_input.txt").readLines()
  val (partOne, partTwo) = Day12.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
