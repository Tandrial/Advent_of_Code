import java.io.IOException;
import java.nio.file.*;
import java.util.stream.IntStream;

public class Day25 {
	public static long solve(String s) {
		int row = Integer.valueOf(s.split(" ")[16].replace(",", "").trim());
		int col = Integer.valueOf(s.split(" ")[18].replace(".", "").trim());

		long code = 20151125L;
		int step = IntStream.range(1, row + col - 1).boxed().reduce(0, (a, b) -> a + b) + col;

		for (int i = 1; i < step; i++) {
			code *= 252533;
			code %= 33554393;
		}

		return code;
	}

	public static void main(String[] args) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get(".input/Day25_input.txt")));
		System.out.println("Part One = " + solve(s));
	}
}