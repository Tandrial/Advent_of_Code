package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

class Layout {
  static Set<Layout> seenLayouts = new HashSet<>();

  int                elevatorPos = 0;
  List<Set<String>>  floors      = new ArrayList<>();

  public Layout(List<String> input) {
    for (String line : input) {
      Matcher m = Pattern.compile("(a \\w+-compatible microchip|a \\w+ generator)(and (a \\w+-compatible microchip)| and (a \\w+ generator))*").matcher(line);
      Set<String> floor = new HashSet<>();
      while (m.find())
        floor.add(m.group(1));
      floors.add(floor);
    }
  }

  public Layout(int elevatorPos, List<Set<String>> floors) {
    this.elevatorPos = elevatorPos;
    this.floors = floors;
  }

  @Override
  public int hashCode() {
    return elevatorPos + floors.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || !(obj instanceof Layout))
      return false;
    Layout other = (Layout) obj;
    if (elevatorPos != other.elevatorPos)
      return false;
    return floors.equals(other.floors);
  }

  public boolean isValid() {
    for (Set<String> floor : floors) {
      Set<String> unpairedChips = new HashSet<>();
      Set<String> generators = new HashSet<>();
      for (String item : floor) {
        if (item.contains("microchip"))
          unpairedChips.add(item);
        else if (item.contains("generator"))
          generators.add(item);
      }
      generators.stream().map(generator -> generator.replace(" generator", "-compatible microchip")).forEach(unpairedChips::remove);
      if (unpairedChips.size() > 0 && generators.size() > 0)
        return false;
    }
    return true;
  }

  public boolean isDone() {
    return !floors.stream().limit(floors.size() - 1).anyMatch(floor -> floor.size() > 0);
  }

  public int getScore() {
    int cnt = 0;
    for (int i = 0; i < floors.size() - 1; i++) {
      Set<String> unpairedChips = new HashSet<>();
      Set<String> generators = new HashSet<>();
      cnt += (floors.size() - i) * floors.get(i).size();
      for (String item : floors.get(i)) {
        if (item.contains("microchip"))
          unpairedChips.add(item);
        else if (item.contains("generator"))
          generators.add(item);
      }
      for (String gen : generators)
        if (unpairedChips.remove(gen.replace(" generator", "-compatible microchip")))
          cnt -= (floors.size() - i);
    }
    return cnt;
  }

  public Set<Layout> genNextMoves() {
    Set<Set<String>> possibleMoves = new HashSet<>();
    for (String item : floors.get(elevatorPos)) {
      possibleMoves.add(new HashSet<>(Arrays.asList(new String[] { item })));
      for (String item2 : floors.get(elevatorPos))
        possibleMoves.add(new HashSet<>(Arrays.asList(new String[] { item, item2 })));
    }
    Set<Layout> nextMoves = new HashSet<>();
    for (int dir = -1; dir <= 1; dir += 2) {
      if (elevatorPos == 0 && dir == -1)
        continue;
      if (elevatorPos == 3 && dir == 1)
        continue;
      if (dir == -1 && !floors.stream().limit(elevatorPos).anyMatch(floor -> floor.size() > 0))
        continue;

      for (Set<String> move : possibleMoves) {
        List<Set<String>> floorsneu = new ArrayList<>();
        for (int i = 0; i < floors.size(); i++) {
          Set<String> f = new HashSet<>(floors.get(i));
          if (i == elevatorPos) {
            f.removeAll(move);
          } else if (i == elevatorPos + dir) {
            f.addAll(move);
          }
          floorsneu.add(f);
        }

        Layout newLayout = new Layout(elevatorPos + dir, floorsneu);
        if (seenLayouts.contains(newLayout) || !newLayout.isValid())
          continue;
        seenLayouts.add(newLayout);
        nextMoves.add(newLayout);
      }
    }
    return nextMoves;
  }
}

public class Day11 {
  private static int solve(Layout layout) {
    Layout.seenLayouts.clear();
    int step = 0;
    PriorityQueue<Layout> layouts = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getScore(), o2.getScore()));
    layouts.add(layout);
    while (layouts.size() > 0) {
      PriorityQueue<Layout> nextLayouts = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getScore(), o2.getScore()));
      for (Layout l : layouts) {
        nextLayouts.addAll(l.genNextMoves().stream().collect(Collectors.toSet()));
        if (nextLayouts.size() >= 7000)
          break;
      }
      step++;
      if (nextLayouts.stream().anyMatch(Layout::isDone))
        break;
      layouts = nextLayouts;
    }
    return step;
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day11_input.txt"));
    Layout layout = new Layout(lines);
    System.out.println("Part One = " + solve(layout));
    layout.floors.get(0).addAll(Arrays.asList(new String[] { "a elerium generator", "a elerium-compatible microchip", "a dilithium generator", "a dilithium-compatible microchip" }));
    System.out.println("Part Two = " + solve(layout));
  }
}
