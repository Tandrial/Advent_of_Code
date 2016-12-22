package aoc2015.jav;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class Day06 {

  private static int partOne(List<String> list) {
    int[][] grid = new int[1000][1000];
    for (String s : list) {
      Point[] p = parsePoints(s);
      for (int x = p[0].x; x <= p[1].x; x++) {
        for (int y = p[0].y; y <= p[1].y; y++) {
          if (s.contains("toggle")) {
            grid[x][y] = grid[x][y] == 0 ? 1 : 0;
          } else if (s.contains("on")) {
            grid[x][y] = 1;
          } else {
            grid[x][y] = 0;
          }
        }
      }
    }
    return Arrays.stream(grid).flatMapToInt(Arrays::stream).sum();
  }

  private static long partTwo(List<String> list) {
    int[][] grid = new int[1000][1000];
    for (String s : list) {
      Point[] p = parsePoints(s);
      for (int x = p[0].x; x <= p[1].x; x++) {
        for (int y = p[0].y; y <= p[1].y; y++) {
          if (s.contains("toggle")) {
            grid[x][y] += 2;
          } else if (s.contains("on")) {
            grid[x][y] += 1;
          } else {
            grid[x][y] = Math.max(0, grid[x][y] - 1);
          }
        }
      }
    }
    return Arrays.stream(grid).flatMapToInt(Arrays::stream).sum();
  }

  private static Point[] parsePoints(String s) {
    String[] line = s.split(" ");
    String[] p1_s = line[line.length - 3].split(",");
    String[] p2_s = line[line.length - 1].split(",");
    Point p1 = new Point(Integer.valueOf(p1_s[0]), Integer.valueOf(p1_s[1]));
    Point p2 = new Point(Integer.valueOf(p2_s[0]), Integer.valueOf(p2_s[1]));
    return new Point[]{p1, p2};
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day06_input.txt"));
    System.out.println("Part One = " + partOne(s));
    System.out.println("Part Two = " + partTwo(s));
  }
}
