package aoc2018.kot

import getNumbers
import java.io.File

object Day16 {
  fun partOne(input: List<String>) = input.chunked(4).takeWhile { it[0] != it[1] }.count { (before, op, after) ->
    check(before.getNumbers(), op.getNumbers(), after.getNumbers()).size >= 3
  }

  fun partTwo(input: List<String>): Int {
    val calibration = input.chunked(4).takeWhile { it[0] != it[1] }
    val matchings = calibration.map { (before, op, after) ->
      val ops = op.getNumbers()
      val possible = check(before.getNumbers(), ops, after.getNumbers()).toMutableSet()
      Pair(ops.first(), possible)
    }.toMap()
    while (matchings.any { it.value.size > 1 }) {
      matchings.filter { it.value.size == 1 }.forEach { id, set ->
        matchings.forEach { id2, set2 -> if (id != id2) set2.removeAll(set) }
      }
    }

    val program = input.drop(4 * calibration.size + 2).map {
      val (opCode, in1, in2, out1) = it.getNumbers()
      "${matchings[opCode]!!.first()} $in1 $in2 $out1"
    }
    return program.fold(listOf(0, 0, 0, 0)) { regs, instruction -> apply(regs, instruction) }[0]
  }

  private fun check(before: List<Int>, line: List<Int>, after: List<Int>): List<String> {
    val (_, in1, in2, out1) = line
    return listOf("addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori", "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr")
            .filter { apply(before, "$it $in1 $in2 $out1") == after }
  }

  private fun apply(oldRegs: List<Int>, line: String): List<Int> {
    val (in1, in2, out1) = line.split(" ").drop(1).map { it.toInt() }
    val regs = oldRegs.toMutableList()
    when (line.split(" ")[0]) {
      "addr" -> regs[out1] = regs[in1] + regs[in2]
      "addi" -> regs[out1] = regs[in1] + in2

      "mulr" -> regs[out1] = regs[in1] * regs[in2]
      "muli" -> regs[out1] = regs[in1] * in2

      "banr" -> regs[out1] = regs[in1] and regs[in2]
      "bani" -> regs[out1] = regs[in1] and in2

      "borr" -> regs[out1] = regs[in1] or regs[in2]
      "bori" -> regs[out1] = regs[in1] or in2

      "setr" -> regs[out1] = regs[in1]
      "seti" -> regs[out1] = in1

      "gtir" -> regs[out1] = if (in1 > regs[in2]) 1 else 0
      "gtri" -> regs[out1] = if (regs[in1] > in2) 1 else 0
      "gtrr" -> regs[out1] = if (regs[in1] > regs[in2]) 1 else 0

      "eqir" -> regs[out1] = if (in1 == regs[in2]) 1 else 0
      "eqri" -> regs[out1] = if (regs[in1] == in2) 1 else 0
      "eqrr" -> regs[out1] = if (regs[in1] == regs[in2]) 1 else 0
    }
    return regs
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day16_input.txt").readLines()
  println("Part One = ${Day16.partOne(input)}")
  println("Part Two = ${Day16.partTwo(input)}")
}
