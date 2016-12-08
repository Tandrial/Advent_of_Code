package aoc2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day08 {

  public static int partOne(List<String> input) {
    boolean[][] arr = new boolean[6][50];
    for (String s : input) {
      if (s.startsWith("rect")) {
        int a = Integer.valueOf(s.split(" ")[1].split("x")[0]);
        int b = Integer.valueOf(s.split(" ")[1].split("x")[1]);
        arr = rect(a, b, arr);
      } else if (s.startsWith("rotate")) {
        int x = Integer.valueOf(s.split("=")[1].split(" ")[0]);
        int by = Integer.valueOf(s.split("by ")[1]);
        arr = s.contains("row") ? rotRow(x, by, arr) : rotCol(x, by, arr);
      }
    }
    int cnt = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        if (arr[i][j]) {
          cnt++;
          System.out.print('#');
        } else
          System.out.print(' ');
      }
      System.out.println();
    }
    return cnt;
  }

  public static boolean[][] rect(int a, int b, boolean[][] arr) {
    boolean[][] result = new boolean[arr.length][];
    for (int i = 0; i < arr.length; i++)
      result[i] = Arrays.copyOf(arr[i], arr[i].length);
    for (int i = 0; i < b; i++)
      for (int j = 0; j < a; j++)
        result[i][j] = true;
    return result;
  }

  public static boolean[][] rotRow(int y, int by, boolean[][] arr) {
    boolean[][] result = new boolean[arr.length][];
    for (int i = 0; i < arr.length; i++)
      result[i] = Arrays.copyOf(arr[i], arr[i].length);
    for (int i = result[y].length - 1; i >= by; i--)
      result[y][i] = arr[y][i - by];
    for (int i = 0; i < by; i++)
      result[y][i] = arr[y][arr[0].length - by + i];
    return result;
  }

  public static boolean[][] rotCol(int x, int by, boolean[][] arr) {
    return transpose(rotRow(x, by, transpose(arr)));
  }

  public static boolean[][] transpose(boolean[][] arr) {
    boolean[][] result = new boolean[arr[0].length][arr.length];
    for (int i = 0; i < result.length; i++)
      for (int j = 0; j < result[0].length; j++)
        result[i][j] = arr[j][i];
    return result;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day08_input.txt"));
    System.out.println("Part One = " + partOne(lines));
  }
}
