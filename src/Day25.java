import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day25 {
	public static long solve(String s) {
		int row = Integer.valueOf(s.split(" ")[16].replace(",", "").trim());
		int col = Integer.valueOf(s.split(" ")[18].replace(".", "").trim());

		long code = 20151125L;
		int step = row + col - 2;
		step = step * (step + 1) / 2 + col;

		for (int i = 1; i < step; i++) {
			code *= 252533;
			code %= 33554393;
		}
		return code;
	}

	public static void main(String[] args) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get("./input/Day25_input.txt")));
		System.out.println("Part One = " + solve(s));
	}
}
