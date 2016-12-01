package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day01 {

  private int[][]                    moves     = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
  private int[][]                    sol       = { { 0, 0 }, { 0, 0 } };

  private boolean                    found     = false;
  private Map<Integer, Set<Integer>> visited   = new HashMap<>();
  private int                        direction = 0;

  public Day01(String string) {
    for (String s : string.split(", ")) {
      direction = Math.floorMod(direction + (s.charAt(0) == 'L' ? -1 : 1), 4);

      int amount = Integer.parseInt(s.substring(1));

      while (amount-- > 0) {
        for (int pos = 0; pos < moves[0].length; pos++)
          sol[0][pos] += moves[direction][pos];

        if (!found) {
          if (!visited.containsKey(sol[0][0]))
            visited.put(sol[0][0], new HashSet<>());
          if (visited.get(sol[0][0]).contains(sol[0][1])) {
            found = true;
            sol[1] = sol[0].clone();
          } else
            visited.get(sol[0][0]).add(sol[0][1]);
        }
      }
    }
  }

  private int partOne() {
    return Math.abs(sol[0][0]) + Math.abs(sol[0][1]);
  }

  private int partTwo() {
    return Math.abs(sol[1][0]) + Math.abs(sol[1][1]);
  }

  public static void main(String[] args) throws IOException {
    String s = new String(Files.readAllBytes(Paths.get("./input/2016/Day01_input.txt")));

    Day01 day1 = new Day01(s);
    System.out.println("Part one = " + day1.partOne());
    System.out.println("Part two = " + day1.partTwo());
  }
}
