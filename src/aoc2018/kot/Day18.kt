package aoc2018.kot

import java.io.File

object Day18 {
  fun partOne(input: List<String>): Int {
    var grid = parse(input)
    repeat(10) {
      grid = nextGeneration(grid)
    }
    return grid.sumBy { it.count { it == '|' } } * grid.sumBy { it.count { it == '#' } }
  }

  fun partTwo(input: List<String>): Int {
    var current = parse(input)
    val old = mutableListOf(current)
    repeat(1000000000) {
      current = nextGeneration(current)
      val find = old.withIndex().filter { (_, arr) -> arr contentDeepEquals current }
      if (find.isNotEmpty()) {
        val idxCycle = (1000000000 - it) % (old.size - find.first().index)
        current = old[idxCycle + find.first().index - 1]
        return current.sumBy { it.count { it == '|' } } * current.sumBy { it.count { it == '#' } }
      }
      old.add(current)

    }
    return current.sumBy { it.count { it == '|' } } * current.sumBy { it.count { it == '#' } }
  }

  private fun parse(input: List<String>) = input.map { it.toCharArray() }.toTypedArray()

  private fun nextGeneration(current: Array<CharArray>): Array<CharArray> {
    val next = Array(current.size) { CharArray(current[0].size) }
    current.forEachIndexed { y, line ->
      line.forEachIndexed { x, c ->
        val surroudings = countNeighbours(x, y, current)
        next[y][x] = when (c) {
          '.' -> if (surroudings['|']!! >= 3) '|' else c
          '|' -> if (surroudings['#']!! >= 3) '#' else c
          '#' -> if (surroudings['#']!! >= 1 && surroudings['|']!! >= 1) '#' else '.'
          else -> c
        }
      }
    }
    return next
  }

  private fun countNeighbours(x: Int, y: Int, grid: Array<CharArray>): MutableMap<Char, Int> {
    val counts = mutableMapOf('.' to 0, '|' to 0, '#' to 0)
    for (currX in (x - 1..x + 1)) {
      for (currY in (y - 1..y + 1)) {
        if (currX != x || currY != y) {
          if (currX in (0 until grid[0].size) && currY in (0 until grid.size))
            counts.replace(grid[currY][currX], counts[grid[currY][currX]]!! + 1)
        }
      }
    }
    return counts
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day18_input.txt").readLines()
  println("Part One = ${Day18.partOne(input)}")
  println("Part Two = ${Day18.partTwo(input)}")
}
