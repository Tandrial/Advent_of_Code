package aoc2016;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Day20 {

  private static class Range {
    private final long start;
    private final long stop;

    public Range(String s) {
      String[] in = s.split("-");
      start = Long.valueOf(in[0]);
      stop = Long.valueOf(in[1]);
    }
  }

  private static long partOne(List<String> input) {
    long result = 0;
    List<Range> ranges = input.stream().map(Range::new).collect(Collectors.toList());
    ranges.sort(Comparator.comparingLong(o -> o.start));
    for (Range range : ranges) {
      if (result < range.start)
        return result;
      result = Math.max(result, range.stop + 1);
    }
    return result;
  }

  private static long partTwo(List<String> input, long max) {
    long result = 0;
    long cnt = 0;
    List<Range> ranges = input.stream().map(Range::new).collect(Collectors.toList());
    ranges.sort(Comparator.comparingLong(o -> o.start));
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
    System.out.println("Part One = " + partOne(s));
    System.out.println("Part Two = " + partTwo(s, 4294967295L));
  }
}
