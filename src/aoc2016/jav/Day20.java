package aoc2016.jav;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Day20 {
  private static class Range implements Comparable<Range> {
    private final Long start;
    private final Long stop;

    Range(String s) {
      String[] in = s.split("-");
      start = Long.valueOf(in[0]);
      stop = Long.valueOf(in[1]);
    }

    @Override
    public int compareTo(Range other) {
      return start.compareTo(other.start);
    }
  }

  private static long partOne(TreeSet<Range> ranges ) {
    long result = 0;
    for (Range range : ranges) {
      if (result < range.start)
        return result;
      result = Math.max(result, range.stop + 1);
    }
    return result;
  }

  private static long partTwo(TreeSet<Range> ranges , long max) {
    long result = 0;
    long cnt = 0;
    for (Range range : ranges) {
      if (result < range.start)
        cnt += range.start - result;
      result = Math.max(result, range.stop + 1);
    }
    cnt += max - (result - 1);
    return cnt;
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2016/Day20_input.txt"));
    TreeSet<Range> ranges = s.stream().map(Range::new).collect(Collectors.toCollection(TreeSet::new));
    System.out.println("Part One = " + partOne(ranges));
    System.out.println("Part Two = " + partTwo(ranges, 4294967295L));
  }
}
