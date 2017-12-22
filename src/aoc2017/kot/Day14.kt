package aoc2017.kot

import toHexString
import java.math.BigInteger

object Day14 {
  fun partOne(input: String): Int = genHashList(input).sumBy { it.count { it == '1' } }

  fun partTwo(input: String): Int {
    val hashArray = genHashList(input).map { it.map { (it - '0') }.toTypedArray() }.toTypedArray()
    var currGroup = 2
    // Loop over each Cell skipping assigned cells and assign groups with BFS starting from the current cell
    for ((idRow, row) in hashArray.withIndex()) {
      for ((idCol, cell) in row.withIndex()) {
        if (cell == 1) {
          hashArray[idRow][idCol] = currGroup
          val queue = mutableListOf(listOf(idRow, idCol, currGroup))
          // BFS: If the neighbour is set to 1 it's part of the group and wasn't yet explored
          while (queue.isNotEmpty()) {
            val (baseX, baseY, group) = queue.removeAt(0)
            listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0)).map { (x, y) -> Pair(baseX + x, baseY + y) }.forEach { (x, y) ->
              try {
                if (hashArray[x][y] == 1) {
                  hashArray[x][y] = group
                  queue.add(listOf(x, y, group))
                }
              } catch (_: Exception) {
              }
            }
          }
          currGroup++
        }
      }
    }
    return currGroup - 2
  }

  private fun genHashList(seed: String): List<String> = (0..127).map {
    val hashIn = "$seed-$it".toCharArray().map { it.toInt() } + listOf(17, 31, 73, 47, 23)
    val hash = Day10.partTwo(hashIn, 64).toHexString()
    BigInteger(hash, 16).toString(2).padStart(128, '0')
  }
}

fun main(args: Array<String>) {
  val input = "vbqugkhl"
  println("Part One = ${Day14.partOne(input)}")
  println("Part Two = ${Day14.partTwo(input)}")
}
