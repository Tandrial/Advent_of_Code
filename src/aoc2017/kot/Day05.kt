package aoc2017.kot

import java.io.File

object Day05 {

  fun partOne(input: List<Int>): Int = solve(input) { _ -> 1 }

  fun partTwo(input: List<Int>): Int = solve(input) { it -> if (it >= 3) -1 else 1 }

  fun solve(input: List<Int>, change: (Int) -> Int): Int {
    val mem = input.toMutableList()
    var pos = 0
    var count = 0
    while (pos < mem.size) {
      val off = mem[pos]
      mem[pos] += change(mem[pos])
      pos += off
      count++
    }
    return count
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day05_input.txt").readLines().map { it.toInt() }
  println("Part One = ${Day05.partOne(input)}")
  println("Part Two = ${Day05.partTwo(input)}")
}
