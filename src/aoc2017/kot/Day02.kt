package aoc2017.kot

import itertools.times
import to2DIntArr

import java.io.File

object Day02 {
  fun partOne(input: List<List<Int>>): Int = input.map { it.max()!! - it.min()!! }.sum()

  fun partTwo(input: List<List<Int>>): Int {
    return input.map {
      it.times(it).filter { (j, k) -> j != k && j % k == 0 }.map { it.first / it.second }.sum()
    }.sum()
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day02_input.txt").to2DIntArr("\t")
  println("Part One = ${Day02.partOne(input)}")
  println("Part Two = ${Day02.partTwo(input)}")
}