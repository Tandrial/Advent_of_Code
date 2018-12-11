package aoc2018.kot

object Day11 {
  fun partOne(gId: Int) = solve(3, calcSumGrid(gId, 300)).first.toString()

  fun partTwo(gId: Int): String {
    val grid = calcSumGrid(gId, 300)

    return (1..300).map {
      val (loc, power) = solve(it, grid)
      Pair(listOf(loc.first, loc.second, it), power)
    }.maxBy { it.second }!!.first.toString()
  }

  private fun solve(size: Int, grid: Array<IntArray>): Pair<Pair<Int, Int>, Int> {
    var maxPower = Pair(Pair(1, 1), Int.MIN_VALUE)
    for (x in (1..300 - size)) {
      for (y in (1..300 - size)) {
        val power = grid[x][y] + grid[x + size][y + size] - grid[x + size][y] - grid[x][y + size]
        if (power > maxPower.second) {
          maxPower = Pair(Pair(x + 1, y + 1), power)
        }
      }
    }
    return maxPower
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
}

fun main(args: Array<String>) {
  val gId = 1308
  println("Part One = ${Day11.partOne(gId)}")
  println("Part Two = ${Day11.partTwo(gId)}")
}
