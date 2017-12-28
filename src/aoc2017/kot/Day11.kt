package aoc2017.kot

import java.io.File

object Day11 {
  fun solve(input: List<String>): Pair<Int, Int> {
    // http://3dmdesign.com/development/hexmap-coordinates-the-easy-way
    var x = 0
    var y = 0
    val distance = input.map {
      when (it) {
        "n" -> y++
        "ne" -> {
          x++; y++
        }
        "se" -> x++
        "s" -> y--
        "sw" -> {
          x--; y--
        }
        "nw" -> x--
      }
      maxOf(Math.abs(x), Math.abs(y))
    }
    return Pair(distance.last(), distance.max() ?: 0)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day11_input.txt").readText().split(",")
  val (partOne, partTwo) = Day11.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
