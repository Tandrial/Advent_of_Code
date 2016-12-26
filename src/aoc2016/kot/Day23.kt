package aoc2016.kot

import java.io.File

fun main(args: Array<String>) {
    val s = File("./input/2016/Day23_input.txt").readLines()
    println("Part One = ${VM(s).run(regA = 7)}")
    println("Part Two = ${VM(s).run(regA = 12)}")
}
