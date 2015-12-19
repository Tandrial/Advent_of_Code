import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day19 {

	public static long partOne(List<String> list, String input) {
		Set<Rule> rules = parseRules(list);
		Set<String> output = new HashSet<>();
		rules.stream().forEach(r -> output.addAll(r.apply(input)));
		return output.size();
	}

	public static int partTwo(List<String> list, String input) {
		Set<Rule> rules = parseRules(list);
		int step = 0;
		final Set<String> current = new HashSet<String>();
		current.add(input);
		while (current.size() > 0 && !current.contains("e")) {
			step++;
			Set<String> candidates = new HashSet<>();
			for (String key : current)
				rules.stream().filter(p -> p.wasApplied(key)).forEach(r -> candidates.addAll(r.revert(key)));

			Set<String> next = new HashSet<>();
			candidates.stream().filter(p -> !current.contains(p)).filter(p -> p.length() == 1 || !p.contains("e"))
					.sorted((a, b) -> a.length() - b.length()).limit(10).forEach(s -> next.add(s));

			current.clear();
			current.addAll(next);
		}
		return step;
	}

	private static Set<Rule> parseRules(List<String> list) {
		return list.stream().map((String s) -> s.split(" => ")).map((String[] line) -> new Rule(line[0], line[1]))
				.collect(Collectors.toSet());
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day19_input.txt"));
		String input = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";
		System.out.println("Part One = " + partOne(s, input));
		System.out.println("Part Two = " + partTwo(s, input));
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
		return Rule.apply(s, lhs, rhs);
	}

	public Set<String> revert(String s) {
		return Rule.apply(s, rhs, lhs);
	}

	private static Set<String> apply(String s, String in, String out) {
		Set<String> candidates = new HashSet<>();
		int idx = s.indexOf(in);
		while (idx >= 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(s.substring(0, idx));
			sb.append(out);
			sb.append(s.substring(idx + in.length()));
			candidates.add(sb.toString());
			idx = s.indexOf(in, idx + in.length());
		}
		return candidates;
	}
}