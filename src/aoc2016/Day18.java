package aoc2016;

public class Day18 {
  private static long solve(String input, int roundCount) {
    long cnt = 0;
    for (int round = 1; round <= roundCount; round++) {
      cnt += input.chars().map(x -> x == '.' ? 1 : 0).sum();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < input.length(); i++)
        sb.append((i == 0 ? '.' : input.charAt(i - 1)) != (i == input.length() - 1 ? '.' : input.charAt(i + 1)) ? '^' : '.');
      input = sb.toString();
    }
    return cnt;
  }

  public static void main(String[] args) {
    System.out.println("Part One = " + solve("^.....^.^^^^^.^..^^.^.......^^..^^^..^^^^..^.^^.^.^....^^...^^.^^.^...^^.^^^^..^^.....^.^...^.^.^^.^", 40));
    System.out.println("Part Two = " + solve("^.....^.^^^^^.^..^^.^.......^^..^^^..^^^^..^.^^.^.^....^^...^^.^^.^...^^.^^^^..^^.....^.^...^.^.^^.^", 400000));
  }
}
