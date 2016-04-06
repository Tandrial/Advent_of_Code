import java.io.IOException;
import java.nio.file.*;
import java.util.*;

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
		distances = new int[list.size()][list.size()];
		cities = new HashMap<>();
		for (String s : list) {
			String[] line = s.split(" = ");
			int value = Integer.valueOf(line[1]);
			int[] indexes = { 0, 0 };
			line = line[0].split(" to ");
			for (int i = 0; i < indexes.length; i++) {
				if (cities.containsKey(line[i])) {
					indexes[i] = cities.get(line[i]);
				} else {
					indexes[i] = cities.size();
					cities.put(line[i], indexes[i]);
				}
			}
			distances[indexes[0]][indexes[1]] = value;
			distances[indexes[1]][indexes[0]] = value;
		}
		return cities.size();
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day09_input.txt"));
		System.out.println("Part One = " + solve(s, true));
		System.out.println("Part Two = " + solve(s, false));
	}
}
