package aoc2016.jav;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

class Day25 {
    private static long getValue(String s, long[] regs) {
        if (s.charAt(0) >= 'a' && s.charAt(0) <= 'd')
            return regs[s.charAt(0) - 'a'];
        else
            return Long.valueOf(s);
    }

    private static long solve(List<String> list) {
        List<String[]> ram = list.stream().map(t -> t.split(" ")).collect(Collectors.toList());
        long[] regs = {0, 0, 0, 0};
        for (int i = 0; i < 1000; i++) {
            int periodeLength = 0;
            regs[0] = i;
            int pc = 0, reg;
            long lastSig = 1;
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
                    case "out":
                        if (getValue(inst[1], regs) == lastSig) {
                            pc = Integer.MAX_VALUE - 1;
                        } else if (periodeLength > 500)
                            return i;
                        periodeLength++;
                        lastSig = getValue(inst[1], regs);
                        break;
                }
                pc++;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        List<String> s = Files.readAllLines(Paths.get("./input/2016/Day25_input.txt"));
        System.out.println("Part One = " + solve(s));
    }
}
