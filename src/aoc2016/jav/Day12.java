package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

class Day12 {

  private static long getValue(String s, long[] regs) {
    if (s.charAt(0) >= 'a' && s.charAt(0) <= 'd')
      return regs[s.charAt(0) - 'a'];
    else
      return Long.valueOf(s);
  }

  private static long solve(List<String> list, long start_regC) {
    List<String[]> ram = list.stream().map(t -> t.split(" ")).collect(Collectors.toList());
    int pc = 0, reg;
    long[] regs = {0, 0, start_regC, 0};
    while (pc < ram.size()) {
      String[] inst = ram.get(pc);
      reg = inst[1].charAt(0) - 'a';
      switch (inst[0]) {
        case "cpy":
          regs[inst[2].charAt(0) - 'a'] = getValue(inst[1], regs);
          break;
        case "inc":
          regs[reg]++;
          break;
        case "dec":
          regs[reg]--;
          break;
        case "jnz":
          if (getValue(inst[1], regs) != 0L)
            pc += Integer.valueOf(inst[2]) - 1;
          break;
      }
      pc++;
    }
    return regs[0];
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2016/Day12_input.txt"));
    System.out.println("Part One = " + solve(s, 0));
    System.out.println("Part Two = " + solve(s, 1));
  }
}
