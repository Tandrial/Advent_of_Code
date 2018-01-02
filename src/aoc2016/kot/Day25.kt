package aoc2016.kot

import VM
import java.io.File

fun main(args: Array<String>) {
  val s = File("./input/2016/Day25_input.txt").readLines()
  var result: Long
  var regA: Long = -1L
  do {
    regA++
    val vm = VM(s, listOf("a" to regA, "lastOut" to -1L))
    result = vm.run()
  } while (result == -1L)
  println("Part One = $regA")
}


