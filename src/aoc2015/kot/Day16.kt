package aoc2015.kot

import itertools.intoPairs
import java.nio.file.Files
import java.nio.file.Paths

object Day16 {
  data class Sue(val num: Int, val stats: HashMap<String, Int>)

  fun parseAunts(lines: List<String>): List<Sue> {
    return lines.map {
      val attrs = hashMapOf<String, Int>()
      var num = 0
      for ((k, v) in it.split(" ").intoPairs()) {
        val key = k.filter { it != ':' && it != ',' }
        val value = Integer.valueOf(v.filter { it != ':' && it != ',' })
        if (key == "Sue") num = value
        else attrs.put(key, value)
      }
      Sue(num, attrs)
    }
  }

  fun solve(aunts: List<Sue>, foundAttrs: HashMap<String, Int>, filter: (String, Int, Int) -> Boolean): Int {
    return aunts.first({ it.stats.none { (k, v) -> !filter(k, foundAttrs[k]!!, v) } }).num
  }
}

fun main(args: Array<String>) {
  val s = Files.readAllLines(Paths.get("./input/2015/Day16_input.txt"))
  val sues = Day16.parseAunts(s)
  val foundAttrs = hashMapOf(Pair("children", 3), Pair("cats", 7), Pair("samoyeds", 2), Pair("pomeranians", 3), Pair("akitas", 0), Pair("vizslas", 0), Pair("goldfish", 5), Pair("trees", 3), Pair("cars", 2), Pair("perfumes", 1))
  println("Part One = ${Day16.solve(sues, foundAttrs, { _: String, fv: Int, v: Int -> fv == v })}")

  fun filter(key: String, fv: Int, v: Int): Boolean {
    if (key == "cats" || key == "trees") return fv <= v
    if (key == "pomeranians" || key == "goldfish") return fv > v
    return fv == v
  }
  println("Part Two = ${Day16.solve(sues, foundAttrs, ::filter)}")
}