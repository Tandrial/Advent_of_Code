package aoc2015;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day13 {
  static private List<String>         persons   = new ArrayList<>();
  static private Map<String, Integer> happiness = new HashMap<>();

  public static long partOne(List<String> s) {
    parseList(s);
    return solve();
  }

  public static long partTwo(List<String> s) {
    parseList(s);
    persons.add("tan");
    return solve();
  }

  private static void parseList(List<String> s) {
    persons.clear();
    happiness.clear();
    for (String p : s) {
      String[] line = p.split(" ");
      String p1 = line[0];
      String p2 = line[10].replace(".", "");
      int value = Integer.parseInt(line[3]);
      value *= line[2].equals("gain") ? 1 : -1;
      happiness.put(p1 + p2, value);
      if (!persons.contains(p1))
        persons.add(p1);
      if (!persons.contains(p2))
        persons.add(p2);
    }
  }

  private static long solve() {
    long maxHappy = Long.MIN_VALUE;
    for (Integer[] pos : new NumberPermutation(persons.size())) {
      long currHappy = 0;
      for (int i = 0; i < persons.size(); i++) {
        int pos_l = i - 1 == -1 ? persons.size() - 1 : i - 1;
        String left = persons.get(pos[i]) + persons.get(pos[pos_l]);
        currHappy += happiness.containsKey(left) ? happiness.get(left) : 0;

        int pos_r = (i + 1) % persons.size();
        String right = persons.get(pos[i]) + persons.get(pos[pos_r]);
        currHappy += happiness.containsKey(right) ? happiness.get(right) : 0;
      }
      maxHappy = Math.max(maxHappy, currHappy);
    }
    return maxHappy;
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day13_input.txt"));
    System.out.println("Part One = " + partOne(s));
    System.out.println("Part Two = " + partTwo(s));
  }
}
