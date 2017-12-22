package aoc2017.kot

import java.io.File

object Day19 {
  fun solve(input: List<CharArray>): Pair<String, Int> {
    var posX = input[0].indexOf('|')
    var posY = 0
    var dx = 0
    var dy = 1
    var count = 0
    val sb = StringBuilder()

    loop@ while (true) {
      val currChar = input[posY][posX]
      when (currChar) {
        in 'A'..'Z' -> sb.append(currChar)
        ' ' -> break@loop
        '+' -> {
          // We only need to check +-90° turns
          val (dxnew, dynew) = listOf(Pair(-dy, -dx), Pair(dy, dx)).first { (x, y) ->
            val (rx, ry) = Pair(posX + x, posY + y)
            if (ry in (0 until input.size) && rx in (0 until input[ry].size)) {
              //Make sure we don't run into a different line, y == 0 means we're moving horizontal ==> nextChar == '-'
              //       ----+|   Left is correct               x == 0 means we're moving vertical   ==> nextChar == '|'
              //           ||
              //           ^|
              (y == 0 && input[ry][rx] == '-') || (x == 0 && input[ry][rx] == '|')
            } else {
              false
            }
          }
          dx = dxnew; dy = dynew
        }
      }
      posX += dx
      posY += dy
      count++
    }
    return Pair(sb.toString(), count)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day19_input.txt").readLines().map { it.toCharArray() }
  val (partOne, partTwo) = Day19.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
