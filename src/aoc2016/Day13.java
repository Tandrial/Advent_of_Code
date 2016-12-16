package aoc2016;

import java.awt.*;
import java.util.*;
import java.util.stream.Stream;

class Day13 {

  static class Cell extends Point {
    int steps = 0;

    public Cell(int x, int y, int steps) {
      super(x, y);
      this.steps = steps;
    }

    boolean isValid(int offset) {
      int value = x * x + 3 * x + 2 * x * y + y + y * y + offset;
      return x >= 0 && y >= 0 && (Integer.bitCount(value) % 2) == 0;
    }
  }

  private static int solve(int endX, int endY, int offset, boolean partTwo) {
    Set<Cell> visited = new HashSet<>();
    Queue<Cell> queue = new ArrayDeque<>();
    queue.add(new Cell(1, 1, 0));

    while (!queue.isEmpty()) {
      Cell curr = queue.poll();
      visited.add(curr);
      if (!partTwo && curr.x == endX && curr.y == endY)
        return curr.steps;
      Stream.of(new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0))
        .map(p -> new Cell(curr.x + p.x, curr.y + p.y, curr.steps + 1))
        .filter(c -> c.isValid(offset) && !visited.contains(c) && !queue.contains(c))
        .filter(c -> !partTwo || c.steps <= 50).forEach(queue::add);
    }
    return visited.size();
  }

  public static void main(String[] args) {
    System.out.println("Part One = " + solve(31, 39, 1358, false));
    System.out.println("Part Two = " + solve(-1, -1, 1358, true));
  }
}
