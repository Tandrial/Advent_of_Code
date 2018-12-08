package aoc2018.kot

import getNumbers
import removeTake
import java.io.File

object Day08 {
  data class Node(val children: MutableList<Node> = mutableListOf(), val metaData: MutableList<Int> = mutableListOf()) {
    fun sumMetaDataPartOne(): Int = children.sumBy { it.sumMetaDataPartOne() } + metaData.sum()

    fun sumMetaDataPartTwo(): Int {
      if (children.isEmpty()) {
        return metaData.sum()
      }
      val childAmount = children.map { it.sumMetaDataPartTwo() }
      return metaData.map { if (it > childAmount.size) 0 else childAmount[it - 1] }.sum()
    }
  }

  fun partOne(input: String): Int = parse(input).sumMetaDataPartOne()
  fun partTwo(input: String): Int = parse(input).sumMetaDataPartTwo()

  private fun parse(input: String) = parse(input.getNumbers().toMutableList())

  private fun parse(input: MutableList<Int>): Node {
    if (input.isEmpty()) return Node()
    val (childCount, metaCount) = input.removeTake(2)
    return Node().apply {
      repeat(childCount) {
        children.add(parse(input))
      }
      metaData.addAll(input.removeTake(metaCount))
    }
  }

}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day08_input.txt").readText()

  println("Part One = ${Day08.partOne(input)}")
  println("Part Two = ${Day08.partTwo(input)}")
}
