package Day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {

	public static Map<String, Integer> varLookUp = new HashMap<>();

	public static int partOne(List<String> list) {
		return partTwo(list, -1);
	}

	public static int partTwo(List<String> list, int a_force) {
		List<Gate> toSolve = new ArrayList<>();
		varLookUp.clear();
		for (String s : list) {
			String[] line = s.split(" -> ");
			String[] in = line[0].split(" ");
			Gate in1 = null, in2 = null;
			if (in.length == 3) {
				in1 = new Input(in[0]);
				in2 = new Input(in[2]);
			}

			String out = line[1];
			if (s.contains("NOT")) {
				in1 = new Input(in[1]);
				toSolve.add(new NOTGate(in1, out));
			} else if (s.contains("AND")) {
				toSolve.add(new ANDGate(in1, in2, out));
			} else if (s.contains("OR")) {
				toSolve.add(new ORGate(in1, in2, out));
			} else if (s.contains("LSHIFT")) {
				toSolve.add(new LSHIFTGate(in1, in2, out));
			} else if (s.contains("RSHIFT")) {
				toSolve.add(new RSHIFTGate(in1, in2, out));
			} else {
				if (isNumeric(in[0])) {
					int value = Integer.valueOf(in[0]);
					if (out.equals("b") && a_force != -1)
						Day7.varLookUp.put(out, a_force);
					else
						Day7.varLookUp.put(out, value);
				} else {
					in1 = new Input(in[0]);
					toSolve.add(new ASSIGNGate(in1, out));
				}
			}
		}

		while (toSolve.size() > 0) {
			for (int i = toSolve.size() - 1; i >= 0; i--) {
				Gate logic = toSolve.get(i);
				if (logic.canSolve()) {
					int value = logic.getValue();
					Day7.varLookUp.put(logic.getOut(), value);
					toSolve.remove(i);
				}
			}
		}
		
		return varLookUp.get("a");
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
		System.out.println("Part Two = " + partTwo(s, res));
	}
}
