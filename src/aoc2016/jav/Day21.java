package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

class Day21 {
  private static String partOne(List<String> input, String current) {
    for (String move : input) {
      Matcher m1 = Pattern.compile("swap (\\w+) (\\w+) with \\w+ (\\w+)").matcher(move);
      Matcher m2 = Pattern.compile("rotate (\\w+) (\\d+) \\w+").matcher(move);
      Matcher m3 = Pattern.compile("rotate based on position of letter (\\w+)").matcher(move);
      Matcher m4 = Pattern.compile("reverse positions (\\d+) through (\\d+)").matcher(move);
      Matcher m5 = Pattern.compile("move position (\\d+) to position (\\d+)").matcher(move);
      if (m1.find()) {
        String swp1 = m1.group(2), swp2 = m1.group(3);
        if (m1.group(1).equals("position")) {
          swp1 = String.valueOf(current.charAt(Integer.valueOf(m1.group(2))));
          swp2 = String.valueOf(current.charAt(Integer.valueOf(m1.group(3))));
        }
        current = current.replaceAll(swp1, "#").replaceAll(swp2, swp1).replaceAll("#", swp2);
      } else if (m2.find()) {
        int amount = Integer.valueOf(m2.group(2));
        if (m2.group(1).equals("left"))
          current = current.substring(amount) + current.substring(0, amount);
        else
          current = current.substring(current.length() - amount) + current.substring(0, current.length() - amount);
      } else if (m3.find()) {
        int amount = current.indexOf(m3.group(1));
        if (amount >= 4) amount += 2;
        else amount++;
        amount %= current.length();
        current = current.substring(current.length() - amount) + current.substring(0, current.length() - amount);
      } else if (m4.find()) {
        StringBuilder sb = new StringBuilder();
        sb.append(current.substring(Integer.valueOf(m4.group(1)), Integer.valueOf(m4.group(2)) + 1));
        sb.reverse();
        if (!m4.group(1).equals("0"))
          sb.insert(0, current.substring(0, Integer.valueOf(m4.group(1))));
        if (Integer.valueOf(m4.group(2)) != current.length() - 1)
          sb.append(current.substring(Integer.valueOf(m4.group(2)) + 1));
        current = sb.toString();
      } else if (m5.find()) {
        Character c = current.charAt(Integer.valueOf(m5.group(1)));
        StringBuilder sb = new StringBuilder(current);
        sb.deleteCharAt(Integer.valueOf(m5.group(1)));
        sb.insert(Integer.parseInt(m5.group(2)), c);
        current = sb.toString();
      }
    }
    return current;
  }

  private static String partTwo(List<String> input, String end) {
    List<String> inputs = new ArrayList<>();
    permutation("", "abcdefgh", inputs);
    for (String s : inputs)
      if (partOne(input, s).equals(end))
        return s;
    return "";
  }

  private static void permutation(String prefix, String str, List<String> permu) {
    int n = str.length();
    if (n == 0) permu.add(prefix);
    else
      for (int i = 0; i < n; i++)
        permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), permu);
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2016/Day21_input.txt"));
    System.out.println("Part One = " + partOne(s, "abcdefgh"));
    System.out.println("Part One = " + partTwo(s, "fbgdceah"));
  }
}
