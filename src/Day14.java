import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14 {
	static long maxDistance = 0;
	static long maxPoints = 0;

	public static long partOne(List<String> list, int dur) {
		simulate(list, dur);
		return maxDistance;
	}

	public static long partTwo(List<String> list, int dur) {
		simulate(list, dur);
		return maxPoints;
	}

	public static void simulate(List<String> list, int dur) {
		List<Reindeer> reindeers = parseReindeer(list);
		maxDistance = 0;
		maxPoints = 0;

		Reindeer.maxDistance = 0;
		for (int i = 0; i < dur; i++) {
			for (Reindeer r : reindeers) {
				r.tick();
			}
			for (Reindeer r : reindeers) {
				if (r.distance == Reindeer.maxDistance)
					r.points++;
				maxDistance = Math.max(maxDistance, r.distance);
				maxPoints = Math.max(maxPoints, r.points);
			}
		}
	}

	private static List<Reindeer> parseReindeer(List<String> list) {
		return list.stream().map(new Function<String, Reindeer>() {
			@Override
			public Reindeer apply(String t) {
				String[] line = t.split(" ");
				Reindeer r = new Reindeer();
				r.fly_speed = Integer.parseInt(line[3]);
				r.fly_durr = Integer.parseInt(line[6]);
				r.rest_durr = Integer.parseInt(line[13]);
				return r;
			}
		}).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day14_input.txt"));
		System.out.println("Part One = " + partOne(s, 2503));
		System.out.println("Part Two = " + partTwo(s, 2503));
	}
}

class Reindeer {

	public static int maxDistance;

	int fly_speed, fly_durr, rest_durr;
	int points, distance, counter;
	boolean flying = true;

	public void tick() {
		counter++;
		if (flying) {
			if (counter == fly_durr) {
				flying = false;
				counter = 0;
			}
			distance += fly_speed;
			Reindeer.maxDistance = Math.max(maxDistance, distance);
		} else if (counter == rest_durr) {
			flying = true;
			counter = 0;
		}
	}
}