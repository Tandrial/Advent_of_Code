import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24 {

	private static long partOne(List<String> list) {
		List<Integer> weights = loadWeights(list);
		Collections.sort(weights);
		int value = weights.stream().reduce(0, (a, b) -> a + b) / 3;
		List<Integer> w1 = new ArrayList<Integer>();
		w1.addAll(weights);
		Set<Set<Integer>> result = solve(value, new HashSet<Integer>(), w1, new HashSet<Set<Integer>>());

		return result.stream().sorted((a, b) -> Integer.compare(a.size(), b.size()))
				.map(t -> t.stream().reduce(1, (a, b) -> a * b)).findFirst().get();
	}

	private static Set<Set<Integer>> solve(int weight, Set<Integer> current, List<Integer> unused,
			Set<Set<Integer>> combinations) {
		if (weight == 0) {
			combinations.add(current);
			return combinations;
		} else if (weight < 0 || unused.isEmpty() || current.size() > 5)
			return combinations;
		else {
			List<Integer> tail = unused.subList(1, unused.size());
			Set<Set<Integer>> one = solve(weight, current, tail, combinations);
			Set<Integer> neu = new HashSet<>();
			neu.addAll(current);
			neu.add(unused.get(0));
			Set<Set<Integer>> two = solve(weight - unused.get(0), neu, tail, combinations);
			one.addAll(two);
			return one;
		}
	}

	private static List<Integer> loadWeights(List<String> list) {
		return list.stream().map(t -> Integer.valueOf(t)).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day24_input.txt"));
		System.out.println("Part One = " + partOne(s));
		// System.out.println("Part Two = " + solve(s, 1));
	}

}
