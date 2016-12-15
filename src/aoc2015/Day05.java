package aoc2015;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.regex.Pattern;

class Day05 {

  private static int partOne(List<String> list) {
    int count = 0;
    for (String s : list) {
      if (!Pattern.matches("(.*[aeiou]){3}.*", s))
        continue;
      if (!Pattern.matches(".*(.)\\1.*", s))
        continue;
      if (Pattern.matches(".*(ab|cd|pq|xy).*", s))
        continue;
      count++;
    }
    return count;
  }

  private static int partTwo(List<String> list) {
    int count = 0;
    for (String s : list) {
      if (!Pattern.matches(".*(..).*\\1.*", s))
        continue;
      if (!Pattern.matches(".*(.)\\w\\1.*", s))
        continue;
      count++;
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day05_input.txt"));
    System.out.println("Part One = " + partOne(s));
    System.out.println("Part Two = " + partTwo(s));
  }
}
