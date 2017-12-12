package aoc2017.kot

import getWords
import java.io.File

object Day07 {
  data class Program(val name: String, val oWeight: Int, val supports: List<String> = listOf(), var weight: Int = oWeight)

  private fun parse(input: List<String>): List<Program> = input.map {
    val line = it.getWords()
    Program(line[0], line[1].toInt(), line.drop(2))
  }

  fun partOne(input: List<String>): String {
    val out = parse(input)
    val onRightSide = out.flatMap { it.supports }
    return out.first { it.name !in onRightSide }.name
  }

  fun partTwo(input: List<String>, rootNode: String): Int {
    val lookUp = parse(input).associateBy { it.name }
    updatedWeights(lookUp[rootNode]!!, lookUp)

    for (p in lookUp.values) {
      val weights = p.supports.map { lookUp[it] }.groupBy { it!!.weight }
      if (weights.size > 1) {
        val correctWeight = weights.filterValues { it.size > 1 }.keys.toList()[0]
        val wrongWeight = weights.filterValues { it.size == 1 }.keys.toList()[0]
        val diff = correctWeight - wrongWeight
        return (weights[wrongWeight]!![0]?.oWeight ?: 0) + if (correctWeight > wrongWeight) -diff else diff
      }
    }
    return -1
  }

  private fun updatedWeights(p: Program, lookup: Map<String, Program>) {
    for (support in p.supports) {
      updatedWeights(lookup[support]!!, lookup)
      lookup[p.name]!!.weight += lookup[support]!!.weight
    }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day07_input.txt").readLines()
  val result = Day07.partOne(input)
  println("Part One = $result")
  println("Part Two = ${Day07.partTwo(input, result)}")
}