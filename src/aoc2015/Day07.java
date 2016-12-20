package aoc2015;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class Day07 {

  private final Map<String, String> expressionMap = new HashMap<>();
  private final Map<String, Integer> cache = new HashMap<>();

  private Day07(List<String> list) {
    for (String s : list) {
      String[] line = s.split(" -> ");
      expressionMap.put(line[1], line[0]);
    }
  }

  private static boolean isNumeric(String str) {
    return str.chars().allMatch(Character::isDigit);
   }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day07_input.txt"));
    Day07 day7 = new Day07(s);
    int res = day7.eval("a");
    System.out.println("Part One = " + res);
    day7.cache.clear();
    day7.expressionMap.put("b", String.valueOf(res));
    System.out.println("Part Two = " + day7.eval("a"));
  }

  private int eval(String expr) {
    if (!cache.containsKey(expr)) {
      if (isNumeric(expr)) {
        cache.put(expr, Integer.valueOf(expr));
      } else if (expr.contains("AND")) {
        String[] line = expr.split(" AND ");
        cache.put(expr, eval(line[0]) & eval(line[1]));
      } else if (expr.contains("OR")) {
        String[] line = expr.split(" OR ");
        cache.put(expr, eval(line[0]) | eval(line[1]));
      } else if (expr.contains("NOT")) {
        cache.put(expr, ~eval(expr.substring(4)));
      } else if (expr.contains("LSHIFT")) {
        String[] line = expr.split(" LSHIFT ");
        cache.put(expr, (eval(line[0]) << eval(line[1])) & 0xFFFF);
      } else if (expr.contains("RSHIFT")) {
        String[] line = expr.split(" RSHIFT ");
        cache.put(expr, eval(line[0]) >> eval(line[1]));
      } else {
        cache.put(expr, eval(expressionMap.get(expr)));
      }
    }
    return cache.get(expr);
  }
}
