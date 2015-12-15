import java.io.IOException;
import java.nio.file.*;

import com.google.gson.*;

public class Day12 {

	public static int solve(JsonElement root, boolean checkForRed) {
		int sum = 0;
		if (root.isJsonArray()) {
			JsonArray jsonArray = root.getAsJsonArray();
			for (JsonElement elem : jsonArray) {
				sum += solve(elem, checkForRed);
			}
		} else if (root.isJsonObject()) {
			JsonObject jsonObject = root.getAsJsonObject();
			for (java.util.Map.Entry<String, JsonElement> elem : jsonObject.entrySet()) {
				if (checkForRed && elem.getValue().isJsonPrimitive() && elem.getValue().getAsString().equals("red"))
					return 0;
				else
					sum += solve(elem.getValue(), checkForRed);
			}
		} else if (root.isJsonPrimitive()) {
			try {
				sum += root.getAsInt();
			} catch (NumberFormatException ex) {
			}
		}
		return sum;
	}

	public static void main(String[] args) throws IOException {
		String s = new String(Files.readAllBytes(Paths.get("./input/Day12_input.txt")));
		JsonElement root = new JsonParser().parse(s);
		System.out.println("Part One = " + solve(root, false));
		System.out.println("Part Two = " + solve(root, true));
	}
}