package aoc2017.kot

import toIntList
import java.io.File

object Day06 {

  fun f(mem: List<Int>): List<Int> {
    val next = mem.toMutableList()
    var (idx, max) = mem.withIndex().maxBy { it.value }!!
    next[idx] = 0
    while (max-- > 0) {
      next[++idx % mem.size]++
    }
    return next.toList()
  }


  fun solve(x0: List<Int>): Pair<Int, Int> {
    var tortoise = f(x0)
    var hare = f(f(x0))
    while (tortoise != hare) {
      tortoise = f(tortoise)
      hare = f(f(hare))
    }

    var μ = 0
    tortoise = x0
    while (tortoise != hare) {
      tortoise = f(tortoise)
      hare = f(hare)
      μ += 1
    }

    var λ = 1
    hare = f(tortoise)
    while (tortoise != hare) {
      hare = f(hare)
      λ += 1
    }

    return Pair(μ, λ)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2017/Day06_input.txt").readText().toIntList()
  val (mu, lambda) = Day06.solve(input)
  println("Part One = ${mu + lambda}")
  println("Part Two = $lambda")
}
