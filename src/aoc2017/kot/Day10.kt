package aoc2017.kot

import itertools.chunksOfSize
import toHexString
import toIntList
import java.io.File

object Day10 {
  fun hashRound(input: List<Int>, rep: Int = 1): List<Int> {
    var mem = (0..255).toList()
    var pos = 0
    var skipS = 0
    repeat(rep) {
      for (len in input) {
        // reverse subList of mem pos..(pos+len)
        if (pos + len < mem.size) {
          // do wrapping
          mem = mem.subList(0, pos).plus(mem.subList(pos, pos + len).reversed()).plus(mem.subList(pos + len, mem.size))
        } else {
          // wrapping - move subList pos..(mem.size) to the front, reverse 0..len and move back
          mem = mem.subList(pos, mem.size).plus(mem.subList(0, pos))
          mem = mem.subList(0, len).reversed().plus(mem.subList(len, mem.size))
          mem = mem.subList(mem.size - pos, mem.size).plus(mem.subList(0, mem.size - pos))
        }
        pos = (pos + len + skipS++) % mem.size
      }
    }
    return mem
  }

  fun partTwo(input: List<Int>, rounds: Int): List<Int> = hashRound(input, rounds).chunksOfSize(16).map { it.reduce { a, b -> a.xor(b) } }.toList()
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day10_input.txt").readText().toIntList(",".toPattern())
  var result = Day10.hashRound(input)
  println("Part One = ${result[0] * result[1]}")
  val input2 = File("./input/2017/Day10_input.txt").readText().toCharArray().map { it.toInt() } + listOf(17, 31, 73, 47, 23)
  result = Day10.partTwo(input2, 64)
  println("Part Two = ${result.toHexString()}")
}
