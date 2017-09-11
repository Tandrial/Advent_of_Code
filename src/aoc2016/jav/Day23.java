package aoc2016.jav;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Day23 {

  private static long getValue(String s, long[] regs) {
    if (s.charAt(0) >= 'a' && s.charAt(0) <= 'd')
      return regs[s.charAt(0) - 'a'];
    else
      return Long.valueOf(s);
  }

  private static long solve(List<String> list, long start_regA) {
    List<String[]> ram = list.stream().map(t -> t.split(" ")).collect(Collectors.toList());
    // optimize inner loop
    ram.set(4, new String[]{"mul", "b", "d", "a"});
    ram.set(4 + 1, new String[]{"cpy", "0", "c"});
    ram.set(4 + 2, new String[]{"cpy", "0", "d"});
    for (int j = 3; j <= 5; j++)
      ram.set(4 + j, new String[]{"jnz", "0", "0"});
    int pc = 0, reg;
    long[] regs = {start_regA, 0, 0, 0};
    while (pc < ram.size()) {
      String[] inst = ram.get(pc);
      reg = inst[1].charAt(0) - 'a';
      switch (inst[0]) {
        case "tgl":
          if (pc + getValue(inst[1], regs) < ram.size()) {
            String[] insts = ram.get(pc + (int) getValue(inst[1], regs));
            if (insts.length == 2)
              insts[0] = insts[0].equals("inc") ? "dec" : "inc";
            else
              insts[0] = insts[0].equals("jnz") ? "cpy" : "jnz";
          }
          break;
        case "cpy":
          if (inst[2].charAt(0) >= 'a' && inst[2].charAt(0) <= 'd')
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
            pc += getValue(inst[2], regs) - 1;
          break;
        case "mul":
          regs[inst[3].charAt(0) - 'a'] = getValue(inst[1], regs) * getValue(inst[2], regs);
          break;
      }
      pc++;
    }
    return regs[0];
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2016/Day23_input.txt"));
    System.out.println("Part One = " + solve(s, 7));
    System.out.println("Part One = " + solve(s, 12));
  }
}