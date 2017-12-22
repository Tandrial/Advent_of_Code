package aoc2017.kot

import java.io.File

object Day16 {
  fun solve(input: List<String>, times: Int = 1): String {
    val sequence = mutableListOf<List<Char>>()
    var curr = (0 until 16).map { 'a' + it }
    repeat(times) {
      if (curr in sequence) {
        return sequence[times % sequence.size].joinToString(separator = "")
      } else {
        sequence.add(curr)
        curr = dance(curr, input)
      }
    }
    return curr.joinToString(separator = "")
  }

  private fun dance(curr: List<Char>, input: List<String>): List<Char> {
    var next = curr.toMutableList()

    for (move in input) {
      when (move[0]) {
        's' -> {
          val pos = move.drop(1).toInt()
          next = (next.drop(next.size - pos) + next.take(next.size - pos)).toMutableList()
        }
        'x' -> {
          val (a, b) = move.drop(1).split("/").map { it.toInt() }
          next[a] = next[b].also { next[b] = next[a] }
        }
        'p' -> {
          val (a, b) = move.drop(1).split("/").map { next.indexOf(it[0]) }
          next[a] = next[b].also { next[b] = next[a] }
        }
      }
    }
    return next
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day16_input.txt").readText().split(",")
  println("Part One = ${Day16.solve(input)}")
  println("Part Two = ${Day16.solve(input, 1_000_000_000)}")
}
