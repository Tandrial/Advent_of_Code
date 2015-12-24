import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day24 {

	private static long solve(List<String> list, int groupCount) {
		int[] weights = loadWeights(list);

		int value = Arrays.stream(weights).reduce(0, (a, b) -> a + b) / groupCount;
		int count = 1;
		List<Long[]> result;
		do {
			result = combinations(count++, weights);
			result.removeIf(p -> Arrays.stream(p).reduce(0L, (a, b) -> a + b) != value);

		} while (result.size() == 0);

		return result.stream().map(x -> Arrays.stream(x).reduce(1L, (a, b) -> a * b)).sorted().findFirst().get();
	}

	// http://stackoverflow.com/a/15603638
	public static List<Long[]> combinations(int k, int[] set) {
		// binomial(N, K)
		int c = (int) binomial(set.length, k);
		// where all sets are stored
		List<Long[]> res = new ArrayList<>();
		// int[][] res = new int[c][Math.max(0, k)];
		// the k indexes (from set) where the red squares are
		// see image above
		int[] ind = k < 0 ? null : new int[k];
		// initialize red squares
		for (int i = 0; i < k; ++i) {
			ind[i] = i;
		}
		// for every combination
		for (int i = 0; i < c; ++i) {
			Long[] neu = new Long[k];
			// get its elements (red square indexes)
			for (int j = 0; j < k; ++j) {
				neu[j] = (long) set[ind[j]];
			}
			// update red squares, starting by the last
			int x = ind.length - 1;
			boolean loop;
			do {
				loop = false;
				// move to next
				ind[x] = ind[x] + 1;
				// if crossing boundaries, move previous
				if (ind[x] > set.length - (k - x)) {
					--x;
					loop = x >= 0;
				} else {
					// update every following square
					for (int x1 = x + 1; x1 < ind.length; ++x1) {
						ind[x1] = ind[x1 - 1] + 1;
					}
				}
			} while (loop);
			res.add(neu);
		}
		return res;
	}

	private static long binomial(int n, int k) {
		if (k < 0 || k > n)
			return 0;
		if (k > n - k) { // take advantage of symmetry
			k = n - k;
		}
		long c = 1;
		for (int i = 1; i < k + 1; ++i) {
			c = c * (n - (k - i));
			c = c / i;
		}
		return c;
	}

	private static int[] loadWeights(List<String> list) {
		int[] res = new int[list.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = Integer.valueOf(list.get(i));
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("/home/michael/Advent_of_Code/input/Day24_input.txt"));
		System.out.println("Part One = " + solve(s, 3));
		System.out.println("Part Two = " + solve(s, 4));
	}

}
