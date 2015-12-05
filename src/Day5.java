import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Day5 {

	public static int partOne(List<String> list) {
		int count = 0;
		for (String s : list) {
			if (!Pattern.matches("(.*[aeiou]){3}.*", s))
				continue;

			if (!Pattern.matches(".*(.)\\1.*", s))
				continue;

			if (Pattern.matches(".*(ab|cd|pq|xy).*", s))
				continue;

			count++;
		}
		return count;
	}

	public static int partTwo(List<String> list) {
		int count = 0;
		for (String s : list) {
			if (!Pattern.matches(".*(..).*\\1.*", s))
				continue;

			if (!Pattern.matches(".*(.)\\w\\1.*", s))
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
