package aoc2018.kot

object Day11 {
  data class Result(val x: Int = 0, val y: Int = 0, val power: Int = Int.MIN_VALUE, val size: Int = 0) {
    override fun toString() = "$x,$y,$size"
  }

  fun partOne(gId: Int) = solve(3, calcSumGrid(gId, 300)).toString().dropLast(2)

  fun partTwo(gId: Int): String {
    val grid = calcSumGrid(gId, 300)
    return (1..300).map { solve(it, grid) }.maxBy { it.power }!!.toString()
  }

  private fun calcSumGrid(gId: Int, maxSize: Int): Array<IntArray> {
    val grid = Array(maxSize + 1) { IntArray(maxSize + 1) }
    for (x in (1 until grid.size)) {
      for (y in (1 until grid[0].size)) {
        grid[x][y] = getPower(x, y, gId) + grid[x - 1][y] + grid[x][y - 1] - grid[x - 1][y - 1]
      }
    }
    return grid
  }

  private fun getPower(x: Int, y: Int, gId: Int) = (((((x + 10) * y) + gId) * (x + 10)) / 100) % 10 - 5

  private fun solve(size: Int, grid: Array<IntArray>): Result {
    var max = Result()
    for (x in (1..300 - size)) {
      for (y in (1..300 - size)) {
        val power = grid[x][y] + grid[x + size][y + size] - grid[x + size][y] - grid[x][y + size]
        if (power > max.power) {
          max = Result(x + 1, y + 1, power, size)
        }
      }
    }
    return max
  }
}

fun main(args: Array<String>) {
  val gId = 1308
  println("Part One = ${Day11.partOne(gId)}")
  println("Part Two = ${Day11.partTwo(gId)}")
}
