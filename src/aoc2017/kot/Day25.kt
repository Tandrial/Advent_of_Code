package aoc2017.kot

import getNumbers
import getWords
import java.io.File

object Day25 {
  data class Rule(val write: Int, val move: Int, val nextState: String)

  fun solve(input: List<String>): Int {
    val rules = mutableMapOf<Pair<String, Int>, Rule>()
    val rulesText = input.drop(3)

    for ((idx, line) in rulesText.withIndex()) {
      if (line.contains("In state ")) {
        val words = line.dropLast(1).split(" ")
        val state = words.last()
        for (value in 0..1) {
          val values = generateSequence(idx + 2 + value * 4) { it + 1 }.take(3)
              .map { rulesText[it].dropLast(1).split(" ").last() }.toList()
          val write = values[0].toInt()
          val move = if (values[1] == "right") 1 else -1
          val nextState = values[2]
          rules[Pair(state, value)] = Rule(write, move, nextState)
        }
      }
    }

    val checkAfter = input[1].getNumbers().first().toInt()
    var currState = input[0].getWords().last()

    val tape = mutableListOf(0)
    var pos = 0

    repeat(checkAfter) {
      val rule = rules[Pair(currState, tape[pos])]!!
      tape[pos] = rule.write
      pos += rule.move
      if (pos < 0) {
        tape.add(0, 0)
        pos = 0
      } else if (pos >= tape.size) {
        tape.add(0)
      }
      currState = rule.nextState
    }

    return tape.sum()
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day25_input.txt").readLines()
  println("Part One = ${Day25.solve(input)}")
}
