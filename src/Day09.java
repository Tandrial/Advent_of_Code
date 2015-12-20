import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day09 {

	private static Map<String, Integer> cities;
	private static int[][] distances;

	private static int solve(List<String> list, boolean findMin) {
		int count = genDistances(list);
		int max = 0;
		int min = Integer.MAX_VALUE;
		outer: for (Integer[] move : new NumberPermutation(count)) {
			int curr = 0;
			for (int i = 0; i < move.length - 1; i++) {
				int start = Integer.valueOf(move[i]);
				int dst = Integer.valueOf(move[i + 1]);
				int dist = distances[start][dst];
				if (dist == 0)
					continue outer;
				curr += dist;
			}
			max = Math.max(curr, max);
			min = Math.min(curr, min);
		}
		return findMin ? min : max;
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

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day09_input.txt"));
		System.out.println("Part One = " + solve(s, true));
		System.out.println("Part Two = " + solve(s, false));
	}
}
