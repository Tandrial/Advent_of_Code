package aoc2016.kot

import VM
import java.io.File

fun main(args: Array<String>) {
  val s = File("./input/2016/Day12_input.txt").readLines()
  var vm = VM(s); vm.run()
  val partOne = vm.getValue("a")
  println("Part One = $partOne")
  vm = VM(s, listOf("c" to 1L)); vm.run()
  val partTwo = vm.getValue("a")
  println("Part Two = $partTwo")
}
