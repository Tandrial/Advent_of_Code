import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Day23 {

	public static long solve(List<String> list, long start_regA) {
		String[][] ram = loadRam(list);
		long pc = 0, reg_a = start_regA, reg_b = 0, val;
		while (pc < ram.length) {
			String[] inst = ram[(int) pc];
			switch (inst[0]) {
			case "hlf":
				if (inst[1].equals("a"))
					reg_a >>= 1;
				else
					reg_b >>= 1;
				pc++;
				break;
			case "tpl":
				if (inst[1].equals("a"))
					reg_a *= 3;
				else
					reg_b *= 3;
				pc++;
				break;
			case "inc":
				if (inst[1].equals("a"))
					reg_a++;
				else
					reg_b++;
				pc++;
				break;
			case "jmp":
				pc += Integer.valueOf(inst[1]);
				break;
			case "jie":
				val = inst[1].equals("a") ? reg_a : reg_b;
				if (val % 2 == 0)
					pc += Integer.valueOf(inst[2]);
				else
					pc++;
				break;
			case "jio":
				val = inst[1].equals("a") ? reg_a : reg_b;
				if (val == 1)
					pc += Integer.valueOf(inst[2]);
				else
					pc++;
				break;
			}
		}
		return reg_b;
	}

	private static String[][] loadRam(List<String> list) {
		String[][] ram = new String[list.size()][];
		for (int i = 0; i < ram.length; i++)
			ram[i] = list.get(i).replace(",", "").split(" ");
		return ram;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day23_input.txt"));
		System.out.println("Part One = " + solve(s, 0));
		System.out.println("Part Two = " + solve(s, 1));
	}
}
