package aoc2018.kot

import java.io.File
import java.util.*

object Day13 {
  data class Cart(var x: Int, var y: Int, var dX: Int = 0, var dY: Int = 0, var next: Int = 0, var alive: Boolean = true) : Comparable<Cart> {
    override fun compareTo(other: Cart) = if (y == other.y) x.compareTo(other.x) else y.compareTo(other.y)

    fun tick(tracks: List<String>) {
      x += dX
      y += dY
      when (tracks[y][x]) {
        '\\' -> dX = dY.also { dY = dX }
        '/' -> dX = -dY.also { dY = -dX }
        '+' -> {
          when (next) { //rotation via complex numbers : left-turn = i, right-turn = -i
            0 -> dX = dY.also { dY = -dX }
            2 -> dX = -dY.also { dY = dX }
          }
          next = (next + 1) % 3
        }
      }
    }
  }

  fun partOne(input: List<String>): String {
    val carts = PriorityQueue<Cart>(getCarts(input))
    while (true) {
      carts.forEach { c ->
        c.tick(input)
        if (carts.any { c != it && c.x == it.x && c.y == it.y }) {
          return "${c.x},${c.y}"
        }
      }
    }
  }

  fun partTwo(input: List<String>): String {
    val carts = getCarts(input).sorted().toMutableList()
    var tick = 0
    while (carts.count { it.alive } > 1) {
      carts.forEach { c ->
        if (c.alive) {
          c.tick(input)
          val col = carts.find { it.alive && c != it && c.x == it.x && c.y == it.y }
          if (col != null) {
            c.alive = false
            col.alive = false
          }
        }
      }
      carts.sort()
      tick++
    }
    val cart = carts.first { it.alive }
    return "${cart.x},${cart.y}"
  }

  private fun getCarts(input: List<String>) = input.withIndex().flatMap { (y, line) ->
    line.withIndex().mapNotNull { (x, value) ->
      when (value) {
        '^' -> Cart(x, y, dY = -1)
        'v' -> Cart(x, y, dY = 1)
        '<' -> Cart(x, y, dX = -1)
        '>' -> Cart(x, y, dX = 1)
        else -> null
      }
    }
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day13_input.txt").readLines()
  println("Part One = ${Day13.partOne(input)}")
  println("Part Two = ${Day13.partTwo(input)}")
}
