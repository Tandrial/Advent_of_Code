package aoc2018.kot

import diffBy
import getNumbers
import java.io.File
import kotlin.math.abs

object Day23 {

    data class Sender(val x: Long, val y: Long, val z: Long, var r: Long) {
        fun inRange(other: Sender): Boolean {
            return (abs(x - other.x) + abs(y - other.y) + abs(z - other.z)) <= r
        }

        fun intersect(other: Sender): Boolean {
            return (abs(x - other.x) + abs(y - other.y) + abs(z - other.z)) <= r + other.r
        }
    }

    fun partOne(input: List<String>): Int {
        val senders = parse(input)
        val maxSender = senders.maxBy { it.r }!!

        return senders.count { maxSender.inRange(it) }
    }

    fun partTwo(input: List<String>): Long {
        val senders = parse(input)
        var radius = maxOf(senders.diffBy { it.x }, senders.diffBy { it.y }, senders.diffBy { it.z })
        var curr = setOf(Sender(0, 0, 0, radius))

        while (radius > 0) {
            radius /= 2
            val next = curr.flatMap { sender ->
                (-1L..1L).flatMap { xOff ->
                    (-1L..1L).flatMap { yOff ->
                        (-1L..1L).map { zOff ->
                            val neu = Sender(
                                sender.x + xOff * radius,
                                sender.y + yOff * radius,
                                sender.z + zOff * radius,
                                radius
                            )
                            neu to senders.count {
                                it.intersect(neu)
                            }
                        }
                    }
                }
            }
            val maxDistance = next.map { it.second }.max() ?: 0
            curr = next.mapNotNull { if (it.second == maxDistance) it.first else null }.toSet()
        }
        return curr.map { abs(it.x) + abs(it.y) + abs(it.z) }.min() ?: 0L
    }

    fun parse(input: List<String>): List<Sender> =
        input.map { val (x, y, z, r) = it.getNumbers(); Sender(x.toLong(), y.toLong(), z.toLong(), r.toLong()) }

}

fun main(args: Array<String>) {
    val input = File("./input/2018/Day23_input.txt").readLines()
    println("Part One = ${Day23.partOne(input)}")
    println("Part Two = ${Day23.partTwo(input)}")
}
