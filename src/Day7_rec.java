import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7_rec {

	private Map<String, String> expressionMap = new HashMap<>();
	private Map<String, Integer> cache = new HashMap<>();

	public Day7_rec(List<String> list) {
		for (String s : list) {
			String[] line = s.split(" -> ");
			expressionMap.put(line[1], line[0]);
		}
	}

	public int eval(String expression) {
		if (cache.containsKey(expression)) {
			return cache.get(expression);
		} else if (isNumeric(expression)) {
			cache.put(expression, Integer.valueOf(expression));
		} else if (expression.contains("AND")) {
			String[] line = expression.split(" AND ");
			cache.put(expression, eval(line[0]) & eval(line[1]));
		} else if (expression.contains("OR")) {
			String[] line = expression.split(" OR ");
			cache.put(expression, eval(line[0]) | eval(line[1]));
		} else if (expression.contains("NOT")) {
			cache.put(expression, ~eval(expression.substring(4)));
		} else if (expression.contains("LSHIFT")) {
			String[] line = expression.split(" LSHIFT ");
			cache.put(expression, (eval(line[0]) << eval(line[1])) & 0xFFFF);
		} else if (expression.contains("RSHIFT")) {
			String[] line = expression.split(" RSHIFT ");
			cache.put(expression, eval(line[0]) >> eval(line[1]));
		} else {
			cache.put(expression, eval(expressionMap.get(expression)));
		}
		return cache.get(expression);
	}

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day7_input.txt"));
		Day7_rec day7 = new Day7_rec(s);
		int res = day7.eval("a");
		System.out.println("Part One = " + res);
		day7.cache.clear();
		day7.expressionMap.put("b", String.valueOf(res));
		System.out.println("Part Two = " + day7.eval("a"));
	}
}
