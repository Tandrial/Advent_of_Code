import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day15 {

	public static long solve(List<String> list, boolean limit_cals) {
		List<Integer[]> ingredients = parseIngeddients(list);
		Integer[] amounts = new Integer[ingredients.size()];
		for (int i = 0; i < amounts.length; i++) {
			amounts[i] = 0;
		}
		amounts[0] = 100;

		List<Integer[]> toCheck = new ArrayList<>();
		Set<Integer> visited = new HashSet<>();
		toCheck.add(amounts);
		long max = 0;
		while (toCheck.size() > 0) {
			Integer[] curr = toCheck.remove(0);
			long curr_score = calcScore(curr, ingredients, limit_cals);
			max = Math.max(max, curr_score);
			for (int i = 0; i < curr.length; i++) {
				for (int j = 0; j < curr.length; j++) {
					if (i == j)
						continue;
					Integer[] neu = curr.clone();
					neu[i] += 1;
					neu[j] -= 1;
					if (neu[i] > 100 || neu[j] < 0 || visited.contains(Arrays.hashCode(neu)))
						continue;
					toCheck.add(neu);
					visited.add(Arrays.hashCode(neu));
				}
			}
		}
		return max;
	}

	public static List<Integer[]> parseIngeddients(List<String> list) {
		return list.stream().map(t -> {
			String[] line = t.split(" ");
			Integer[] i = new Integer[5];
			for (int x = 0; x < 5; x++)
				i[x] = Integer.valueOf(line[(x + 1) * 2].replace(",", ""));
			return i;
		}).collect(Collectors.toList());

	}

	private static long calcScore(Integer[] amounts, List<Integer[]> ingredients, boolean limit_cals) {
		int[] scores = new int[5];

		for (int i = 0; i < ingredients.size(); i++) {
			Integer[] in = ingredients.get(i);
			for (int j = 0; j < in.length; j++) {
				scores[j] += in[j] * amounts[i];
			}
		}
		if (limit_cals && scores[4] != 500)
			return 0;

		return Arrays.stream(scores).limit(scores.length - 1).reduce(1, (a, b) -> a * Math.max(0, b));
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day15_input.txt"));
		System.out.println("Part One = " + solve(s, false));
		System.out.println("Part Two = " + solve(s, true));
	}
}
