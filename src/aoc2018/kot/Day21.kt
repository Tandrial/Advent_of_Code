package aoc2018.kot

import VM18
import getNumbers
import getWords
import java.io.File

object Day21 {
  fun partOne(input: List<String>): Long {
    val vm = VM18(input.drop(1), "012345".map { it.toString() to 0L }, input.first().getWords()[1])
    return vm.run(day21 = true)
  }

  fun partTwo(input: List<String>): Int {
    val num = input[8].getNumbers().first()
    val seen = mutableSetOf<Int>()
    var last = -1
    var r4 = 0
    while (true) {
      var r5 = r4 or 0x10000
      r4 = num
      while (true) {
        r4 = (((r4 + (r5 and 255)) and 16777215) * 65899) and 16777215
        if (256 > r5) {
          if (seen.add(r4)) {
            last = r4
            break
          } else {
            return last
          }
        } else
          r5 /= 256
      }
    }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day21_input.txt").readLines()
  println("Part One = ${Day21.partOne(input)}")
  println("Part Two = ${Day21.partTwo(input)}")
}
