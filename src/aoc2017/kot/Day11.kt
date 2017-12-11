package aoc2017.kot

import java.io.File

object Day11 {
  fun partOne(input: List<String>): Pair<Int, Int> {
    // http://3dmdesign.com/development/hexmap-coordinates-the-easy-way
    var x = 0
    var y = 0
    var dMax = 0
    input.forEach {
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
      dMax = maxOf(Math.abs(x), Math.abs(y), dMax)
    }
    return Pair(maxOf(Math.abs(x), Math.abs(y)), dMax)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day11_input.txt").readText().split(",")
  val result = Day11.partOne(input)
  println("Part One = ${result.first}")
  println("Part One = ${result.second}")
}
