import java.io.File
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.system.measureTimeMillis

/**
 * Returns the alphabetically sorted String
 *
 * @return The string sorted alphabetically
 */
fun String.sort(): String = this.toCharArray().sorted().joinToString()

/**
 * Parses the File contents into a 2D Int
 *
 * @param delim The delimiter by which the [Int]s are sperated in each line
 * @return The file contents parsed to a 2D Int structure
 */
fun File.to2DIntArr(delim: Pattern = "\\s+".toPattern()): List<List<Int>> {
  return this.readLines().map { it.toIntList(delim) }
}

/**
 * Splits the string into a [List] of [Int].
 *
 * @param delim The delimiter by which the [Int]s in the string are sperated
 * @return [List] of [Int]s
 */
fun String.toIntList(delim: Pattern = "\\s+".toPattern()): List<Int> = this.split(delim).map { it.toInt() }

/**
 * Splits the [String] into a [List] or words, where a word is matched bs \w+
 *
 * @return [List] of matched words
 */
fun String.getWords(): List<String> = Regex("\\w+").findAll(this).toList().map { it.value }

/**
 * Splits the [String] into a [List] or numbers, where a word is matched bs -?\d+
 *
 * @return [List] of matched numbers
 */
fun String.getNumbers(): List<Int> = Regex("-?\\d+").findAll(this).toList().map { it.value.toInt() }

/**
 * Converts the [Int]s in the [Iterable] into their hex-Representation and joints them together
 *
 * @return a [String] of hexValuess
 */
fun Iterable<Int>.toHexString(): String = joinToString(separator = "") { it.toString(16).padStart(2, '0') }

/**
 * Combination of [take] and [remove]
 *
 * @param take How many elements to remove from the [MutableList]
 *
 * @return a [List] of the taken elements
 */
fun <T : Any> MutableList<T>.removeTake(take: Int): List<T> {
  val removed = this.take(take)
  repeat(take) { this.removeAt(0) }
  return removed
}

fun timeIt(block: () -> Unit) {
  println("${measureTimeMillis(block)}ms")
}

/**
 * Calculates the difference between the [max] and [min] Value of the elemnts of the List, transfomred by [block]
 *
 * @param block What to do calculate the difference of
 *
 * @return difference
 */
fun <T : Any> List<T>.diffBy(block: (T) -> Long): Long {
  val values = map(block)
  return abs((values.max() ?: 0L) - (values.min() ?: 0L))
}
