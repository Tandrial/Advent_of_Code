package aoc2018.kot

import java.io.File
import java.util.*
import kotlin.math.abs

object Day05 {
  fun partOne(input: String): Vector<Char> = reduce(input)

  fun partTwo(input: String): Int = ('a'..'z').map { c -> reduce(input.filter { it != c && it != c - 32 }).size }.min()!!

  fun reduce(input: String): Vector<Char> = input.fold(Vector(input.length)) { acc, c ->
    when {
      acc.isNotEmpty() && abs(acc.elementAt(acc.size - 1) - c) == 32 -> acc.removeElementAt(acc.size - 1)
      else -> acc.addElement(c)
    }
    acc
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day05_input.txt").readText()
  val reducedString = Day05.partOne(input).joinToString(separator = "")
  println("Part One = ${reducedString.length}")
  println("Part Two = ${Day05.partTwo(reducedString)}")
}
