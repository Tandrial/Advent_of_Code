package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Day09 {
  public static long partOne(List<String> input) {
    return input.stream().map(x -> solve(x, false)).reduce(0L, Long::sum);
  }

  public static long partTwo(List<String> input) {
    return input.stream().map(x -> solve(x, true)).reduce(0L, Long::sum);
  }

  public static long solve(String s, boolean partTwo) {
    long cnt = 0;
    char[] s_arr = s.toCharArray();
    for (int i = 0; i < s_arr.length; i++) {
      if (s_arr[i] == '(') {
        int end = s.indexOf(')', i);
        int howMuch = Integer.valueOf(s.substring(i + 1, end).split("x")[0]);
        int times = Integer.valueOf(s.substring(i + 1, end).split("x")[1]);
        String repeat = s.substring(end + 1, end + 1 + howMuch);
        cnt += times * ((partTwo) ? solve(repeat, partTwo) : repeat.length());
        i = end + howMuch;
      } else if (s_arr[i] == ' ')
        continue;
      else
        cnt++;
    }
    return cnt;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day09_input.txt"));
    System.out.println("Part One = " + partOne(lines));
    System.out.println("Part Two = " + partTwo(lines));
  }
}
