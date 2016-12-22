package aoc2015.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

class Day23 {

  private static long solve(List<String> list, long start_regA) {
    List<String[]> ram = list.stream().map(t -> t.replace(",", "").split(" ")).collect(Collectors.toList());
    int pc = 0, reg;
    long[] regs = {start_regA, 0};
    while (pc < ram.size()) {
      String[] inst = ram.get(pc);
      reg = inst[1].equals("a") ? 0 : 1;
      switch (inst[0]) {
        case "hlf":
          regs[reg] >>= 1;
          break;
        case "tpl":
          regs[reg] *= 3;
          break;
        case "inc":
          regs[reg]++;
          break;
        case "jmp":
          pc += Integer.valueOf(inst[1]) - 1;
          break;
        case "jie":
          if (regs[reg] % 2 == 0)
            pc += Integer.valueOf(inst[2]) - 1;
          break;
        case "jio":
          if (regs[reg] == 1)
            pc += Integer.valueOf(inst[2]) - 1;
          break;
      }
      pc++;
    }
    return regs[1];
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2015/Day23_input.txt"));
    System.out.println("Part One = " + solve(s, 0));
    System.out.println("Part Two = " + solve(s, 1));
  }
}
