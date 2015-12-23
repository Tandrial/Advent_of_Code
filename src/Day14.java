import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 {
	static long maxDistance = 0;
	static long maxPoints = 0;

	public static void simulate(List<String> list, int dur) {
		List<Reindeer> reindeers = parseReindeer(list);
		maxDistance = 0;
		maxPoints = 0;
		for (int i = 0; i < dur; i++) {
			reindeers.forEach(Reindeer::tick);
			reindeers.forEach(Reindeer::addPoints);
		}
		reindeers.forEach(Day14::updateStats);
	}

	private static void updateStats(Reindeer r) {
		maxDistance = Math.max(maxDistance, r.distance);
		maxPoints = Math.max(maxPoints, r.points);
	}

	private static List<Reindeer> parseReindeer(List<String> list) {
		return list.stream().map(t -> {
			String[] line = t.split(" ");
			Reindeer r = new Reindeer();
			r.fly_speed = Integer.parseInt(line[3]);
			r.fly_dur = Integer.parseInt(line[6]);
			r.rest_dur = Integer.parseInt(line[13]);
			return r;
		}).collect(Collectors.toList());

	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day14_input.txt"));
		simulate(s, 2503);
		System.out.println("Part One = " + Day14.maxDistance);
		System.out.println("Part Two = " + Day14.maxPoints);
	}
}

class Reindeer {
	public static int maxDistance = 0;

	int fly_speed, fly_dur, rest_dur;
	int points, distance, counter;
	boolean flying = true;

	public void tick() {
		counter++;
		if (flying) {
			if (counter == fly_dur) {
				flying = false;
				counter = 0;
			}
			distance += fly_speed;
			Reindeer.maxDistance = Math.max(maxDistance, distance);
		} else if (counter == rest_dur) {
			flying = true;
			counter = 0;
		}
	}

	public void addPoints() {
		if (distance == Reindeer.maxDistance)
			points++;
	}
}
