package aoc2018.kot

import getNumbers
import java.io.File

object Day10 {

  data class Particle(var x: Int, var y: Int, val xSpeed: Int, val ySpeed: Int) {
    fun tick() {
      x += xSpeed
      y += ySpeed
    }
  }

  fun partOne(input: List<String>): Int {
    val current = parse(input)
    var s = 0
    while (true) {
      val (lminX, lminY) = Pair(current.minBy { it.x }!!.x, current.minBy { it.y }!!.y)
      val (lmaxX, lmaxY) = Pair(current.maxBy { it.x }!!.x, current.maxBy { it.y }!!.y)
      current.forEach(Particle::tick)
      val (minX, minY) = Pair(current.minBy { it.x }!!.x, current.minBy { it.y }!!.y)
      val (maxX, maxY) = Pair(current.maxBy { it.x }!!.x, current.maxBy { it.y }!!.y)
      s++
      if (lmaxY - lminY < maxY - minY && lmaxX - lminX < maxX - minX) {
        println("Part One = ")
        (lminY..lmaxY).forEach { y ->
          val sb = StringBuilder()
          val row = current.filter { it.y - it.ySpeed == y }.map { it.x - it.xSpeed }
          (lminX..lmaxX).forEach { if (it in row) sb.append('#') else sb.append(' ') }
          println(sb.toString())
        }
        return s - 1
      }
    }
  }

  fun parse(input: List<String>): List<Particle> = input.map {
    val (x, y, xSpeed, ySpeed) = it.getNumbers()
    Particle(x, y, xSpeed, ySpeed)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day10_input.txt").readLines()
  println("Part Two = ${Day10.partOne(input)}")
}
