package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day04 {
  public static int partOne(List<String> input) {
    int partOne = 0;
    for (String s : input) {
      String hash = s.substring(s.indexOf('[') + 1, s.length() - 1);
      Map<String, Long> chars = s.codePoints().filter(c -> c >= 'a' && c <= 'z').mapToObj(c -> String.valueOf((char) c)).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
      if (buildHash(chars).equals(hash)) {
        int secID = Integer.parseInt(s.substring(s.lastIndexOf('-') + 1, s.indexOf('[')));
        partOne += secID;
      }
    }
    return partOne;
  }

  public static int partTwo(List<String> input) {
    for (String s : input) {
      int secID = Integer.parseInt(s.substring(s.lastIndexOf('-') + 1, s.indexOf('[')));
      String d = s.codePoints().mapToObj(c -> String.valueOf((char) ((c - 'a' + secID) % 26 + 'a'))).collect(Collectors.joining());
      if (d.startsWith("northpole"))
        return secID;
    }
    return -1;
  }

  public static String buildHash(Map<String, Long> count) {
    return count.entrySet().stream().sorted(Map.Entry.comparingByKey()).sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(5).map(Map.Entry::getKey).collect(Collectors.joining());
  }
  
  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day04_input.txt"));
    System.out.println("Part One = " + partOne(lines));
    System.out.println("Part Two = " + partTwo(lines));
  }
}
