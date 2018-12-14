package aoc2018.kot

import kotlin.system.measureTimeMillis

object Day14 {
  fun partOne(input: String): Long {
    val recepies = mutableListOf(3, 7)
    var pos1 = 0
    var pos2 = 1
    repeat(input.toInt() + 10) {
      val score1 = recepies[pos1]
      val score2 = recepies[pos2]
      (score1 + score2).toString().forEach { recepies.add(it - '0') }
      pos1 = (pos1 + 1 + score1) % recepies.size
      pos2 = (pos2 + 1 + score2) % recepies.size
    }
    return recepies.drop(input.toInt()).take(10).fold(0L) { acc, e -> acc * 10 + e }
  }

  fun partTwo(input: String): Int {
    val recepies = mutableListOf(3, 7)
    var pos1 = 0
    var pos2 = 1
    while (true) {
      val score1 = recepies[pos1]
      val score2 = recepies[pos2]
      val next = (score1 + score2).toString()
      next.forEach { recepies.add(it - '0') }
      if (next.any { it == input.last() }) {
        val loc = recepies.takeLast(input.length + 1).joinToString(separator = "").indexOf(input)
        if (loc != -1) {
          return if (next.length > 1)
            recepies.size - input.length + loc - 1
          else
            recepies.size - input.length
        }
      }
      pos1 = (pos1 + 1 + score1) % recepies.size
      pos2 = (pos2 + 1 + score2) % recepies.size
    }
  }
}

fun main(args: Array<String>) {
  val input = "380621"
  println("Part One = ${Day14.partOne(input)}")
  println(measureTimeMillis { println("Part Two = ${Day14.partTwo(input)}") })
}
