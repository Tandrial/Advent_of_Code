/*
 * Copyright (c) 2017 Michael Krane
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
*/

package itertools

import kotlin.coroutines.experimental.buildSequence

/**
 * Splits the [Iterable] into chunks of [size]. The last element might be shorter if the string can't evenly divided
 *
 * @param size The size of the chunks
 * @return [Sequence] of [size] big chunks
 */
fun <T : Any> Iterable<T>.chunksOfSize(size: Int): Sequence<List<T>> = buildSequence {
  val iterator = iterator()
  while (iterator.hasNext()) {
    val window = mutableListOf<T>()
    (1..size).forEach { if (iterator.hasNext()) window += (iterator.next()) }
    yield(window)
  }
}

/**
 * Creates a string from a [Char] by repeating it n times
 *
 * @param n How often the char is repeated
 * @return A string which is the char repated n times
 */
operator fun Char.times(n: Int): String {
  val sb = StringBuilder()
  (1..n).forEach { sb.append(this) }
  return sb.toString()
}

/**
 * Groups elements in fixed size blocks by passing a *sliding window* over them, as opposed to partitioning them, as is done in [[chunksOfSize()]].
 * The last and the only element will be truncated if there are fewer characters than *size*
 *
 * @param size the number of characters per group
 */
fun String.sliding(size: Int): Sequence<String> = buildSequence {
  val iterator = iterator()
  val window = StringBuilder()
  (1..size).forEach { if (iterator.hasNext()) window.append(iterator.next()) }
  yield(window.toString())
  while (iterator.hasNext()) {
    window.deleteCharAt(0).append(iterator.next())
    yield(window.toString())
  }
}

/**
 * Cartesian product for CharRanges.
 * The input is the range itself repeated n times
 *
 * @param n How often the Range is repeated
 * @return The cartesian prodcut of the range
 */
fun CharRange.cartProd(n: Int): Sequence<List<Char>> {
  val ranges = repeat(n).toList().toTypedArray()
  return cartProd(*(ranges))
}

/**
 * Cartesian product for CharRanges.
 * The input is the range itself repeated n times
 *
 * @param n How often the Range is repeated
 * @return The cartesian prodcut of the range
 */
fun IntRange.cartProd(n: Int): Sequence<List<Int>> {
  val ranges = repeat(n).toList().toTypedArray()
  return cartProd(*(ranges))
}

/**
 * Cartesian product for CharRanges.
 * The input is the range itself repeated n times
 *
 * @param n How often the Range is repeated
 * @return The cartesian prodcut of the range
 */
fun LongRange.cartProd(n: Int): Sequence<List<Long>> {
  val ranges = repeat(n).toList().toTypedArray()
  return cartProd(*(ranges))
}

/**
 * Creates a [Sequence] which contains the cartesian product of the two Iterable
 *
 * @param other The second iterable
 *
 * @return [Sequence] which contains the cartesian product as Pairs
 */
operator fun <T : Any> Iterable<T>.times(other: Iterable<T>): Sequence<Pair<T, T>> = buildSequence {
  for (e1 in this@times) for (e2 in other) yield(Pair(e1, e2))
}

/**
 * Cartesian product of input [Iterable]. (https://en.wikipedia.org/wiki/Cartesian_product)
 * Roughly equivalent to nested for-loops in a generator expression.
 * For example, product(A, B) returns the same as ((x,y) for x in A for y in B).
 * The nested loops cycle like an odometer with the rightmost element advancing on every iteration.
 * This pattern creates a lexicographic ordering so that if the input’s iterables are sorted,
 * the product tuples are emitted in sorted order.
 *
 * @param items The input for which the cartesian product is calucalted
 *
 * @return A [Sequence] of [List] which contains the cartesian product
 *
 */
fun <T : Any> cartProd(vararg items: Iterable<T>): Sequence<List<T>> = buildSequence {
  val itemsIter = items.map { it.iterator() }.filter { it.hasNext() }.toMutableList()
  val currElement: MutableList<T> = itemsIter.map { it.next() }.toMutableList()
  loop@ while (true) {
    yield(currElement.toList())
    for (pos in itemsIter.count() - 1 downTo 0) {
      if (!itemsIter[pos].hasNext()) {
        if (pos == 0) break@loop
        itemsIter[pos] = items[pos].iterator()
        currElement[pos] = itemsIter[pos].next()
      } else {
        currElement[pos] = itemsIter[pos].next()
        break
      }
    }
  }
}

/**
 * Make a [Sequence] that returns object over and over again.
 * Runs indefinitely unless the [times] argument is specified.
 *
 * @param times How often the object is repeated. null means its repeated indefinitely
 */
fun <T : Any> T.repeat(times: Int? = null): Sequence<T> = buildSequence {
  var count = 0
  while (times == null || count++ < times) yield(this@repeat)
}

/**
 * Splits this iterable into pairs. The last element might be have the the second element set to null, if the length in uneven
 *
 * @return [Sequence] of pairs
 */
