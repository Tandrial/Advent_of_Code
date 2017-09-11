package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;

class Day03 {
  private static int checkTriangle(Integer[][] input) {
    int cnt = 0;
    for (Integer[] x : input)
      for (int j = 0; j < x.length; j += 3)
        if (x[j] + x[j + 1] > x[j + 2] && x[j] + x[j + 2] > x[j + 1] && x[j + 1] + x[j + 2] > x[j])
          cnt++;
    return cnt;
  }

  private static Integer[][] transpose(Integer[][] input) {
    return IntStream.range(0, input[0].length).mapToObj(r -> Arrays.stream(input).mapToInt(integers -> integers[r]).boxed().toArray(Integer[]::new)).toArray(Integer[][]::new);
  }

  private static Integer[][] parse(List<String> lines) {
    return lines.stream().map(s -> Arrays.stream(s.trim().split("\\s+")).map(Integer::valueOf).toArray(Integer[]::new)).toArray(Integer[][]::new);
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day03_input.txt"));
    System.out.println("Part One = " + checkTriangle(parse(lines)));
    System.out.println("Part Two = " + checkTriangle(transpose(parse(lines))));
  }
}
