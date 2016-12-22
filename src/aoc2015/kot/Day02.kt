package aoc2015.kot

import java.io.File

object Day02 {
    fun solve(input: List<String>, f: (Int, Int, Int) -> Int): Int {
        return input.map { val (x1, x2, x3) = it.split("x").map(String::toInt).sortedBy { it }; f(x1, x2, x3) }.sum()
    }
}

fun main(args: Array<String>) {
    val input = File("./input/2015/Day02_input.txt").readLines().filter(String::isNotEmpty)
    System.out.println("Part One = ${Day02.solve(input) { x1, x2, x3 -> 2 * (x1 * x2 + x1 * x3 + x2 * x3) + x1 * x2 }}")
    System.out.println("Part Two = ${Day02.solve(input) { x1, x2, x3 -> 2 * (x1 + x2) + x1 * x2 * x3 }}")
}
