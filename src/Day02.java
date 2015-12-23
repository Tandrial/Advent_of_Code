import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day02 {

	public static long partOne(List<String> list) {
		int totalFeet = 0;
		for (String s : list) {
			int[] size = new int[3];
			String[] s_split = s.split("x");
			for (int i = 0; i < s_split.length; i++) 
				size[i] = Integer.valueOf(s_split[i]);
						
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
			String[] s_split = s.split("x");
			for (int i = 0; i < s_split.length; i++) 
				size[i] = Integer.valueOf(s_split[i]);
			
			Arrays.sort(size);

			totalFeet += 2 * (size[0] + size[1]);
			totalFeet += size[0] * size[1] * size[2];
		}
		return totalFeet;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day02_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
