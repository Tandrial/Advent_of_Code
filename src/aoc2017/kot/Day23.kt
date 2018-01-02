package aoc2017.kot

import VM
import java.io.File

object Day23 {

  fun partOne(input: List<String>): Long {
    val vm = VM(input)
    vm.run()
    return vm.getValue("MultCount")
  }

  fun partTwoFast(): Int {
    var count = 0
    for (i in (108400..125400).step(17)) {
      for (d in 2..Math.sqrt(i.toDouble()).toInt()) {
        if (i % d == 0) {
          count++; break
        }
      }
    }
    return count
  }

  fun partTwo(input: List<String>): Long {
    val patch = listOf(
            "set g b", "mod g d", "jnz g 2", "set f 0", "jnz f 2",
            "jnz 1 9", "sub d -1", "set g d", "sub g b", "jnz g -9", "jnz 1 4", "set a a", "set a a", "set a a"
    )
    val program = input.subList(0, 10) + patch + input.subList(24, input.size)
    val vm = VM(program, listOf("a" to 1L))
    vm.run()
    return vm.getValue("h")
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day23_input.txt").readLines()
  println("Part One = ${Day23.partOne(input)}")
  println("Part Two = ${Day23.partTwo(input)}")
}
