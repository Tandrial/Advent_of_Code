package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

import com.google.common.collect.MinMaxPriorityQueue;

public class Day11 {

  static Set<List<Set<String>>> seenLayouts = new HashSet<>();

  public static Set<List<Set<String>>> genNextMoves(List<Set<String>> layout) {
    seenLayouts.add(layout);
    Set<List<Set<String>>> result = new HashSet<>();
    int currFloor = -1;

    for (int i = 0; i < layout.size(); i++) {
      if (layout.get(i).contains("E")) {
        currFloor = i;
        break;
      }
    }
    Set<String> floor = new HashSet<>(layout.get(currFloor));
    floor.remove("E");
    Set<Set<String>> moves = new HashSet<>();

    for (String s : floor) {
      Set<String> sSet = new HashSet<>();
      sSet.add(s);
      moves.add(sSet);
      for (String s2 : floor) {
        sSet = new HashSet<>();
        sSet.add(s);
        sSet.add(s2);
        moves.add(sSet);
      }
    }

    for (Integer dir : new int[] { -1, 1 }) {
      for (Set<String> x : moves) {
        if (currFloor == 0 && dir == -1)
          continue;
        if (currFloor == 3 && dir == 1)
          continue;
        if (dir == -1) {
          boolean skip = true;
          for (int i = 0; i < currFloor; i++) {
            if (layout.get(i).size() != 0)
              skip = false;
          }
          if (skip)
            continue;
        }

        List<Set<String>> newLayout = new ArrayList<>();
        for (Set<String> f : layout) {
          newLayout.add(new HashSet<>(f));
        }
        newLayout.get(currFloor).removeAll(x);
        newLayout.get(currFloor).remove("E");
        newLayout.get(currFloor + dir).addAll(x);
        newLayout.get(currFloor + dir).add("E");
        if (seenLayouts.contains(newLayout) || !checkLayout(newLayout))
          continue;
        seenLayouts.add(newLayout);
        result.add(newLayout);
      }
    }
    return result;
  }

  public static Integer scoreLayout(List<Set<String>> layout) {
    int cnt = 0;
    for (int i = 0; i < layout.size() - 1; i++) {
      Set<String> floor = layout.get(i);
      cnt += (layout.size() - i) * layout.get(i).size();
      Set<String> chips = new HashSet<>();
      Set<String> generators = new HashSet<>();
      for (String item : floor) {
        if (item.contains("microchip"))
          chips.add(item);
        else if (item.contains("generator")) {
          generators.add(item);
        }
      }
      for (String gen : generators) {
        if (chips.remove(gen.replace(" generator", "-compatible microchip"))) {
          cnt -= (layout.size() - i);
        }
      }
    }
    return cnt;
  }

  public static boolean checkLayout(List<Set<String>> layout) {
    boolean result = true;
    for (Set<String> floor : layout) {
      Set<String> chips = new HashSet<>();
      Set<String> generators = new HashSet<>();
      for (String item : floor) {
        if (item.contains("microchip"))
          chips.add(item);
        else if (item.contains("generator")) {
          generators.add(item);
        }
      }
      for (String gen : generators) {
        chips.remove(gen.replace(" generator", "-compatible microchip"));
      }
      if (chips.size() > 0 && generators.size() > 0) {
        result = false;
        break;
      }
    }
    return result;
  }

  public static boolean checkDone(List<Set<String>> layout) {
    for (int i = 0; i < layout.size() - 1; i++)
      if (layout.get(i).size() > 0)
        return false;
    return true;
  }

  public static List<Set<String>> parse(List<String> input) {
    List<Set<String>> result = new ArrayList<>();
    for (String line : input) {
      Set<String> floor = new HashSet<>();
      if (line.contains("first floor"))
        floor.add("E");
      Matcher m = Pattern
          .compile(
              "(a \\w+-compatible microchip|a \\w+ generator)(and (a \\w+-compatible microchip)| and (a \\w+ generator))*")
          .matcher(line);

      while (m.find()) {
        floor.add(m.group(1));
      }
      result.add(floor);
    }

    return result;
  }

  private static int solve(List<Set<String>> layout) {
    boolean foundGoal = false;
    int step = 0;
    MinMaxPriorityQueue<List<Set<String>>> layouts = MinMaxPriorityQueue
        .orderedBy((List<Set<String>> o1, List<Set<String>> o2) -> scoreLayout(o1).compareTo(scoreLayout(o2)))
        .maximumSize(8000).create();
    layouts.add(layout);
    while (!foundGoal && layouts.size() > 0) {
      MinMaxPriorityQueue<List<Set<String>>> nextLayouts = MinMaxPriorityQueue
          .orderedBy((List<Set<String>> o1, List<Set<String>> o2) -> scoreLayout(o1).compareTo(scoreLayout(o2)))
          .maximumSize(8000).create();
      for (List<Set<String>> l : layouts) {
        nextLayouts.addAll(genNextMoves(l).stream().collect(Collectors.toSet()));
      }
      step++;
      if (nextLayouts.stream().anyMatch(Day11::checkDone))
        foundGoal = true;
      layouts = nextLayouts;
      System.out.println("step = " + step + " moves.size = " + nextLayouts.size());
    }
    return step;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day11_input.txt"));
    List<Set<String>> layout = parse(lines);

    System.out.println("Part One = " + solve(layout));
    layout = parse(lines);
    for (Set<String> floor : layout) {
      if (floor.contains("E")) {
        floor.add("a elerium generator");
        floor.add("a elerium-compatible microchip");
        floor.add("a dilithium generator");
        floor.add("a dilithium-compatible microchip");
      }
    }
    System.out.println("Part Two = " + solve(layout));
  }
}
