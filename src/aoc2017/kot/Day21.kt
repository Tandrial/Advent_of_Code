package aoc2017.kot

import java.io.File

object Day21 {
  fun solve(start: String, input: List<String>, iter: Int): Int {
    val lookUp = parsePatterns(input)
    var current = start.split("/").map { it.toCharArray() }.toTypedArray()
    repeat(iter) {
      val chunkSize = if (current.size % 2 == 0) 2 else 3
      val listOfBlocks = splitIntoBlocks(current, chunkSize)
      current = mergeBlocks(listOfBlocks.map { lookUp[it.contentDeepHashCode()]!! })
    }
    return current.sumBy { it.count { it == '#' } }
  }

  private fun splitIntoBlocks(current: Array<CharArray>, chunkSize: Int): List<Array<CharArray>> {
    if (current.size == chunkSize) return listOf(current)
    val blockCount = (current.size * current.size) / (chunkSize * chunkSize)
    val blocks = Array(blockCount) { Array(chunkSize) { CharArray(chunkSize) { '.' } } }
    for (y in 0 until current.size) {
      for (x in 0 until current.size) {
        val bOff = x / blocks[0].size + (y / blocks[0].size) * Math.sqrt(blockCount.toDouble()).toInt()
        val xOff = x % blocks[0].size
        val yOff = y % blocks[0].size
        blocks[bOff][xOff][yOff] = current[x][y]
      }
    }
    return blocks.toList()
  }

  private fun mergeBlocks(blocks: List<Array<CharArray>>): Array<CharArray> {
    if (blocks.size == 1) return blocks[0]
    val size = Math.sqrt((blocks.size * blocks[0].size * blocks[0].size).toDouble()).toInt()
    val result = Array(size) { CharArray(size) { '.' } }

    for (y in 0 until result.size) {
      for (x in 0 until result.size) {
        val bOff = x / blocks[0].size + (y / blocks[0].size) * Math.sqrt(blocks.size.toDouble()).toInt()
        val xOff = x % blocks[0].size
        val yOff = y % blocks[0].size
        result[x][y] = blocks[bOff][xOff][yOff]
      }
    }
    return result
  }

  private fun parsePatterns(input: List<String>): Map<Int, Array<CharArray>> {
    val patterns = mutableMapOf<Int, Array<CharArray>>()
    input.forEach {
      val (lhs, rhs) = it.split(" => ").map { it.split("/") }
      val rhsArr = rhs.map { it.toCharArray() }.toTypedArray()
      val permutations = generatePermutations(lhs).map { it.contentDeepHashCode() to rhsArr }
      patterns.putAll(permutations)
    }
    return patterns
  }

  private fun generatePermutations(s: List<String>): List<Array<CharArray>> {
    val result = mutableSetOf<List<String>>()
    var next = s
    repeat(4) {
      result.add(next)
      result.add(next.reversed())
      result.add(next.map { it.reversed() })
      next = rot(next)
    }
    return result.map { it.map { it.toCharArray() }.toTypedArray() }
  }

  private fun rot(grid: List<String>): List<String> = flip2DArray(grid).reversed()
  private fun flip2DArray(arr: List<String>): List<String> = arr.mapIndexed { index, _ -> arr.map { it[index] }.joinToString(separator = "") }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day21_input.txt").readLines()
  val start = ".#./..#/###"
  println("Part One = ${Day21.solve(start, input, 5)}")
  println("Part Two = ${Day21.solve(start, input, 18)}")
}