fun <T : Any> Iterable<T>.intoPairs(): Sequence<Pair<T, T?>> = buildSequence {
  val iterator = iterator()
  while (iterator.hasNext()) {
    val fst = iterator.next()
    val snd = if (iterator.hasNext()) iterator.next() else null
    yield(Pair(fst, snd))
  }
}

/**
 * Make an iterator that returns elements from the first iterable until it is exhausted,
 * then proceeds to the next iterable, until all of the iterables are exhausted.
 * Used for treating consecutive sequences as a single sequence.
 *
 */
fun <T : Any> Iterable<T>.chain(vararg other: Iterable<T>): Sequence<T> = buildSequence {
  for (elem in this@chain) yield(elem)
  other.flatMap { it }.forEach { yield(it) }
}

// Functions for finite Collections without get(index)
fun String.combinations(r: Int, replace: Boolean = false): Sequence<String> = asIterable().combinations(r, replace).map { it.joinToString(separator = "") }

fun <T : Any> Array<T>.combinations(r: Int, replace: Boolean = false): Sequence<List<T>> = asIterable().combinations(r, replace)

/**
 * Return r length [List]s of T from this List which are emitted in lexicographic sort order.
 * So, if the input iterable is sorted, the combination tuples will be produced in sorted order.
 * Elements are treated as unique based on their position, not on their value.
 * So if the input elements are unique, there will be no repeat values in each combination.
 *
 * @param r How many elements to pick
 * @param replace elements are replaced after being choosen
 *
 * @return [Sequence] of all possible combintations of length r
 */
fun <T : Any> Iterable<T>.combinations(r: Int, replace: Boolean = false): Sequence<List<T>> {
  val elements = toMutableList()
  val n = count()
  if (r > n) return sequenceOf()
  return buildSequence {
    var indices = if (replace) 0.repeat(r).toMutableList() else (0 until r).toMutableList()
    while (true) {
      yield(indices.map { elements[it] })
      var i = r - 1
      loop@ while (i >= 0) {
        when (replace) {
          true -> if (indices[i] != n - 1) break@loop
          false -> if (indices[i] != i + n - r) break@loop
        }
        i--
      }
      if (i < 0) break
      when (replace) {
        true -> indices = (indices.take(i) + (indices[i] + 1).repeat(r - i)).toMutableList()
        false -> {
          indices[i] += 1
          (i + 1 until r).forEach { indices[it] = indices[it - 1] + 1 }
        }
      }
    }
  }
}

/**
 * Make an [Sequence] that returns evenly spaced values starting with n
 *
 * @param start The value at which the sequence starts
 * @param step The step size
 */
fun count(start: Int = 0, step: Int = 1): Sequence<Int> = generateSequence(start) { it + step }

/**
 * Make an [Sequence] that returns evenly spaced values starting with n
 *
 * @param start The value at which the sequence starts
 * @param step The step size
 */
fun count(start: Long = 0, step: Long = 1): Sequence<Long> = generateSequence(start) { it + step }

/**
 * Make an [Sequence] that returns evenly spaced values starting with n
 *
 * @param start The value at which the sequence starts
 * @param step The step size
 */
fun count(start: Float = 0f, step: Float = 1f): Sequence<Float> = generateSequence(start) { it + step }

/**
 * Make an [Sequence] that returns evenly spaced values starting with n
 *
 * @param start The value at which the sequence starts
 * @param step The step size
 */
fun count(start: Double = 0.0, step: Double = 1.0): Sequence<Double> = generateSequence(start) { it + step }

/**
 * Make an [Sequence] returning elements from the iterable and saving a copy of each.
 * When the iterable is exhausted, return elements from the saved copy. Repeats indefinitely.
 *
 */
fun <T : Any> Iterable<T>.cycle(): Sequence<T> = buildSequence {
  val saved = mutableListOf<T>()
  for (elem in this@cycle) {
    saved.add(elem)
    yield(elem)
  }
  while (true) {
    for (elem in saved) yield(elem)
  }
}

// Functions for finite Collections without get(index)
fun String.permutations(k: Int = this.length): Sequence<String> = asIterable().permutations(k).map { it.joinToString(separator = "") }

fun <T : Comparable<T>> Array<T>.permutations(k: Int = this.size): Sequence<List<T>> = asIterable().permutations(k)

/**
 * Return successive permutations of elements in the [Iterable].
 * all possible
 * full-length permutations are generated. Permutations are emitted in lexicographic sort order.
 * So, if the input iterable is sorted, the permutation tuples will be produced in sorted order.
 * Elements are treated as unique based on their position, not on their value.
 * So if the input elements are unique, there will be no repeat values in each permutation.
 *
 *
 * @return [Sequence] of all possible permutations
 */
