package aoc2018.kot

import getNumbers
import java.io.File

object Day04 {
  data class Guard(val id: Int, val sleeps: MutableList<List<Int>> = mutableListOf())

  fun partOne(input: List<String>): Int {
    val guards = parse(input)
    val maxSleeper = guards.maxBy { it.sleeps.sumBy { it.last() - it.first() } }!!
    val sleepTime = maxSleeper.sleeps.flatten().groupingBy { it }.eachCount().maxBy { it.value }!!.key
    return sleepTime * maxSleeper.id
  }

  fun partTwo(input: List<String>): Int {
    val guards = parse(input)
    val (gId, min) = guards.map {
      Pair(it.id, it.sleeps.flatten().groupingBy { it }.eachCount().maxBy { it.value }!!)
    }.maxBy { (_, count) -> count.value }!!
    return gId * min.key
  }

  fun parse(inputs: List<String>): Collection<Guard> {
    val guards = mutableMapOf<Int, Guard>()
    var guardID = -1
    var startSleep = 0

    for (line in inputs.sorted()) {
      val nums = line.getNumbers()
      when {
        "Guard" in line -> guardID = nums[5]
        "falls" in line -> startSleep = nums[4]
        else -> guards.computeIfAbsent(guardID) { Guard(guardID) }.sleeps.add((startSleep..nums[4]).toList())
      }
    }
    return guards.values.filter { it.sleeps.isNotEmpty() }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day04_input.txt").readLines()
  println("Part One = ${Day04.partOne(input)}")
  println("Part Two = ${Day04.partTwo(input)}")
}
