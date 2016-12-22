package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Day17 {

  private static List<Integer> partOne(List<String> list) {
    List<Integer> containers = list.stream().mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
    List<Integer> solutionLength = new ArrayList<>();
    System.out.println("Part One = " + solve(150, 0, containers, solutionLength));
    return solutionLength;
  }

  private static long partTwo(List<Integer> solutions) {
    int min = solutions.stream().min(Integer::compareTo).get();
    return solutions.stream().filter(x -> x.equals(min)).count();
  }

  private static int solve(int liters, int used, List<Integer> unused, List<Integer> sLength) {
    if (liters == 0) {
      sLength.add(used);
      return 1;
    } else if (liters < 0 || unused.isEmpty())
      return 0;
    else {
      List<Integer> tail = unused.subList(1, unused.size());
      return solve(liters - unused.get(0), used + 1, tail, sLength) + solve(liters, used, tail, sLength);
    }
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day17_input.txt"));
    List<Integer> solutions = partOne(s);
    System.out.println("Part Two = " + partTwo(solutions));
  }
}
