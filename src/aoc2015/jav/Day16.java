package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Day16 {

  private static int solve(List<String> s, Map<String, Integer> properties, boolean partTwo) {
    for (Map<String, Integer> aunt : parseAunts(s)) {
      if (aunt.entrySet().stream().allMatch(entry -> {
        if (entry.getKey().equals("Sue"))
          return true;
        if (partTwo && (entry.getKey().equals("cats:") || entry.getKey().equals("trees:"))) {
          return entry.getValue().compareTo(properties.get(entry.getKey())) > 0;
        } else if (partTwo && (entry.getKey().equals("pomeranians:") || entry.getKey().equals("goldfish:"))) {
          return entry.getValue().compareTo(properties.get(entry.getKey())) < 0;
        } else {
          return properties.get(entry.getKey()).equals(entry.getValue());
        }
      }))
        return aunt.get("Sue");
    }
    return -1;
  }

  private static List<Map<String, Integer>> parseAunts(List<String> list) {
    return list.stream().map(t -> {
      String[] line = t.split(" ");
      Map<String, Integer> a = new HashMap<>();
      for (int i = 0; i <= line.length - 2; i += 2) {
        a.put(line[i], Integer.valueOf(line[i + 1].replace(":", "").replace(",", "")));
      }
      return a;
    }).collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day16_input.txt"));
    Map<String, Integer> properties = new HashMap<>();
    properties.put("children:", 3);
    properties.put("cats:", 7);
    properties.put("samoyeds:", 2);
    properties.put("pomeranians:", 3);
    properties.put("akitas:", 0);
    properties.put("vizslas:", 0);
    properties.put("goldfish:", 5);
    properties.put("trees:", 3);
    properties.put("cars:", 2);
    properties.put("perfumes:", 1);
    System.out.println("Part One = " + solve(s, properties, false));
    System.out.println("Part Two = " + solve(s, properties, true));
  }
}
