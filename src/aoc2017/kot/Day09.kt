package aoc2017.kot

import java.io.File

object Day09 {
  private enum class State { DEFAULT, GARBAGE, IGNORE }

  fun solve(input: String): Pair<Int, Int> {
    var state = State.DEFAULT
    var sum = 0
    var groupCount = 0
    var garbageCount = 0
    input.forEach {
      // Ignore can only be in Garbage
      when (state) {
        State.DEFAULT -> when (it) {
          '<' -> state = State.GARBAGE
          '{' -> groupCount++
          '}' -> sum += groupCount--
        }
        State.GARBAGE -> when (it) {
          '!' -> state = State.IGNORE
          '>' -> state = State.DEFAULT
          else -> garbageCount++
        }
        State.IGNORE -> state = State.GARBAGE
      }
    }
    return Pair(sum, garbageCount)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day09_input.txt").readText()
  val result = Day09.solve(input)
  println("Part One = ${result.first}")
  println("Part Two = ${result.second}")
}
