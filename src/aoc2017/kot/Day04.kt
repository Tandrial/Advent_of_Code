package aoc2017.kot

import getWords
import sort
import java.io.File

object Day04 {

  fun partOne(input: List<String>): Int = input.map { it.getWords() }.count { it.size == it.toSet().size }

  fun partTwo(input: List<String>): Int = input.map {
    it.getWords().map { it.sort() }
  }.count { it.size == it.toSet().size }

}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day04_input.txt").readLines()
  println("Part One = ${Day04.partOne(input)}")
  println("Part Two = ${Day04.partTwo(input)}")
}
