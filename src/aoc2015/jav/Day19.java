package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Day19 {

  private static long partOne(List<String> list, String input) {
    Set<Rule> rules = parseRules(list);
    Set<String> output = new HashSet<>();
    rules.forEach(r -> output.addAll(r.apply(input)));
    return output.size();
  }

  private static int partTwo(List<String> list, String input) {
    Set<Rule> rules = parseRules(list);
    int step = 0;
    final Set<String> current = new HashSet<>();
    current.add(input);
    while (current.size() > 0 && !current.contains("e")) {
      step++;
      Set<String> candidates = new HashSet<>();
      current.forEach(key -> rules.stream().map(r -> r.revert(key)).forEach(candidates::addAll));

      Set<String> next = candidates.stream().filter(p -> !current.contains(p)).sorted(Comparator.comparingInt(String::length))
        .limit(10).collect(Collectors.toSet());

      current.clear();
      current.addAll(next);
    }
    return step;
  }

  private static Set<Rule> parseRules(List<String> list) {
    return list.stream().map((String s) -> s.split(" => ")).map((String[] line) -> new Rule(line[0], line[1]))
      .collect(Collectors.toSet());
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day19_input.txt"));
    String input = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";
    System.out.println("Part One = " + partOne(s, input));
    System.out.println("Part Two = " + partTwo(s, input));
  }
}

class Rule {
  private final String lhs;
  private final String rhs;

  public Rule(String lhs, String rhs) {
    this.lhs = lhs;
    this.rhs = rhs;
  }

  private static Set<String> apply(String s, String in, String out) {
    Set<String> candidates = new HashSet<>();
    int idx = s.indexOf(in);
    while (idx >= 0) {
      String sb = s.substring(0, idx) + out + s.substring(idx + in.length());
      candidates.add(sb);
      idx = s.indexOf(in, idx + in.length());
    }
    return candidates;
  }

  Set<String> apply(String s) {
    return Rule.apply(s, lhs, rhs);
  }

  Set<String> revert(String s) {
    return Rule.apply(s, rhs, lhs);
  }
}
