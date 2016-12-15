package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

class Day09 {
  private static long solve(List<String> input, boolean partTwo) {
    return input.stream().map(x -> solve(x, partTwo)).reduce(0L, Long::sum);
  }

  private static long solve(String s, boolean partTwo) {
    long cnt = 0;
    char[] s_arr = s.toCharArray();
    for (int i = 0; i < s_arr.length; i++) {
      if (s_arr[i] == '(') {
        int end = s.indexOf(')', i);
        int howMuch = Integer.valueOf(s.substring(i + 1, end).split("x")[0]);
        int times = Integer.valueOf(s.substring(i + 1, end).split("x")[1]);
        String repeat = s.substring(end + 1, end + 1 + howMuch);
        cnt += times * ((partTwo) ? solve(repeat, true) : repeat.length());
        i = end + howMuch;
      } else if (s_arr[i] != ' ')
        cnt++;
    }
    return cnt;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day09_input.txt"));
    System.out.println("Part One = " + solve(lines, false));
    System.out.println("Part Two = " + solve(lines, true));
  }
}
