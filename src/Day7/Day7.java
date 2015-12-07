package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {

	public static Map<String, Integer> table = new HashMap<>();

	public static int partOne(List<String> list) {
		return partTwo(list, -1);
	}

	public static int partTwo(List<String> list, int a_force) {
		List<Gate> toSolve = new ArrayList<>();
		for (String s : list) {
			String[] line = s.split(" -> ");
			String out = line[1];
			String[] in_arr = line[0].split(" ");
			Gate v1 = null, v2 = null;
			if (in_arr.length == 3) {
				if (isNumeric(in_arr[0]))
					v1 = new NumInput(Integer.valueOf(in_arr[0]));
				else
					v1 = new VarInput(in_arr[0]);
				if (isNumeric(in_arr[2]))
					v2 = new NumInput(Integer.valueOf(in_arr[2]));
				else
					v2 = new VarInput(in_arr[2]);
			}

			if (s.contains("NOT")) {
				if (isNumeric(in_arr[1])) {
					int value = Integer.valueOf(in_arr[0]);
					v1 = new NumInput(value);
				} else
					v1 = new VarInput(in_arr[1]);
				toSolve.add(new NOTGate(v1, out));
			} else if (s.contains("AND")) {
				toSolve.add(new ANDGate(v1, v2, out));
			} else if (s.contains("OR")) {
				toSolve.add(new ORGate(v1, v2, out));
			} else if (s.contains("LSHIFT")) {
				toSolve.add(new LSHIFTGate(v1, v2, out));
			} else if (s.contains("RSHIFT")) {
				toSolve.add(new RSHIFTGate(v1, v2, out));
			} else {
				if (isNumeric(in_arr[0])) {
					int value = Integer.valueOf(in_arr[0]);
					if (out.equals("b") && a_force != -1)
						Day7.table.put(out, a_force);
					else
						Day7.table.put(out, value);
				} else {
					v1 = new VarInput(in_arr[0]);
					toSolve.add(new ASSIGNGate(v1, out));
				}
			}
		}
		while (toSolve.size() > 0)
			for (int i = 0; i < toSolve.size(); i++) {
				Gate logic = toSolve.get(i);
				if (logic.canSolve()) {
					int value = logic.getValue();
					Day7.table.put(logic.getOut(), value);
					toSolve.remove(i);
					i--;
				}
			}
		return table.get("a");
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
		int res = partOne(s);
		System.out.println("Part One = " + res);
		table.clear();
		System.out.println("Part Two = " + partTwo(s, res));
	}
}
