package aoc2016.kot

import java.io.File

fun main(args: Array<String>) {
  val s = File("./input/2016/Day12_input.txt").readLines()
  println("Part One = ${VM(s).run()}")
  println("Part Two = ${VM(s, regC = 1).run()}")
}
