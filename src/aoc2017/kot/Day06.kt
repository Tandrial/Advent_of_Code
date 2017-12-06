package aoc2017.kot

import toIntList
import java.io.File

object Day06 {
  fun solve(input: List<Int>) {
    val mem = input.toMutableList()
    val seen = mutableSetOf<List<Int>>()
    do {
      var (idx, max) = mem.withIndex().maxBy { it.value }!!
      mem[idx] = 0
      while (max-- > 0) {
        mem[++idx % mem.size]++
      }
    } while (seen.add(mem.toList()))
    println("Part One = ${seen.size + 1}")
    println("Part Two = ${seen.size - seen.indexOf(mem.toList())}")
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day06_input.txt").readText().toIntList()
  Day06.solve(input)
}
