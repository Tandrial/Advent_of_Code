import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {
	static private List<String> persons = new ArrayList<>();
	static private Map<String, Integer> happiness = new HashMap<>();

	public static long partOne(List<String> s) {
		parseList(s);
		return solve();
	}

	public static long partTwo(List<String> s) {
		parseList(s);
		for (String p : persons) {
			happiness.put(p + "|" + "tan", 0);
			happiness.put("tan|" + p, 0);
		}
		persons.add("tan");
		return solve();
	}

	private static void parseList(List<String> s) {
		persons.clear();
		happiness.clear();
		for (String p : s) {
			String[] line = p.split(" ");
			String p1 = line[0];
			String p2 = line[10].replace(".", "");
			int value = Integer.parseInt(line[3]);
			value *= line[2].equals("gain") ? 1 : -1;
			happiness.put(p1 + "|" + p2, value);
			if (!persons.contains(p1))
				persons.add(p1);
			if (!persons.contains(p2))
				persons.add(p2);
		}
	}

	private static long solve() {
		List<String> seatingPositions = genSeatingpositions(persons.size());
		long maxHappy = Long.MIN_VALUE;
		for (String pos : seatingPositions) {
			long currHappy = 0;
			char[] pos_arr = pos.toCharArray();
			for (int i = 0; i < persons.size(); i++) {
				int pos_l = i - 1 == -1 ? persons.size() - 1 : i - 1;
				String left = persons.get(pos_arr[i] - '0') + "|" + persons.get(pos_arr[pos_l] - '0');
				currHappy += happiness.get(left);

				int pos_r = (i + 1) % persons.size();
				String right = persons.get(pos_arr[i] - '0') + "|" + persons.get(pos_arr[pos_r] - '0');
				currHappy += happiness.get(right);
			}
			maxHappy = Math.max(maxHappy, currHappy);
		}
		return maxHappy;
	}

	private static List<String> genSeatingpositions(int count) {
		StringBuilder permute = new StringBuilder();
		for (int i = 0; i < count; i++) {
			permute.append(i);
		}
		return permutation("", permute.toString(), new ArrayList<String>());
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
		List<String> s = Files.readAllLines(Paths.get("./input/Day13_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
