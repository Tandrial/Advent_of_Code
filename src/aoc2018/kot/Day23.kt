package aoc2018.kot

import diffBy
import getNumbers
import java.io.File
import java.util.*
import kotlin.math.abs

object Day23 {

  data class State(val sender: Sender, val inRange: Int) : Comparable<State> {
    override fun compareTo(other: State) = if (other.inRange == inRange) sender.r.compareTo(other.sender.r) else other.inRange.compareTo(inRange)
  }

  data class Sender(val x: Long, val y: Long, val z: Long, var r: Long) {
    val dist = abs(x) + abs(y) + abs(z)
    fun inRange(other: Sender) = (abs(x - other.x) + abs(y - other.y) + abs(z - other.z)) <= r
    fun intersect(other: Sender) = (abs(x - other.x) + abs(y - other.y) + abs(z - other.z)) <= r + other.r
  }

  fun partOne(input: List<String>): Int {
    val senders = parse(input)
    val maxSender = senders.maxBy { it.r }!!

    return senders.count { maxSender.inRange(it) }
  }

  fun partTwo(input: List<String>): Long {
    val senders = parse(input)
    val radius = maxOf(senders.diffBy { it.x }, senders.diffBy { it.y }, senders.diffBy { it.z })
    val checked = mutableSetOf<Sender>()
    val queue = PriorityQueue<State>(listOf(State(Sender(0, 0, 0, radius), 0)))
    var max = queue.peek()

    while (queue.isNotEmpty()) {
      val (sender, inRange) = queue.poll()

      if (sender.r == 0L) {
        if (inRange > max.inRange || (inRange == max.inRange && sender.dist < max.sender.dist)) {
          max = State(sender, inRange)
          queue.removeIf { it.inRange <= inRange }
        }
      } else if (checked.add(sender)) {
        val neuR = sender.r / 2
        val next = (-1L..1L).flatMap { xOff ->
          (-1L..1L).flatMap { yOff ->
            (-1L..1L).mapNotNull { zOff ->
              val neu = Sender(sender.x + xOff * neuR, sender.y + yOff * neuR, sender.z + zOff * neuR, neuR)
              val count = senders.count { it.intersect(neu) }
              if (count >= max.inRange)
                State(neu, count)
              else null
            }
          }
        }
        queue.addAll(next)
      }
    }
    return max.sender.dist
  }

  fun parse(input: List<String>) = input.map {
    val (x, y, z, r) = it.getNumbers()
    Sender(x.toLong(), y.toLong(), z.toLong(), r.toLong())
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day23_input.txt").readLines()
  println("Part One = ${Day23.partOne(input)}")
  println("Part Two = ${Day23.partTwo(input)}")
}
