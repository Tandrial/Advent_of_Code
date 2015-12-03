import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day2 {

	public static long partOne(List<String> list) {
		int totalFeet = 0;
		for (String s : list) {

			int[] size = new int[3];
			size[0] = Integer.valueOf(s.split("x")[0]);
			size[1] = Integer.valueOf(s.split("x")[1]);
			size[2] = Integer.valueOf(s.split("x")[2]);
			Arrays.sort(size);

			totalFeet += 2 * size[0] * size[1] + 2 * size[0] * size[2] + 2 * size[1] * size[2];
			totalFeet += size[0] * size[1];
		}
		return totalFeet;
	}

	public static long partTwo(List<String> list) {
		int totalFeet = 0;
		for (String s : list) {

			int[] size = new int[3];
			size[0] = Integer.valueOf(s.split("x")[0]);
			size[1] = Integer.valueOf(s.split("x")[1]);
			size[2] = Integer.valueOf(s.split("x")[2]);
			Arrays.sort(size);

			totalFeet += 2 * (size[0] + size[1]);
			totalFeet += size[0] * size[1] * size[2];
		}
		return totalFeet;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("Day2_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
