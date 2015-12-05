import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5 {

	public static int partOne(List<String> list) {
		String[] bad = { "ab", "cd", "pq", "xy" };
		int count = 0;
		
		outer: for (String s : list) {
			if (s.chars().filter(x -> x == 'a' || x == 'i' || x == 'o' || x == 'e' || x == 'u').count() < 3) {
				continue;
			}

			boolean check = false;
			for (int i = 0; i < s.length() - 1; i++) {
				if (s.charAt(i) == s.charAt(i + 1)) {
					check = true;
					break;
				}
			}
			if (!check)
				continue;

			for (String b : bad) {
				if (s.contains(b))
					continue outer;
			}

			count++;
		}
		return count;
	}

	public static int partTwo(List<String> list) {
		int count = 0;
		for (String s : list) {
			boolean check = false;
			for (int i = 0; i < s.length() - 1; i++) {
				String curr = s.substring(i, i + 2);
				if (s.substring(i + 2).contains(curr)) {
					check = true;
					break;
				}
			}
			if (!check)
				continue;

			check = false;
			for (int i = 1; i < s.length() - 1; i++) {
				if (s.charAt(i - 1) == s.charAt(i + 1)) {
					check = true;
					break;
				}
			}

			if (!check)
				continue;

			count++;
		}
		return count;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day5_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
