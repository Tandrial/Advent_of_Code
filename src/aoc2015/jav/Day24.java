package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Day24 {

  private static long solve(List<String> list, int groupCount) {
    List<Integer> weights = loadWeights(list);

    int value = weights.stream().reduce(0, (a, b) -> a + b) / groupCount;
    int count = 1;
    List<List<Integer>> result = new ArrayList<>();
    do {
      for (List<Integer> c : new CombinationIterator<>(weights, count++))
        if (c.stream().reduce(0, (a, b) -> a + b) == value)
          result.add(c);

    } while (result.size() == 0);

    return result.stream().map(x -> x.stream().mapToLong(Long::valueOf).reduce(1L, (a, b) -> a * b)).sorted().findFirst().orElse(Long.MIN_VALUE);
  }

  private static List<Integer> loadWeights(List<String> list) {
    return list.stream().map(Integer::valueOf).collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day24_input.txt"));
    System.out.println("Part One = " + solve(s, 3));
    System.out.println("Part Two = " + solve(s, 4));
  }
}
