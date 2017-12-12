package aoc2016.kot

import java.io.File

fun main(args: Array<String>) {
  val s = File("./input/2016/Day25_input.txt").readLines()
  var result: Long
  var regA: Long = 0
  do {
    regA++
    result = VM(s, regA = regA).run()
  } while (result == -1L)
  println("Part One = $regA")
}


