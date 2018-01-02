package aoc2016.kot

import VM
import java.io.File

object Day23 {
  fun partOne(input: List<String>): Long {
    val vm = VM(input, listOf("a" to 7L)); vm.run()
    return vm.getValue("a")
  }

  fun partTwo(input: List<String>): Long {
    val patch = listOf("cpy b a", "mul a d", "cpy 0 c", "cpy 0 d", "jnz 0 0", "jnz 0 0")
    val program = input.subList(0, 4) + patch + input.subList(10, input.size)
    val vm = VM(program, listOf("a" to 12L))
    vm.run()
    return vm.getValue("a")
  }
}

fun main(args: Array<String>) {
  val s = File("./input/2016/Day23_input.txt").readLines()
  println("Part One = ${Day23.partOne(s)}")
  println("Part Two = ${Day23.partTwo(s)}")
}
