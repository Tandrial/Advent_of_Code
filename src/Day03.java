import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Day03 {

	public static int partOne(String s) {
		return solve(s, 1);
	}

	public static int partTwo(String s) {
		return solve(s, 2);
	}

	public static int solve(String s, int santaCount) {
		HashSet<String> houses = new HashSet<>();
		int[][] pos = new int[santaCount][2];
		houses.add("0:0");
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '>':
				pos[i % santaCount][0]++;
				break;
			case '^':
				pos[i % santaCount][1]++;
				break;
			case '<':
				pos[i % santaCount][0]--;
				break;
			case 'v':
				pos[i % santaCount][1]--;
				break;
			default:
				break;
			}
			houses.add(pos[i % santaCount][0] + ":" + pos[i % santaCount][1]);
		}
		return houses.size();
	}

	public static void main(String[] args) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get("./input/Day03_input.txt")));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
