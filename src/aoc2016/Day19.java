package aoc2016;

import java.util.*;
import java.util.stream.*;

class Day19 {
  private static int partOne(int size) {
    Deque<Integer> queue = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toCollection(ArrayDeque::new));
    while (queue.size() > 1) {
      queue.add(queue.poll());
      queue.poll();
    }
    return queue.poll();
  }

  private static int partTwo(int size) {
    Deque<Integer> left = IntStream.rangeClosed(1, size / 2).boxed().collect(Collectors.toCollection(ArrayDeque::new));
    Deque<Integer> right = IntStream.rangeClosed(size / 2 + 1, size).boxed().collect(Collectors.toCollection(ArrayDeque::new));

    while (left.size() + right.size() > 1) {
      right.poll();
      right.add(left.poll());
      if ((left.size() + right.size()) % 2 == 0)
        left.add(right.poll());
    }
    return right.poll();
  }

  public static void main(String[] args) {
    System.out.println("Part One = " + partOne(3018458));
    System.out.println("Part Two = " + partTwo(3018458));
  }
}
