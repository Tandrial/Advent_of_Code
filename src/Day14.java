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

		for (int i = 0; i < dur; i++) {
			Reindeer.maxDistance = 0;
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
				return new Reindeer(Integer.parseInt(line[3]), Integer.parseInt(line[6]), Integer.parseInt(line[13]));
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

	int fly_speed;
	int fly_durr;
	int rest_durr;

	boolean flying;
	int counter;

	int points;
	int distance;

	public Reindeer(int fly_speed, int fly_durr, int rest_durr) {
		this.fly_speed = fly_speed;
		this.fly_durr = fly_durr;
		this.rest_durr = rest_durr;
		this.points = 0;
		this.flying = true;
		this.counter = 0;
	}

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