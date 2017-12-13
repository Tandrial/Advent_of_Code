package aoc2017.kot

import java.io.File

object Day13 {

  data class Layer(val depth: Int, val range: Int, var currPos: Int = 0) {
    var dir = -1
    fun move() {
      if (currPos == range - 1) dir = -dir
      else if (currPos == 0) dir = -dir
      currPos += dir
    }

    fun move(wait: Int) {
      if (wait == 0) {
        dir = -1
        currPos = 0
      } else {
        val offset = wait % (2 * range - 1)
        if (offset > range) {
          dir = 1
          currPos = offset - range
        } else if (offset == range) {
          dir = -1
          currPos = range
        } else if (offset == 0) {
          dir = -1
          currPos = 0
        } else {
          dir = -1
          currPos = offset
        }
      }
    }

    fun sev(): Int = depth * range

    fun reset() {
      dir = -1
      currPos = 0
    }
  }

  fun partOne(input: List<Layer>, wait: Int): Int {
    var packetPos = -1
    var sum = 0

    input.forEach { it.move(wait) }

    val bal = 0
    while (true) {
      packetPos++
      if (packetPos != 0 || wait > 0) sum += input.filter { it.depth == packetPos && it.currPos == 0 }.sumBy { maxOf(it.sev(), 1) }
      input.forEach { it.move() }
      if (packetPos == input.last().depth) break
    }

    return sum
  }

  fun partTwo(input: List<Layer>): Int {
    var wait = 0
    while (true) {
      if (partOne(input, wait) == 0) break
      wait++
      if (wait % 1000 == 0) println(wait)
    }
    return wait
  }

//  fun parse(input: List<String>): List<?> {
//    val result = listOf<>()
//
//    return result
//  }

}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day13_input.txt").readLines().map { val line = it.split(": "); Day13.Layer(line[0].toInt(), line[1].toInt()) }

  println("Part One = ${Day13.partOne(input, 0)}")
  println("Part Two = ${Day13.partTwo(input)}")
}
