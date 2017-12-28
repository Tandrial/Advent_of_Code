package aoc2017.kot

import java.io.File
import java.util.regex.Pattern

object Day08 {

  fun solve(input: List<String>): Pair<Int, Int> {
    val memory = mutableMapOf<String, Int>()
    val regex = Pattern.compile("(\\w+) (inc|dec) (-?\\d+) if (\\w+) (>|<|==|>=|<=|!=) (-?\\d+)")
    var maxCurr = -1
    for (line in input) {
      val m = regex.matcher(line)
      if (m.find()) {
        val reg = m.group(1).toString()
        var mod = m.group(3).toInt()
        if (m.group(2).toString() == "dec") mod *= -1

        val checkReg = m.group(4).toString()
        val value = m.group(6).toInt()

        val cmp: (Int, Int) -> Boolean = when (m.group(5)) {
          ">" -> { r, v -> r > v }
          "<" -> { r, v -> r < v }
          ">=" -> { r, v -> r >= v }
          "<=" -> { r, v -> r <= v }
          "==" -> { r, v -> r == v }
          "!=" -> { r, v -> r != v }
          else -> { _, _ -> false }
        }

        if (cmp(memory.getOrPut(checkReg, { 0 }), value)) memory[reg] = memory.getOrPut(reg, { 0 }) + mod
        maxCurr = maxOf(memory.getOrPut(reg, { 0 }), maxCurr)
      }
    }
    return Pair(memory.values.max()!!, maxCurr)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day08_input.txt").readLines()
  val (partOne, partTwo) = Day08.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
