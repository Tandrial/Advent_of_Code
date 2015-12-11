import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {

	private Map<String, String> expressionMap = new HashMap<>();
	private Map<String, Integer> cache = new HashMap<>();

	public Day7(List<String> list) {
		for (String s : list) {
			String[] line = s.split(" -> ");
			expressionMap.put(line[1], line[0]);
		}
	}

	public int eval(String expr) {
		if (!cache.containsKey(expr)) {
			if (isNumeric(expr)) {
				cache.put(expr, Integer.valueOf(expr));
			} else if (expr.contains("AND")) {
				String[] line = expr.split(" AND ");
				cache.put(expr, eval(line[0]) & eval(line[1]));
			} else if (expr.contains("OR")) {
				String[] line = expr.split(" OR ");
				cache.put(expr, eval(line[0]) | eval(line[1]));
			} else if (expr.contains("NOT")) {
				cache.put(expr, ~eval(expr.substring(4)));
			} else if (expr.contains("LSHIFT")) {
				String[] line = expr.split(" LSHIFT ");
				cache.put(expr, (eval(line[0]) << eval(line[1])) & 0xFFFF);
			} else if (expr.contains("RSHIFT")) {
				String[] line = expr.split(" RSHIFT ");
				cache.put(expr, eval(line[0]) >> eval(line[1]));
			} else {
				cache.put(expr, eval(expressionMap.get(expr)));
			}
		}
		return cache.get(expr);
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
		Day7 day7 = new Day7(s);
		int res = day7.eval("a");
		System.out.println("Part One = " + res);
		day7.cache.clear();
		day7.expressionMap.put("b", String.valueOf(res));
		System.out.println("Part Two = " + day7.eval("a"));
	}
}
