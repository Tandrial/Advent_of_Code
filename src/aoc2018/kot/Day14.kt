package aoc2018.kot

import kotlin.coroutines.experimental.buildSequence
import kotlin.system.measureTimeMillis

object Day14 {

  fun partOne(input: String) = genRecepies.drop(input.toInt()).take(10).fold(0L) { acc, e -> acc * 10 + e }

  fun partTwo(input: String) = genRecepies.windowed(input.length).indexOf(input.map { it - '0' })

  private val genRecepies: Sequence<Int> = buildSequence {
    var i = 0
    var j = 1
    val scores = mutableListOf(3, 7)
    yieldAll(scores)
    while (true) {
      val s1 = scores[i]
      val s2 = scores[j]
      val s = s1 + s2
      if (s >= 10) {
        scores.add(s / 10)
        yield(scores.last())
      }
      scores.add(s % 10)
      yield(scores.last())
      i = (i + s1 + 1) % scores.size
      j = (j + s2 + 1) % scores.size
    }
  }
}

fun main(args: Array<String>) {
  val input = "380621"
  println("Part One = ${Day14.partOne(input)}")
  println(measureTimeMillis { println("Part Two = ${Day14.partTwo(input)}") })
}
