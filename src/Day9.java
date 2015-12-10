import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9 {

	private static Map<String, Integer> cities;
	private static int[][] distances;

	public static int partOne(List<String> list) {
		int count = genDistances(list);
		List<String> routes = genRoutes(count);
		int min = Integer.MAX_VALUE;
		outer: for (String per : routes) {
			String[] moves = per.split("");
			int curr = 0;
			for (int i = 0; i < moves.length - 1; i++) {
				int start = Integer.valueOf(moves[i]);
				int dst = Integer.valueOf(moves[i + 1]);
				int dist = distances[start][dst];
				if (dist == 0)
					continue outer;
				curr += dist;
			}

			if (curr < min) {
				min = curr;
			}
		}
		return min;
	}

	public static int partTwo(List<String> list) {
		int count = genDistances(list);
		List<String> routes = genRoutes(count);
		int max = 0;
		outer: for (String per : routes) {
			String[] moves = per.split("");
			int curr = 0;
			for (int i = 0; i < moves.length - 1; i++) {
				int start = Integer.valueOf(moves[i]);
				int dst = Integer.valueOf(moves[i + 1]);
				int dist = distances[start][dst];
				if (dist == 0)
					continue outer;
				curr += dist;
			}

			if (curr > max) {
				max = curr;
			}
		}
		return max;
	}

	private static List<String> genRoutes(int count) {
		String permute = "";
		for (int i = 0; i < count; i++) {
			permute += i;
		}
		return permutation("", permute, new ArrayList<String>());
	}

	private static int genDistances(List<String> list) {
		int count = 0;
		distances = new int[list.size()][list.size()];
		cities = new HashMap<>();
		for (String s : list) {
			String[] line = s.split(" = ");
			int value = Integer.valueOf(line[1]);
			int idx_1, idx_2;
			line = line[0].split(" to ");
			if (cities.containsKey(line[0])) {
				idx_1 = cities.get(line[0]);
			} else {
				idx_1 = count++;
				cities.put(line[0], idx_1);
			}
			if (cities.containsKey(line[1])) {
				idx_2 = cities.get(line[1]);
			} else {
				idx_2 = count++;
				cities.put(line[1], idx_2);
			}

			distances[idx_1][idx_2] = value;
			distances[idx_2][idx_1] = value;
		}
		return count;
	}

	private static List<String> permutation(String prefix, String str, List<String> acc) {
		int n = str.length();
		if (n == 0)
			acc.add(prefix);
		else {
			for (int i = 0; i < n; i++)
				acc = permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), acc);
		}
		return acc;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day9_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
