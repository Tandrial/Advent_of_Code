package aoc2017.kot

import java.io.File

object Day22 {
  fun solve(input: List<String>, cnt: Int, partTwo: Boolean = false): Int {
    val grid = genGrid(input)
    var count = 0
    var pos = Pair(grid.size / 2, grid.size / 2)
    var dir = Pair(-1, 0)

    repeat(cnt) {
      val (posX, posY) = pos
      when (grid[posX][posY]) {
        '#' -> {
          //right turn
          dir = Pair(dir.second, -dir.first)
          if (partTwo) {
            grid[posX][posY] = 'F'
          } else {
            grid[posX][posY] = '.'
          }
        }
        '.' -> {
          //left turn
          dir = Pair(-dir.second, dir.first)
          if (partTwo) {
            grid[posX][posY] = 'W'
          } else {
            grid[posX][posY] = '#'
            count++
          }
        }
        'W' -> {
          grid[posX][posY] = '#'
          count++
        }
        'F' -> {
          grid[posX][posY] = '.'
          dir = Pair(-dir.first, -dir.second)
        }
      }
      pos = Pair(posX + dir.first, posY + dir.second)
    }
    return count
  }

  private fun genGrid(input: List<String>): Array<CharArray> {
    val grid = Array(1000) { CharArray(1000) { '.' } }
    val offSet = grid.size / 2 - input.size / 2
    for (xG in 0 until input.size) {
      for (yG in 0 until input.size) {
        grid[offSet + xG][offSet + yG] = input[xG][yG]
      }
    }
    return grid
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day22_input.txt").readLines()
  println("Part One = ${Day22.solve(input, 10000)}")
  println("Part Two = ${Day22.solve(input, 10000000, true)}")
}
