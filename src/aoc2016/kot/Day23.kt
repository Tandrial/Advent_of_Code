package aoc2016.kot

import java.io.File

fun main(args: Array<String>) {
  val s = File("./input/2016/Day23_input.txt").readLines()
  println("Part One = ${VM(s, regA = 7).run()}")
  println("Part Two = ${VM(s, regA = 12).run()}")
}
