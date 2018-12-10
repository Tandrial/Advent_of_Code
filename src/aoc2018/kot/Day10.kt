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
    val lastTick = mutableListOf<Particle>()
    var s = 0
    while (true) {
      val minX = current.minBy { it.x }!!.x
      val minY = current.minBy { it.y }!!.y
      val maxX = current.maxBy { it.x }!!.x
      val maxY = current.maxBy { it.y }!!.y
      if (lastTick.isNotEmpty()) {
        val lminX = lastTick.minBy { it.x }!!.x
        val lminY = lastTick.minBy { it.y }!!.y
        val lmaxX = lastTick.maxBy { it.x }!!.x
        val lmaxY = lastTick.maxBy { it.y }!!.y
        if (lmaxY - lminY < maxY - minY && lmaxX - lminX < maxX - minX) {
          println("Part One = ")
          (lminY..lmaxY).forEach { y ->
            val sb = StringBuilder()
            val row = lastTick.filter { it.y == y }.map { it.x }
            (lminX..lmaxX).forEach { if (it in row) sb.append('#') else sb.append('.') }
            println(sb.toString())
          }
          return s - 1
        }
        lastTick.clear()
      }
      current.forEach {
        lastTick.add(it.copy())
        it.tick()
      }
      s++
      if (s > 11000) return -1
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
