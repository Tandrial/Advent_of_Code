package aoc2017.kot

import java.io.File

object Day23 {


  class VM(val input: List<String>, regA: Long = 0, val partOne: Boolean = false) {
    private val ram = input.map { ("$it .").split(" ") }
    private val regs = longArrayOf(regA, 0, 0, 0, 0, 0, 0, 0)
    private var pc = 0
    private var count = 0L
    private fun isReg(s: String): Boolean = s[0] in 'a'..'h'

    private fun getValue(s: String): Long = when (isReg(s)) {
      true -> regs[s[0] - 'a']
      false -> s.toLong()
    }

    fun getRegH(): Long = regs['h' - 'a']

    fun run(): Long {
      while (pc < ram.size) {
        val (inst, op1, op2) = ram[pc]
        when (inst) {
          "set" -> if (isReg(op1)) regs[op1[0] - 'a'] = getValue(op2)
          "sub" -> if (isReg(op1)) regs[op1[0] - 'a'] -= getValue(op2)
          "mul" -> if (isReg(op1)) {
            regs[op1[0] - 'a'] *= getValue(op2); count++
          }
          "mod" -> if (isReg(op1)) regs[op1[0] - 'a'] %= getValue(op2)
          "jnz" -> if (getValue(op1) != 0L) pc += getValue(op2).toInt() - 1
        }
        pc++
      }
      return count

    }
  }

  fun partOne(input: List<String>): Long {
    return VM(input).run()
  }

  fun partTwo(input: List<String>): Long {
    val patch = listOf("set g b", "mod g d", "jnz g 2", "set f 0", "jnz f 2",
        "jnz 1 9", "sub d -1", "set g d", "sub g b", "jnz g -9", "jnz 1 4", "set a a", "set a a", "set a a")
    val program = input.subList(0, 10) + patch + input.subList(24, input.size)
    val vm = VM(program, regA = 1)
    vm.run()
    return vm.getRegH()
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day23_input.txt").readLines()
  println("Part One = ${Day23.partOne(input)}")
  println("Part Two = ${Day23.partTwo(input)}")

}


