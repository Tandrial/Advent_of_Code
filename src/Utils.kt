import java.io.File

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
fun File.to2DIntArr(delim: String = "\\s+"): List<List<Int>> {
  return this.readLines().map { it.toIntList(delim) }
}

/**
 * Splits the string into a [List] of [Int].
 *
 * @param delim The delimiter by which the [Int]s in the string are sperated
 * @return [List] of [Int]s
 */
fun String.toIntList(delim: String = "\\s+"): List<Int> = this.split(delim).map { it.toInt() }