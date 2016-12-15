package aoc2015;
import java.io.IOException;
import java.nio.file.*;

public class Day01 {

	private static int partOne(String s) {
		return s.chars().map(x -> x == '(' ? 1 : -1).sum();
	}

	private static int partTwo(String s, int acc, int step) {
		if (s.length() == 0 || acc == -1)
			return step;
		else if (s.charAt(0) == '(')
			acc++;
		else if (s.charAt(0) == ')')
			acc--;
		return partTwo(s.substring(1), acc, step + 1);
	}

	public static void main(String[] args) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get("./input/2015/Day01_input.txt")));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s, 0, 0));
	}
}