fun <T : Any> Iterable<T>.permutations(): Sequence<List<T>> {
  val elements = toMutableList()
  val n = elements.count()
  return buildSequence {
    // https://en.wikipedia.org/wiki/Heap%27s_algorithm
    val indicies = 0.repeat(n).toMutableList()
    yield(elements)
    var i = 0
    while (i < n) {
      if (indicies[i] < i) {
        if (i.rem(2) == 0) elements.swapByIndex(0, i)
        else elements.swapByIndex(indicies[i], i)
        yield(elements)
        indicies[i] += 1
        i = 0
      } else {
        indicies[i] = 0
        i += 1
      }
    }
  }
}

/**
 * Return successive r length permutations of elements in the [Iterable].
 * If r is not specified, then r defaults to the length of the iterable and all possible
 * full-length permutations are generated. Permutations are emitted in lexicographic sort order.
 * So, if the input iterable is sorted, the permutation tuples will be produced in sorted order.
 * Elements are treated as unique based on their position, not on their value.
 * So if the input elements are unique, there will be no repeat values in each permutation.
 *
 *
 * @param k The length of the permutation
 *
 * @return [Sequence] of all k-length possible permutations
 */
fun <T : Comparable<T>> Iterable<T>.permutations(k: Int): Sequence<List<T>> {
  val elements = toMutableList()
  val n = elements.count()
  if (k == n) return elements.permutations()
  return buildSequence {
    // https://alistairisrael.wordpress.com/2009/09/22/simple-efficient-pnk-algorithm/
    while (true) {
      yield(elements.take(k))
      var edge = k - 1
      // find j in (k…n-1) where a_j > a_edge
      var j = (k until n).firstOrNull { elements[it] > elements[edge] } ?: n
      if (j < n) {
        elements.swapByIndex(edge, j)
      } else {
        elements.reverseRightOf(edge)
        // find rightmost ascent to left of edge
        edge = (edge - 1 downTo 0).firstOrNull { elements[it] < elements[it + 1] } ?: break
        // find j in (n-1 … i+1) where a_j > a_i
        j = (j - 1 downTo edge).firstOrNull { elements[edge] < elements[it] } ?: edge
        elements.swapByIndex(edge, j)
        elements.reverseRightOf(edge)
      }
    }
  }
}

fun <T : Any> MutableList<T>.swapByIndex(from: Int, to: Int) {
  this[from] = this[to].also { this[to] = this[from] }
}

fun <T : Any> MutableList<T>.reverseRightOf(start: Int) {
  val end = start + Math.floorDiv(size - start, 2)
  ((start + 1)..end).forEach { swapByIndex(it, size - it + start) }
}


fun main(args: Array<String>) {
  val s = "ABCDEFGHIJKLMN"
//  print("\"$s\".chunksOfSize(3): ")
//  for (chunk in s.chunksOfSize(3)) print("$chunk ")
//  println()

  println("'c' * 3 = ${'c' * 3}")

  print("\"$s\".sliding(3): ")
  for (window in s.sliding(3)) print("$window ")
  println()

  print("(1..2).cartProd(2): ")
  for (prod in (1..2).cartProd(2)) print("$prod ")
  print("\n(a..b).cartProd(3): ")
  for (prod in ('a'..'b').cartProd(3)) print("$prod ")
  print("\n(1..2) * (1..2): ")
  for (prod in (1..2) * (1..2)) print("$prod ")
  print("\ncartProd((1..2), (3..4), (5..6)): ")
  for (prod in cartProd((1..2), (3..4), (5..6))) print("$prod ")
  println()

  print("1.repeat(10): ")
  for (i in 1.repeat(10)) print("$i ")
  println()

  print("(1..13).intoPairs(): ")
  for (pair in (1..13).intoPairs()) print("$pair ")
  println()

  print("(1..2).chain((3..4),(5..6),(7..8)): ")
  for (elem in (1..2).chain((3..4), (5..6), (7..8))) print("$elem ")
  println()

  print("\"abcd\".combinations(2): ")
  for (elem in "abcd".combinations(2)) print("$elem ")
  print("\n\"abcd\".combinations(2, true): ")
  for (elem in "abcd".combinations(2, true)) print("$elem ")
  print("\nIterable<T>(1,2,3,4).combinations(2): ")
  for (elem in (1..4).combinations(2)) print("$elem ")
  print("\nIterable<T>(1,2,3,4).combinations(2, true): ")
  for (elem in (1..4).combinations(2, true)) print("$elem ")
  println()

  print("count(0, 2).take(10): ")
  for (i in count(0, 2).take(10)) print("$i ")
  println()

  print("(1..3).cycle().take(6): ")
  for (i in (1..3).cycle().take(6)) print("$i ")
  println()

  print("\"abcd\".permutations(): ")
  for (elem in "abcd".permutations()) print("$elem ")
  print("\n\"abcd\".permutations(2): ")
  for (elem in "abcd".permutations(2)) print("$elem ")
  print("\nIterable<T>(1,2,3).permutations(): ")
  for (elem in (1..3).permutations()) print("$elem ")
  print("\nIterable<T: Comparable<T>>(1,2,3,4).permutations(2): ")
  for (elem in (1..4).permutations(2)) print("$elem ")
  println()
}
