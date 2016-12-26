package aoc2016.kot

import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern

object Day22 {
    data class Node(val x: Int, val y: Int, val size: Int, val used: Int, val avail: Int)

    fun parse(input: List<String>): List<Node> {
        return input.map {
            val m = Pattern.compile("(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)").matcher(it)
            if (m.find())
                Node(m.group(1).toInt(), m.group(2).toInt(), m.group(3).toInt(), m.group(4).toInt(), m.group(5).toInt())
            else null
        }.filterNotNull()
    }

    fun partOne(s: List<Node>): Int {
        return s.filter { n -> n.used > 0 }.map { n -> s.filter { other -> n != other && other.avail >= n.used }.count() }.sum()
    }

    fun partTwo(s: List<Node>): Int {
        val xSize: Int = s.maxBy { it.x }!!.x
        val wallEdge: Node = s.find { it.x == s.find { it.size > 250 }!!.x - 1 }!!
        val emptyDisk: Node = s.find { it.used == 0 }!!
        val nodes = s.groupBy { it.x }

        for ((key, value) in nodes) {
            println(value.map {
                when {
                    it.x == 0 && it.y == 0 -> 'S'
                    it.x == xSize && it.y == 0 -> 'G'
                    it.size > 100 -> '#'
                    else -> '.'
                }
            })
        }
        var result = Math.abs(emptyDisk.x - wallEdge.x) + Math.abs(emptyDisk.y - wallEdge.y)
        result += Math.abs(wallEdge.x - xSize) + wallEdge.y + 5 * (xSize - 1)
        return result
    }
}

fun main(args: Array<String>) {
    val s = Files.readAllLines(Paths.get("./input/2016/Day22_input.txt"))
    println("Part One = ${Day22.partOne(Day22.parse(s))}")
    println("Part One = ${Day22.partTwo(Day22.parse(s))}")
}
