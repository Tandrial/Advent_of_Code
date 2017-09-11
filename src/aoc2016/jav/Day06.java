package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

class Day06 {
  private static String solve(Character[][] input, Comparator<Map.Entry<Character, Long>> sortBy) {
    StringBuilder sb = new StringBuilder();
    for (Character[] xs : input) {
      Map<Character, Long> freqDist = Arrays.stream(xs).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      freqDist.entrySet().stream().sorted(sortBy).limit(1).map(Map.Entry::getKey).forEachOrdered(sb::append);
    }
    return sb.toString();
  }

  private static Character[][] transpose(Character[][] input) {
    return IntStream.range(0, input[0].length).mapToObj(r -> Arrays.stream(input).map(characters -> characters[r]).toArray(Character[]::new)).toArray(Character[][]::new);
  }

  private static Character[][] parse(List<String> lines) {
    return lines.stream().map(s -> s.chars().mapToObj(c -> (char) c).toArray(Character[]::new)).toArray(Character[][]::new);
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day06_input.txt"));
    System.out.println("Part One = " + solve(transpose(parse(lines)), Collections.reverseOrder(Map.Entry.comparingByValue())));
    System.out.println("Part Two = " + solve(transpose(parse(lines)), Map.Entry.comparingByValue()));
  }
}
