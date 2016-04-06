import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 {

	private static long solve(List<String> list, int groupCount) {
		List<Integer> weights = loadWeights(list);

		int value = weights.stream().reduce(0, (a, b) -> a + b) / groupCount;
		int[] size = { 1, 1 };
		List<List<Integer>> result = new ArrayList<>();
		do {
			for (List<Integer> g1 : new CombinationIterator<Integer>(weights, size[0]++)) {
				if (g1.stream().reduce(0, (a, b) -> a + b) == value) {
					List<Integer> remaining = new ArrayList<Integer>();
					remaining.addAll(weights);
					remaining.removeAll(g1);
					while (size[1] <= remaining.size() || result.size() == 0) {
						for (List<Integer> c2 : new CombinationIterator<Integer>(remaining, size[1]++)) {
							if (c2.stream().reduce(0, (a, b) -> a + b) == value) {
								result.add(g1);
								break;
							}
						}
					}
				}
			}
		} while (result.size() == 0);

		return result.stream().map(x -> x.stream().mapToLong(Long::valueOf).reduce(1L, (a, b) -> a * b)).sorted()
				.findFirst().get();
	}

	private static List<Integer> loadWeights(List<String> list) {
		return list.stream().map(t -> Integer.valueOf(t)).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("/home/michael/Advent_of_Code/input/Day24_input.txt"));
		System.out.println("Part One = " + solve(s, 3));
		System.out.println("Part Two = " + solve(s, 4));
	}
}
