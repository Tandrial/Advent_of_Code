package aoc2017.kot

import itertools.cartProd

object Day03 {

  fun partOne(input: Int): Int {
    val size = Math.ceil(Math.sqrt(input.toDouble())).toInt()
    val start: Pair<Int, Int> = Pair(size / 2, size / 2)
    var len = 1
    var x = start.first
    var y = start.second
    var value = 1

    loop@ while (true) {
      for ((x_off, y_off) in listOf(Pair(1, -1), Pair(-1, 1))) {
        for (idx in 0 until len) {
          value++
          x += x_off
          if (value == input) {
            break@loop
          }
        }
        for (idx in 0 until len) {
          value++
          y += y_off
          if (value == input) {
            break@loop
          }
        }
        len++
      }
    }
    return Math.abs(x - start.first) + Math.abs(y - start.second)
  }

  fun partTwo(input: Int): Int {
    val size = Math.ceil(Math.sqrt(input.toDouble())).toInt()
    val grid = Array(size) { IntArray(size) }
    var x = size / 2
    var y = size / 2
    var len = 1
    grid[x][y] = 1
    while (true) {
      for ((x_off, y_off) in listOf(Pair(1, -1), Pair(-1, 1))) {
        for (idx in 0 until len) {
          x += x_off
          grid[x][y] = getValue(grid, x, y)
          if (grid[x][y] >= input) {
            return grid[x][y]
          }
        }
        for (idx in 0 until len) {
          y += y_off
          grid[x][y] = getValue(grid, x, y)
          if (grid[x][y] >= input) {
            return grid[x][y]
          }
        }
        len++
      }
    }
  }

  private fun getValue(grid: Array<IntArray>, x: Int, y: Int): Int {
    return (-1..1).cartProd(2).map { grid[x + it[0]][y + it[1]] }.sum()
  }
}

fun main(args: Array<String>) {
  val input = 361527
  println("Part One = ${Day03.partOne(input)}")
  println("Part Two = ${Day03.partTwo(input)}")
}


