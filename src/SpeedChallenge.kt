import java.io.File
import kotlin.system.measureTimeMillis

fun measureTime(name: String, part1: () -> Unit, part2: () -> Unit, maxTime: Long) {
  val timeP1 = measureTimeMillis(part1)
  val timeP2 = measureTimeMillis(part2)
  val fail = if (timeP1 > maxTime || timeP2 > maxTime) "<<<" else ""
  println("$name \t${"%4d".format(timeP1)} \t${"%4d".format(timeP2)} $fail")
}

data class Solution(val name: String, val part1: () -> Unit, val part2: () -> Unit)

fun testAoC2017() {
  val days = mutableListOf<Solution>()
  days.add(Solution("Day_01", {
    val input = File("./input/2017/Day01_input.txt").readText()
    aoc2017.kot.Day01.partOne(input)
  }, {
    val input = File("./input/2017/Day01_input.txt").readText()
    aoc2017.kot.Day01.partTwo(input)
  }))
  days.add(Solution("Day_02", {
    val input = File("./input/2017/Day02_input.txt").to2DIntArr()
    aoc2017.kot.Day02.partOne(input)
  }, {
    val input = File("./input/2017/Day02_input.txt").to2DIntArr()
    aoc2017.kot.Day02.partTwo(input)
  }))
  days.add(Solution("Day_03", {
    aoc2017.kot.Day03.partOne(361527)
  }, {
    aoc2017.kot.Day03.partTwo(361527)
  }))

  days.add(Solution("Day_04", {
    val input = File("./input/2017/Day04_input.txt").readLines()
    aoc2017.kot.Day04.partOne(input)
  }, {
    val input = File("./input/2017/Day04_input.txt").readLines()
    aoc2017.kot.Day04.partTwo(input)
  }))

  days.add(Solution("Day_05", {
    val input = File("./input/2017/Day05_input.txt").readLines().map { it.toInt() }
    aoc2017.kot.Day05.partOne(input)
  }, {
    val input = File("./input/2017/Day05_input.txt").readLines().map { it.toInt() }
    aoc2017.kot.Day05.partTwo(input)
  }))

  days.add(Solution("Day_06", {
    val input = File("./input/2017/Day06_input.txt").readText().toIntList()
    aoc2017.kot.Day06.solve(input)
  }, { }))

  days.add(Solution("Day_07", {
    val input = File("./input/2017/Day07_input.txt").readLines()
    aoc2017.kot.Day07.partOne(input)
  }, {
    val input = File("./input/2017/Day07_input.txt").readLines()
    val result = aoc2017.kot.Day07.partOne(input)
    aoc2017.kot.Day07.partTwo(input, result)
  }))

  days.add(Solution("Day_08", {
    val input = File("./input/2017/Day08_input.txt").readLines()
    aoc2017.kot.Day08.solve(input)
  }, { }))

  days.add(Solution("Day_09", {
    val input = File("./input/2017/Day09_input.txt").readText()
    aoc2017.kot.Day09.solve(input)
  }, { }))

  days.add(Solution("Day_10", {
    val input = File("./input/2017/Day10_input.txt").readText().toIntList(",".toPattern())
    aoc2017.kot.Day10.hashRound(input)
  }, {
    val input2 = File("./input/2017/Day10_input.txt").readText().toCharArray().map { it.toInt() } + listOf(17, 31, 73, 47, 23)
    aoc2017.kot.Day10.partTwo(input2, 64)
  }))

  days.add(Solution("Day_11", {
    val input = File("./input/2017/Day11_input.txt").readText().split(",")
    aoc2017.kot.Day11.solve(input)
  }, { }))

  days.add(Solution("Day_12", {
    val input = File("./input/2017/Day12_input.txt").readLines()
    val connections = input.map { val all = it.getWords(); all[0] to all.drop(1) }.toMap()
    aoc2017.kot.Day12.partOne(connections, "0")
  }, {
    val input = File("./input/2017/Day12_input.txt").readLines()
    val connections = input.map { val all = it.getWords(); all[0] to all.drop(1) }.toMap()
    aoc2017.kot.Day12.partTwo(connections)
  }))

  days.add(Solution("Day_13", {
    val input = File("./input/2017/Day13_input.txt").readLines()
    aoc2017.kot.Day13.partOne(input)
  }, {
    val input = File("./input/2017/Day13_input.txt").readLines()
    aoc2017.kot.Day13.partTwo(input)
  }))

  days.add(Solution("Day_14", {
    val input = "vbqugkhl"
    aoc2017.kot.Day14.partOne(input)
  }, {
    val input = "vbqugkhl"
    aoc2017.kot.Day14.partTwo(input)
  }))

  days.add(Solution("Day_15", {
    val input = File("./input/2017/Day15_input.txt").readLines()
    val seeds = input.map { it.getWords()[4].toLong() }
    aoc2017.kot.Day15.partOne(seeds[0], seeds[1])
  }, {
    val input = File("./input/2017/Day15_input.txt").readLines()
    val seeds = input.map { it.getWords()[4].toLong() }
    aoc2017.kot.Day15.partTwo(seeds[0], seeds[1])
  }))

  days.add(Solution("Day_16", {
    val input = File("./input/2017/Day16_input.txt").readText().split(",")
    aoc2017.kot.Day16.solve(input)
  }, {
    val input = File("./input/2017/Day16_input.txt").readText().split(",")
    aoc2017.kot.Day16.solve(input, 1_000_000_000)
  }))

  days.add(Solution("Day_17", {
    aoc2017.kot.Day17.partOne(304, 2017)
  }, {
    aoc2017.kot.Day17.partTwo(304, 50_000_000)
  }))

  days.add(Solution("Day_18", {
    val input = File("./input/2017/Day18_input.txt").readLines()
    aoc2017.kot.Day18.partOne(input)
  }, {
    val input = File("./input/2017/Day18_input.txt").readLines()
    aoc2017.kot.Day18.partTwo(input)
  }))

  days.add(Solution("Day_19", {
    val input = File("./input/2017/Day19_input.txt").readLines().map { it.toCharArray() }
    aoc2017.kot.Day19.solve(input)
  }, { }))

  days.add(Solution("Day_20", {
    val input = File("./input/2017/Day20_input.txt").readLines()
    aoc2017.kot.Day20.partOne(input)
  }, {
    val input = File("./input/2017/Day20_input.txt").readLines()
    aoc2017.kot.Day20.partTwo(input)
  }))

  days.add(Solution("Day_21", {
    val input = File("./input/2017/Day21_input.txt").readLines()
    val start = ".#./..#/###"
    aoc2017.kot.Day21.solve(start, input, 5)

  }, {
    val input = File("./input/2017/Day21_input.txt").readLines()
    val start = ".#./..#/###"
    aoc2017.kot.Day21.solve(start, input, 18)
  }))

  days.add(Solution("Day_22", {
    val input = File("./input/2017/Day22_input.txt").readLines()
    aoc2017.kot.Day22.solve(input, 10000)
  }, {
    val input = File("./input/2017/Day22_input.txt").readLines()
    aoc2017.kot.Day22.solve(input, 10000000, true)
  }))

  days.add(Solution("Day_23", {
    val input = File("./input/2017/Day23_input.txt").readLines()
    aoc2017.kot.Day23.partOne(input)
  }, {
    val input = File("./input/2017/Day23_input.txt").readLines()
    aoc2017.kot.Day23.partTwo(input)
  }))

  days.add(Solution("Day_24", {
    val input = File("./input/2017/Day24_input.txt").readLines()
    aoc2017.kot.Day24.solve(input)
  }, { }))

  days.add(Solution("Day_25", {
    val input = File("./input/2017/Day25_input.txt").readLines()
    aoc2017.kot.Day25.solve(input)
  }, { }))

  println("Day\t\tPart 1\tPart 2")
  days.forEach { measureTime(it.name, it.part1, it.part2, 500) }
}


fun main(args: Array<String>) {
  testAoC2017()
}