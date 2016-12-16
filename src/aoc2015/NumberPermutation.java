package aoc2015;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implicit immutable collection of all permutations of a numbersequence with
 * an iterator over the permutations.
 * <p>
 * http://stackoverflow.com/a/13037291
 * <p>
 * implements Iterable&ltInteger[]&gt
 *
 * @see #NumberPermutation(int)
 */
class NumberPermutation implements Iterable<Integer[]> {

  private final Integer[] arr;

  /**
   * Creates an implicit Iterable collection of all permutations of
   * numbersequence from 0 to stop.
   *
   * @param end End of the number sequence (exclusive)
   * @see Iterable
   * @see #iterator
   */
  public NumberPermutation(int end) {
    this.arr = new Integer[end];
    for (int i = 0; i < end; i++) {
      arr[i] = i;
    }
  }

  /**
   * Constructs and sequentially returns the permutation values
   */
  @Override
  public Iterator<Integer[]> iterator() {

    return new Iterator<Integer[]>() {

      final Integer[] array = arr.clone();
      final int length = array.length;
      int[] index = (length == 0) ? null : new int[length];

      @Override
      public boolean hasNext() {
        return index != null;
      }

      @Override
      public Integer[] next() {
        if (index == null)
          throw new NoSuchElementException();

        for (int i = 1; i < length; ++i) {
          Integer swap = array[i];
          System.arraycopy(array, 0, array, 1, i);
          array[0] = swap;
          for (int j = 1; j < i; ++j) {
            index[j] = 0;
          }
          if (++index[i] <= i) {
            return array.clone();
          }
          index[i] = 0;
        }
        index = null;
        return array.clone();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}