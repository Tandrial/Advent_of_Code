package aoc2017.kot

import getWords
import java.io.File

object Day15 {
  fun partOne(seedA: Long, seedB: Long): Int {
    val genA = gen(seedA, 16807)
    val genB = gen(seedB, 48271)
    return getCount(genA, genB, 40_000_000)
  }

  fun partTwo(seedA: Long, seedB: Long): Int {
    val genA = gen(seedA, 16807, 4)
    val genB = gen(seedB, 48271, 8)
    return getCount(genA, genB, 5_000_000)
  }

  private fun gen(seed: Long, mult: Int, filter: Long = 1): Sequence<Long> = generateSequence(seed) {
    var next = it
    do {
      next = (next * mult) % Int.MAX_VALUE
    } while (next and (filter - 1) != 0L)
    next
  }

  private fun getCount(seqA: Sequence<Long>, seqB: Sequence<Long>, length: Int): Int {
    val values = seqA.zip(seqB).take(length)
    return values.count { (a, b) -> a and 0xFFFF == b and 0xFFFF }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day15_input.txt").readLines()
  val seeds = input.map { it.getWords()[4].toLong() }
  println("Part One = ${Day15.partOne(seeds[0], seeds[1])}")
  println("Part Two = ${Day15.partTwo(seeds[0], seeds[1])}")
}
