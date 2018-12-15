package aoc2017.kot

import java.io.File
import kotlin.system.measureTimeMillis

object Day13 {
  data class Layer(val depth: Int, val range: Int) {
    fun isAtStart(offset: Int): Boolean = (depth + offset) % (2 * (range - 1)) == 0
  }

  // Sum of all layers which are at position 0 when we pass
  fun partOne(input: List<String>): Int = parse(input).filter { it.isAtStart(0) }.sumBy { it.depth * it.range }

  fun partTwo(input: List<String>): Int {
    val layers = parse(input)
    // Find the smallest time we have to wait where no layer is at position 0 when we pass
    return generateSequence(0) { it + 1 }.first { layers.none { layer -> layer.isAtStart(it) } }
  }

  private fun parse(input: List<String>): List<Layer> = input.map { val line = it.split(": "); Layer(line[0].toInt(), line[1].toInt()) }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day13_input.txt").readLines()
  println("Part One = ${Day13.partOne(input)}")
  println("Part One = ${Day13.partTwo(input)}")
  println("Part Two = ${measureTimeMillis { Day13.partTwo(input) }}")
}
