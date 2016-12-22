package aoc2015.kot

import java.io.File

object Day01 {
    fun partOne(input: String): Int {
        return input.fold(0) { total, next -> total + if (next == '(') 1 else -1 }
    }

    fun partTwo(input: String): Int {
        var pos = 0
        for ((idx, c) in input.withIndex()) {
            if (pos == -1) return idx
            when (c) {
                '(' -> pos++
                ')' -> pos--
            }
        }
        return -1
    }

}

fun main(args: Array<String>) {
    val input = File("./input/2015/Day01_input.txt").readText()
    System.out.println("Part One = ${Day01.partOne(input)}")
    System.out.println("Part Two = ${Day01.partTwo(input)}")
}
