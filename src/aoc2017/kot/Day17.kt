package aoc2017.kot

object Day17 {
  fun partOne(stepSize: Int, steps: Int): Int {
    var pos = 0
    val lock = mutableListOf(0)

    repeat(steps) {
      pos = (pos + stepSize) % lock.size + 1
      lock.add(pos, lock.size)
    }
    return lock[pos + 1]
  }

  fun partTwo(stepSize: Int, steps: Int): Int {
    var pos = 0
    var valueAfterZero = -1

    for (size in 1..steps) {
      pos = (pos + stepSize) % size + 1
      if (pos == 1) valueAfterZero = size
    }
    return valueAfterZero
  }
}

fun main(args: Array<String>) {
  println("Part One = ${Day17.partOne(304, 2017)}")
  println("Part Two = ${Day17.partTwo(304, 50_000_000)}")
}
