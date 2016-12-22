package aoc2015.kot

fun isNice(s: String): Boolean {
    if (s.filter { "aeiou".contains(it) }.count() < 3)
        return false
    if (listOf("ab", "cd", "pq", "xy").any { s.contains(it) })
        return false
    return (0..(s.length - 2)).any { s[it] == s[it + 1] }
}

fun isNice2(s: String): Boolean {
    if ((0..(s.length - 2)).any { start: Int -> ((start + 2)..(s.length - 2)).any { start2: Int -> s[start] == s[start2] && s[start + 1] == s[start2 + 1] } }.not())
        return false
    return (0..(s.length - 3)).any { s[it] == s[it + 2] }
}

fun main(args: Array<String>) {
    val input = java.io.File("./input/2015/Day05_input.txt").readLines()
    System.out.println("Part One = ${input.count(::isNice)}")
    System.out.println("Part Two = ${input.count(::isNice2)}")
}