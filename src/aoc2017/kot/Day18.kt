package aoc2017.kot

import VM
import java.io.File

object Day18 {

  fun partOne(input: List<String>): Long = VM(input, listOf("partOne" to 1L)).run()

  fun partTwo(input: List<String>): Long {
    val vm1 = VM(input, listOf("p" to 0L))
    val vm2 = VM(input, listOf("p" to 1L))
    var result1 = 1L
    var result2 = 1L
    while (true) {
      if (result1 != 0L) result1 = vm1.run()
      vm2.inputQueue.addAll(vm1.outQueue)
      vm1.outQueue.clear()
      if (result2 != 0L) result2 = vm2.run()
      vm1.inputQueue.addAll(vm2.outQueue)
      vm2.outQueue.clear()
      if (result1 == 0L && result2 == 0L) break
      if (result2 < 0L && result1 < 0L && vm1.inputQueue.isEmpty() && vm2.inputQueue.isEmpty()) break
    }
    return vm2.getValue("SendCount")
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day18_input.txt").readLines()
  println("Part One = ${Day18.partOne(input)}")
  println("Part Two = ${Day18.partTwo(input)}")
}
