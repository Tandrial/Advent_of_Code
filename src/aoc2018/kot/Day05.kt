package aoc2018.kot

import java.io.File
import java.util.*
import kotlin.math.abs

object Day05 {
  fun partOne(input: String): Int = reduce(input)

  fun partTwo(input: String): Int {
    return ('a'..'z').map { Pair(it, it - 32) }.map { (lower, upper) ->
      reduce(input.filter { it != lower && it != upper })
    }.min()!!
  }

  fun reduce(input: String): Int {
    val stack = Stack<Char>()
    input.forEach { c ->
      if (!stack.empty() && abs(stack.peek() - c) == 32)
        stack.pop()
      else
        stack.push(c)
    }
    return stack.size
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day03_input.txt").readText()
  println("Part One = ${Day05.partOne(input)}")
  println("Part Two = ${Day05.partTwo(input)}")
}
