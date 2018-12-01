package aoc2018.kot

import itertools.cycle
import java.io.File

object Day01 {
  fun partOne(input: List<String>): Int = input.map { it.toInt() }.sum()

  fun partTwo(input: List<String>): Int {
    val freqs = mutableSetOf<Int>()
    var freq = 0

    input.cycle { it.toInt() }.forEach {
      if (!freqs.add(freq)) {
        return freq
      }
      freq += it
    }
    return -1
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day01_input.txt").readLines()
  println("Part One = ${Day01.partOne(input)}")
  println("Part Two = ${Day01.partTwo(input)}")
}
