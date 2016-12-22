package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class Day07 {

  private static int partOne(List<String> input) {
    int cnt = 0;
    for (String s : input) {
      boolean found = false;
      boolean inHypernet = false;
      char[] s_arr = s.toCharArray();
      for (int i = 0; i < s_arr.length - 3; i++) {
        if (s_arr[i] == '[')
          inHypernet = true;
        else if (s_arr[i] == ']')
          inHypernet = false;
        else if (s_arr[i] != s_arr[i + 1] && s_arr[i] == s_arr[i + 3] && s_arr[i + 1] == s_arr[i + 2])
          if (inHypernet) {
            found = false;
            break;
          } else
            found = true;
      }
      if (found)
        cnt++;
    }
    return cnt;
  }

  private static int i;

  private static int partTwo(List<String> input) {
    int cnt = 0;
    for (String s : input) {
      List<String> hypernet = getHyperNets(s);
      char[] s_arr = s.toCharArray();
      for (i = 0; i < s_arr.length - 2; i++) {
        if (s_arr[i] == '[') {
          i = s.indexOf(']', i);
        } else if (s_arr[i] == s_arr[i + 2] && s_arr[i] != s_arr[i + 1]) {
          if (hypernet.stream().anyMatch(net -> net.contains(String.valueOf(s_arr[i + 1]) + s_arr[i] + s_arr[i + 1]))) {
            cnt++;
            break;
          }
        }
      }
    }
    return cnt;
  }

  private static List<String> getHyperNets(String s) {
    List<String> result = new ArrayList<>();
    int start = 0;
    while ((start = s.indexOf('[', start)) != -1) {
      int end = s.indexOf(']', start);
      result.add(s.substring(start + 1, end));
      start = end + 1;
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day07_input.txt"));
    System.out.println("Part One = " + partOne(lines));
    System.out.println("Part One = " + partTwo(lines));
  }
}
