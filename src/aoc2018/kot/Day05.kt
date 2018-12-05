package aoc2018.kot

import java.io.File
import java.util.*
import kotlin.math.abs

object Day05 {
  fun partOne(input: String): Stack<Char> = reduce(input)

  fun partTwo(input: String): Int = ('a'..'z').map { c -> reduce(input.filter { it != c && it != c - 32 }).size }.min()!!

  fun reduce(input: String): Stack<Char> = input.fold(Stack()) { acc, c ->
    when {
      acc.isNotEmpty() && abs(acc.peek() - c) == 32 -> acc.pop()
      else -> acc.push(c)
    }
    acc
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day05_input.txt").readText()
  val resultOne = Day05.partOne(input)
  println("Part One = ${resultOne.size}")
  val reducedString = resultOne.joinToString(separator = "")
  println("Part Two = ${Day05.partTwo(reducedString)}")
}
