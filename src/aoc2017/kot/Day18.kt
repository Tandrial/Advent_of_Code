package aoc2017.kot

import java.io.File

object Day18 {

  class VM(val input: List<String>, regP: Long = 0, val partOne: Boolean = false) {
    private val ram = input.map { ("$it .").split(" ") }
    private val regs = longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, regP, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var pc = 0

    var count = 0
    val inputQueue = mutableListOf<Long>()
    val outQueue = mutableListOf<Long>()
    private fun isReg(s: String): Boolean = s[0] in 'a'..'z'

    private fun getValue(s: String): Long = when (isReg(s)) {
      true -> regs[s[0] - 'a']
      false -> s.toLong()
    }

    fun run(): Long {
      while (pc < ram.size) {
        val (inst, op1, op2) = ram[pc]
        when (inst) {
          "snd" -> {
            outQueue.add(getValue(op1))
            count++
          }
          "rcv" -> when {
            partOne -> return outQueue.last()
            inputQueue.size == 0 -> return -1
            else -> regs[op1[0] - 'a'] = inputQueue.removeAt(0)
          }
          "set" -> if (isReg(op1)) regs[op1[0] - 'a'] = getValue(op2)
          "add" -> if (isReg(op1)) regs[op1[0] - 'a'] += getValue(op2)
          "mul" -> if (isReg(op1)) regs[op1[0] - 'a'] *= getValue(op2)
          "mod" -> if (isReg(op1)) regs[op1[0] - 'a'] %= getValue(op2)
          "jgz" -> if (getValue(op1) > 0L) pc += getValue(op2).toInt() - 1
        }
        pc++
      }
      return 0
    }
  }

  fun partOne(input: List<String>): Int = VM(input, partOne = true).run().toInt()

  fun partTwo(input: List<String>): Int {
    val vm1 = VM(input, regP = 0)
    val vm2 = VM(input, regP = 1)
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
    return vm2.count
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day18_input.txt").readLines()
  println("Part One = ${Day18.partOne(input)}")
  println("Part Two = ${Day18.partTwo(input)}")
}
