package aoc2017.kot

import java.io.File

object Day01 {
  fun partOne(input: String): Int = solve(input, 1)
  fun partTwo(input: String): Int = solve(input, input.length / 2)

  fun solve(input: String, offset: Int): Int {
    return (0 until input.length).filter { input[it] == input[(it + offset) % input.length] }.sumBy { input[it] - '0' }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day01_input.txt").readText()
  println("Part One = ${Day01.partOne(input)}")
  println("Part Two = ${Day01.partTwo(input)}")
}
