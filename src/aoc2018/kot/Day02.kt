package aoc2018.kot

import itertools.combinations
import java.io.File

object Day02 {
  fun partOne(input: List<String>): Int {
    val res = input.map { id ->
      id.groupingBy { it }.eachCount().values
    }
    return res.count { 2 in it } * res.count { 3 in it }
  }

  fun partTwo(input: List<String>): String {
    input.combinations(2).forEach { (b1, b2) ->
      val sb = StringBuilder()
      var diff = 0
      for ((c1, c2) in b1.zip(b2)) {
        if (c1 != c2) {
          diff++
        } else {
          sb.append(c1)
        }
      }
      if (diff == 1) {
        return sb.toString()
      }
    }
    return ""
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day02_input.txt").readLines()
  println("Part One = ${Day02.partOne(input)}")
  println("Part Two = ${Day02.partTwo(input)}")
}

