import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day16 {

	private static int solve(List<String> s, Map<String, Integer> properties, boolean partTwo) {
		for (Map<String, Integer> aunt : parseAunts(s)) {
			if (aunt.entrySet().stream().map(new Function<Map.Entry<String, Integer>, Boolean>() {
				@Override
				public Boolean apply(Map.Entry<String, Integer> t) {
					if (t.getKey().equals("Sue"))
						return true;
					if (partTwo && (t.getKey().equals("cats:") || t.getKey().equals("trees:"))) {
						return t.getValue().compareTo(properties.get(t.getKey())) > 0;
					} else if (partTwo && (t.getKey().equals("pomeranians:") || t.getKey().equals("goldfish:"))) {
						return t.getValue().compareTo(properties.get(t.getKey())) < 0;
					} else {
						return properties.get(t.getKey()).equals(t.getValue());
					}
				}
			}).allMatch(t -> t))
				return aunt.get("Sue");
		}
		return -1;
	}

	private static List<Map<String, Integer>> parseAunts(List<String> list) {
		return list.stream().map(new Function<String, Map<String, Integer>>() {
			@Override
			public Map<String, Integer> apply(String t) {
				String[] line = t.split(" ");
				Map<String, Integer> a = new HashMap<>();
				for (int i = 0; i <= line.length - 2; i += 2) {
					a.put(line[i], Integer.valueOf(line[i + 1].replace(":", "").replace(",", "")));
				}
				return a;
			}
		}).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day16_input.txt"));
		Map<String, Integer> properties = new HashMap<>();
		properties.put("children:", 3); properties.put("cats:", 7);
		properties.put("samoyeds:", 2);	properties.put("pomeranians:", 3);
		properties.put("akitas:"  , 0);	properties.put("vizslas:", 0);
		properties.put("goldfish:", 5);	properties.put("trees:", 3);
		properties.put("cars:"    , 2);	properties.put("perfumes:", 1);
		System.out.println("Part One = " + solve(s, properties, false));
		System.out.println("Part Two = " + solve(s, properties, true));
	}
}
