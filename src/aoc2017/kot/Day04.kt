package aoc2017.kot

import java.io.File

object Day04 {

  fun partOne(input: List<String>): Int = input.map { it.split(" ") }.count { it.size == it.toSet().size }

  fun partTwo(input: List<String>): Int = input.map {
    it.split(" ").map { it.sort() }
  }.count { it.size == it.toSet().size }

  fun String.sort(): String {
    val carr = this.toCharArray()
    carr.sort()
    return String(carr)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day04_input.txt").readLines()
  println("Part One = ${Day04.partOne(input)}")
  println("Part Two = ${Day04.partTwo(input)}")
}


