package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.IntStream;

class Bot {
  static String partOne;
  static int[]  cmp    = { -1, -1 };

  String        id     = "";
  int[]         values = { -1, -1 };
  String[]      passTo = { "", "" };

  public Bot(String id, String idLow, String idHigh) {
    this.id = id;
    passTo = new String[] { idHigh, idLow };
  }

  public Collection<String> accept(int value) {
    if (value > values[0])
      values = new int[] { value, values[0] };
    else
      values[1] = value;

    Collection<String> result = new ArrayList<>();
    if (values[0] != -1 && values[1] != -1) {
      if (values[0] == Bot.cmp[0] && values[1] == Bot.cmp[1])
        Bot.partOne = id;
      for (int i = 0; i < values.length; i++)
        result.add(String.format("value %d goes to %s", values[i], passTo[i]));
      values = new int[] { -1, -1 };
    }
    return result;
  }
}

public class Day10 {
  static Map<String, Bot> bots = new HashMap<>();

  public static String partOne(List<String> input, int[] cmp) {
    Bot.cmp = cmp;
    Queue<String> moves = new ArrayDeque<>(input);
    while (!moves.isEmpty()) {
      String move = moves.poll();
      Matcher valueAdd = Pattern.compile("value (\\d+) goes to (output \\d+|bot \\d+)").matcher(move);
      Matcher valueMove = Pattern.compile("(bot \\d+) gives low to (output \\d+|bot \\d+) and high to (output \\d+|bot \\d+)").matcher(move);
      
      if (valueAdd.matches() && (bots.containsKey(valueAdd.group(2)) || valueAdd.group(2).startsWith("output")))
        moves.addAll(bots.computeIfAbsent(valueAdd.group(2), x -> new Bot(x, "", "")).accept(Integer.valueOf(valueAdd.group(1))));
      else if (valueMove.matches())
        bots.put(valueMove.group(1), new Bot(valueMove.group(1), valueMove.group(2), valueMove.group(3)));
      else
        moves.add(move);
    }
    return Bot.partOne;
  }

  public static int partTwo(int max) {
    return IntStream.range(0, max + 1).map(x -> bots.get("output " + String.valueOf(x)).values[0]).reduce(1, Math::multiplyExact);
  }

  public static void main(String[] args) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./input/2016/Day10_input.txt"));
    System.out.println("Part One = " + partOne(lines, new int[] { 61, 17 }));
    System.out.println("Part Two = " + partTwo(2));
  }
}
