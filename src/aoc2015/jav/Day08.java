package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

class Day08 {

  private static int partOne(List<String> list) {
    int count = 0;
    for (String s : list) {
      char[] chars = s.toCharArray();
      for (int i = 0; i < chars.length; i++) {
        if (chars[i] == '\\') {
          if (chars[i + 1] == 'x') {
            i += 2;
            count += 3;
          } else {
            i += 1;
            count++;
          }
        } else if (chars[i] == '\"') {
          count++;
        }
      }
    }
    return count;
  }

  private static int partTwo(List<String> list) {
    return list.stream().mapToInt(x -> cntSub("\\", x) + cntSub("\"", x) + 2).sum();
  }

  private static int cntSub(String subStr, String str) {
    return (str.length() - str.replace(subStr, "").length()) / subStr.length();
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day08_input.txt"));
    System.out.println("Part One = " + partOne(s));
    System.out.println("Part Two = " + partTwo(s));
  }
}
