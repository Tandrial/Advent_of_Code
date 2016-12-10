package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.*;

public class Day08 {

  public static void solve(List<String> input) {
    int[][] lcd = new int[6][50];
    for (String s : input) {
      if (s.startsWith("rect")) {
        int a = Integer.valueOf(s.split(" ")[1].split("x")[0]);
        int b = Integer.valueOf(s.split(" ")[1].split("x")[1]);
        lcd = rect(a, b, lcd);
      } else if (s.startsWith("rotate")) {
        int x = Integer.valueOf(s.split("=")[1].split(" ")[0]);
        int by = Integer.valueOf(s.split("by ")[1]);
        lcd = s.contains("row") ? rotRow(x, by, lcd) : transpose().apply(rotRow(x, by, transpose().apply(lcd)));
      }
    }
    System.out.println(String.format("Part One = %d\nPart Two = ", Arrays.stream(lcd).map(xs -> Arrays.stream(xs).sum()).reduce(0, Integer::sum)));
    Arrays.stream(lcd).map(xs -> Arrays.stream(xs).mapToObj(x -> String.valueOf(x == 1 ? '#' : ' ')).collect(Collectors.joining())).forEachOrdered(System.out::println);
  }

  public static UnaryOperator<int[][]> transpose() {
    return m -> { return IntStream.range(0, m[0].length).mapToObj(r -> IntStream.range(0, m.length).map(c -> m[c][r]).toArray()).toArray(int[][]::new); };
  }
  
  public static int[][] rect(int a, int b, int[][] arr) {
    int[][] result = new int[arr.length][];
    for (int i = 0; i < arr.length; i++)
      result[i] = Arrays.copyOf(arr[i], arr[i].length);
    for (int i = 0; i < b; i++)
      Arrays.fill(result[i], 0, a, 1);
    return result;
  }

  public static int[][] rotRow(int y, int by, int[][] arr) {
    int[][] result = new int[arr.length][];
    for (int i = 0; i < arr.length; i++)
      result[i] = Arrays.copyOf(arr[i], arr[i].length);
    for (int i = 0; i < result[y].length; i++)
      result[y][i] = arr[y][Math.floorMod(i - by, result[y].length)];
    return result;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day08_input.txt"));
    solve(lines);
  }
}
