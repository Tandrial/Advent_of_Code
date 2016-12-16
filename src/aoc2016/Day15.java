package aoc2016;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

class Day15 {
  private static long t;

  private static long solve(List<Point> discs) {
    Point biggest = discs.get(0);
    t = biggest.x - biggest.y;
    while (discs.stream().anyMatch(d -> ((t + d.y) % d.x) != 0))
      t += biggest.x;
    return t;
  }

  private static List<Point> parse(List<String> input) {
    List<Point> discs = new ArrayList<>();
    for (String s : input) {
      Matcher m = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=0, it is at position (\\d+)\\.")
        .matcher(s);
      if (m.find())
        discs.add(new Point(Integer.valueOf(m.group(2)), Integer.valueOf(m.group(1)) + Integer.valueOf(m.group(3))));
    }
    discs.sort((o1, o2) -> Integer.compare(o2.x, o1.x));
    return discs;
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2016/Day15_input.txt"));
    System.out.println("Part One = " + solve(parse(s)));
    s.add("Disc #7 has 11 positions; at time=0, it is at position 0.");
    System.out.println("Part One = " + solve(parse(s)));
  }
}
