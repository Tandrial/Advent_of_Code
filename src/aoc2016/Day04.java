package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day04 {
  public static int partOne(List<String> input) {
    int partOne = 0;
    for (String s : input) {
      int secID = Integer.parseInt(s.substring(s.lastIndexOf('-') + 1, s.indexOf('[')));
      String hash = s.substring(s.indexOf('[') + 1, s.length() - 1);
      Map<Character, Integer> chars = new HashMap<>();
      for (Character c : s.toCharArray()) {
        if (c >= 'a' && c <= 'z') {
          if (chars.containsKey(c))
            chars.replace(c, chars.get(c) + 1);
          else
            chars.put(c, 1);
        }
      }
      if (buildHash(chars).equals(hash))
        partOne += secID;
    }
    return partOne;
  }

  public static int partTwo(List<String> input) {
    for (String s : input) {
      int secID = Integer.parseInt(s.substring(s.lastIndexOf('-') + 1, s.indexOf('[')));
      String d = s.codePoints().mapToObj(c -> String.valueOf((char) ((c - 'a' + secID) % 26 + 'a')))
          .collect(Collectors.joining());
      if (d.toString().startsWith("northpole"))
        return secID;
    }
    return -1;
  }

  public static String buildHash(Map<Character, Integer> count) {
    return count.entrySet().stream().sorted((o1, o2) -> {
      if (o2.getValue().equals(o1.getValue()))
        return o1.getKey().compareTo(o2.getKey());
      else
        return (o2.getValue()).compareTo(o1.getValue());
    }).limit(5).map(e -> String.valueOf(e.getKey())).collect(Collectors.joining());
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day04_input.txt"));
    System.out.println("Part One = " + partOne(lines));
    System.out.println("Part Two = " + partTwo(lines));
  }
}
