package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.IntStream;

class Day10 {
  private static class Bot {
    static String          partOne;
    String                 id     = "";
    final PriorityQueue<Integer> values = new PriorityQueue<>(2, Comparator.reverseOrder());
    String[]               passTo = { "", "" };

    public Bot(String id, String idLow, String idHigh) {
      this.id = id;
      this.passTo = new String[] { idHigh, idLow };
    }

    public Collection<String> accept(int value, int[] cmp) {
      values.add(value);
      Collection<String> result = new ArrayList<>();
      if (values.size() == 2) {
        int[] val = { values.poll(), values.poll() };
        if (val[0] == cmp[0] && val[1] == cmp[1])
          Bot.partOne = id;
        for (int i = 0; i < val.length; i++)
          result.add(String.format("value %d goes to %s", val[i], passTo[i]));
      }
      return result;
    }
  }

  private static final Map<String, Bot> bots = new HashMap<>();

  private static String partOne(List<String> input, int[] cmp) {
    Queue<String> moves = new ArrayDeque<>(input);
    while (!moves.isEmpty()) {
      String move = moves.poll();
      Matcher valueAdd = Pattern.compile("value (\\d+) goes to (\\S+ \\d+)").matcher(move);
      Matcher valueMove = Pattern.compile("(bot \\d+) gives low to (\\S+ \\d+) and high to (\\S+ \\d+)").matcher(move);

      if (valueAdd.matches() && (bots.containsKey(valueAdd.group(2)) || valueAdd.group(2).startsWith("output")))
        moves.addAll(bots.computeIfAbsent(valueAdd.group(2), x -> new Bot(x, "", "")).accept(Integer.valueOf(valueAdd.group(1)), cmp));
      else if (valueMove.matches())
        bots.put(valueMove.group(1), new Bot(valueMove.group(1), valueMove.group(2), valueMove.group(3)));
      else
        moves.add(move);
    }
    return Bot.partOne;
  }

  private static int partTwo() {
    return IntStream.range(0, 2 + 1).map(x -> bots.get("output " + String.valueOf(x)).values.peek()).reduce(1, Math::multiplyExact);
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day10_input.txt"));
    System.out.println("Part One = " + partOne(lines, new int[] { 61, 17 }));
    System.out.println("Part Two = " + partTwo());
  }
}
