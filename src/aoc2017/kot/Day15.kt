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
      // after = (after * mult) % Int.MAX_VALUE
      // Int.MAX_VALUE is a Mersenne prime 2^s - 1, so we can calculate the mod with:
      //https://ariya.io/2007/02/modulus-with-mersenne-prime
      //  int i = (k & p) + (k >> s);
      //  return (i >= p) ? i - p : i;
      next *= mult
      next = (next and 0x7fffffff) + (next shr 31)
      next = if (next shr 31 != 0L) next - 0x7fffffff else next
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
