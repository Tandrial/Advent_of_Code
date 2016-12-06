package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day06 {
  public static String solve(Character[][] input,Comparator<Map.Entry<String, Long>> sortBy) {
    StringBuilder sb = new StringBuilder();
    for (Character[] xs : input) {
      Map<String, Long> freqDist = Arrays.stream(xs).map(c -> String.valueOf(c)).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
      sb.append(freqDist.entrySet().stream().sorted(sortBy).limit(1).map(Map.Entry::getKey).collect(Collectors.joining()));
    }
    return sb.toString();
  }

  public static Character[][] transpose(Character[][] input) {
    return IntStream.range(0, input[0].length).mapToObj(r -> IntStream.range(0, input.length).mapToObj(c -> Character.valueOf((char) input[c][r])).toArray(Character[]::new)).toArray(Character[][]::new);
  }

  public static Character[][] parse(List<String> lines) {
    return lines.stream().map(s -> s.chars().mapToObj(c -> Character.valueOf((char) c)).toArray(Character[]::new)).toArray(Character[][]::new);
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day06_input.txt"));
    System.out.println("Part One = " + solve(transpose(parse(lines)),Collections.reverseOrder(Map.Entry.comparingByValue())));
    System.out.println("Part Two = " + solve(transpose(parse(lines)),Map.Entry.comparingByValue()));    
  }
}
