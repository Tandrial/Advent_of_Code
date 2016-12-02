package aoc2016;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day01 {
  private int[][]    moves     = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
  private Point[]    sol       = { new Point(), null };

  private Set<Point> visited   = new HashSet<>();
  private int        direction = 0;

  public void solve(String string) {
    for (String s : string.split(", ")) {
      direction = Math.floorMod(direction + (s.charAt(0) == 'L' ? -1 : 1), 4);

      int amount = Integer.parseInt(s.substring(1));
      while (amount-- > 0) {
        sol[0].translate(moves[direction][0], moves[direction][1]);

        if (sol[1] == null && visited.contains(sol[0]))
          sol[1] = new Point(sol[0]);
        else
          visited.add(sol[0]);
      }
    }
    System.out.println(String.format("Part one = %d\nPart two = %d", Math.abs(sol[0].x) + Math.abs(sol[0].y),
        Math.abs(sol[1].x) + Math.abs(sol[1].y)));
  }

  public static void main(String[] args) throws IOException {
    String s = new String(Files.readAllBytes(Paths.get("./input/2016/Day01_input.txt")));
    new Day01().solve(s);
  }
}
