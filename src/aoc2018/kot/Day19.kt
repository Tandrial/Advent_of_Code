package aoc2018.kot


import VM18
import getWords
import java.io.File
import kotlin.math.sqrt

object Day19 {
  fun partOne(input: List<String>) = solve(input, listOf("0" to 0L, "1" to 0L, "2" to 0L, "3" to 0L, "4" to 0L, "5" to 0L))
  fun partTwo(input: List<String>) = solve(input, listOf("0" to 1L, "1" to 0L, "2" to 0L, "3" to 0L, "4" to 0L, "5" to 0L))

  fun solve(input: List<String>, regs: List<Pair<String, Long>>): Long {
    val vm = VM18(input.drop(1), regs, input.first().getWords()[1])
    val num = vm.run(day19 = true)
    return (1L..(sqrt(num.toDouble()).toLong() + 1L)).map { if (num % it == 0L) it + num / it else 0 }.sum()
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day19_input.txt").readLines()
  println("Part One = ${Day19.partOne(input)}")
  println("Part Two = ${Day19.partTwo(input)}")
}
