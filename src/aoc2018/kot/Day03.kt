package aoc2018.kot

import getNumbers
import java.io.File

object Day03 {
  data class Claim(val id: Int, val x: Int, val y: Int, val xsize: Int, val ysize: Int)

  fun partOne(input: List<String>): Int = putClaims(parse(input)).sumBy {
    it.count { it > 1 }
  }

  fun partTwo(input: List<String>): Int {
    val claims = parse(input)
    val grid = putClaims(claims)

    return claims.first { claim ->
      var check = true
      for (x in (claim.x until claim.x + claim.xsize))
        for (y in (claim.y until claim.y + claim.ysize))
          if (grid[x][y] != 1) check = false
      check
    }.id
  }

  private fun parse(input: List<String>): List<Claim> = input.map {
    val (id, x, y, xSize, ySize) = it.getNumbers()
    Claim(id, x, y, xSize, ySize)
  }

  private fun putClaims(claims: List<Claim>): Array<IntArray> {
    val array = Array(1000) { IntArray(1000) }

    for (claim in claims) {
      for (x in (claim.x until claim.x + claim.xsize))
        for (y in (claim.y until claim.y + claim.ysize))
          array[x][y]++
    }
    return array
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day03_input.txt").readLines()
  println("Part One = ${Day03.partOne(input)}")
  println("Part Two = ${Day03.partTwo(input)}")
}
