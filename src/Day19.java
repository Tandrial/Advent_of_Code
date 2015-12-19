import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day19 {

	public static long partOne(List<String> s) {
		Set<Rule> rules = new HashSet<>();
		String input = parseRules(s, rules);
		Set<String> output = new HashSet<>();
		rules.stream().forEach(r -> output.addAll(r.apply(input)));
		return output.size();
	}

	public static int partTwo(List<String> list) {
		Set<Rule> rules = new HashSet<>();
		String input = parseRules(list, rules);
		AtomicInteger step = new AtomicInteger(0);
		final Map<String, Integer> current = new HashMap<>();
		current.put(input, step.get());
		while (!current.containsKey("e")) {
			step.incrementAndGet();
			Map<String, Integer> next = new HashMap<>();
			Set<String> candidates = new HashSet<>();
			for (String key : current.keySet()) {
				rules.stream().filter(p -> p.wasApplied(key)).forEach(r -> candidates.addAll(r.revert(key)));
			}
			candidates.stream().filter(p -> !current.containsKey(p)).filter(p -> p.length() == 1 || !p.contains("e"))
					.sorted((a, b) -> a.length() - b.length()).limit(10).forEach(s -> next.put(s, step.get()));

			if (next.size() == 0)
				return -1;
			current.clear();
			current.putAll(next);
		}
		return current.get("e");
	}

	private static String parseRules(List<String> list, Set<Rule> rules) {
		for (String s : list) {
			String[] line = s.split(" => ");
			if (line.length == 2) {
				rules.add(new Rule(line[0], line[1]));
			} else if (line[0].length() > 0) {
				return line[0];
			}
		}
		return "";
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day19_input.txt"));
		System.out.println("Part One = " + partOne(s));
		long start = System.currentTimeMillis();
		System.out.println("Part One = " + partTwo(s));
		System.out.println(System.currentTimeMillis() - start);
	}
}

class Rule {
	String lhs;
	String rhs;

	public Rule(String lhs, String rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public boolean wasApplied(String s) {
		return s.indexOf(rhs) >= 0;
	}

	public Set<String> apply(String s) {
		return apply(s, lhs, rhs);
	}

	public Set<String> revert(String s) {
		return apply(s, rhs, lhs);
	}

	private Set<String> apply(String s, String in, String out) {
		Set<String> candidates = new HashSet<>();
		int idx = s.indexOf(in);
		while (idx >= 0) {
			StringBuilder sb = new StringBuilder();
			if (idx > 0)
				sb.append(s.substring(0, idx));
			sb.append(out);
			if (idx + in.length() < s.length())
				sb.append(s.substring(idx + in.length()));
			candidates.add(sb.toString());
			idx = s.indexOf(in, idx + in.length());
		}
		return candidates;
	}
}