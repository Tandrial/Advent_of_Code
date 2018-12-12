package aoc2018.kot

import java.io.File

object Day12 {
  fun partOne(input: List<String>): Int {
    val (state, rules) = parse(input)
    return (0 until 20).fold(state) { s, _ -> nextGen(s, rules) }.sum()
  }

  fun partTwo(input: List<String>): Long {
    val (state, rules) = parse(input)
    var currentSum = state.sum()
    var next = state
    val lastDifferences = mutableListOf<Int>()
    var cnt = 0

    while (lastDifferences.size < 2 || lastDifferences.any { it != lastDifferences.last() }) {
      next = nextGen(next, rules)
      lastDifferences.add(next.sum() - currentSum)
      if (lastDifferences.size > 100) lastDifferences.removeAt(0)
      currentSum = next.sum()
      cnt++
    }
    return (50000000000 - cnt) * lastDifferences.last() + currentSum
  }

  private fun nextGen(state: Set<Int>, rules: List<String>): Set<Int> {
    val left = state.min()!!
    val right = state.max()!!
    val next = mutableSetOf<Int>()

    for (i in (left - 2..right + 2)) {
      val pattern = (-2..2).joinToString(separator = "") {
        when (i + it) {
          in state -> "#"
          else -> "."
        }
      }
      if (rules.any { it == pattern }) next.add(i)
    }
    return next
  }

  private fun parse(input: List<String>): Pair<Set<Int>, List<String>> {
    val state = input[0].split(": ")[1]
    val rules = input.drop(2).mapNotNull { line ->
      val (lhs, rhs) = line.split(" => ")
      when (rhs) {
        "#" -> lhs
        else -> null
      }
    }
    return Pair(state.withIndex().filter { it.value == '#' }.map { it.index }.toSet(), rules)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day12_input.txt").readLines()
  println("Part One = ${Day12.partOne(input)}")
  println("Part Two = ${Day12.partTwo(input)}")
}
