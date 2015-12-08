import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day6 {

	public static int partOne(List<String> list) {
		int[][] grid = new int[1000][1000];
		for (String s : list) {
			String[] line = s.split(" ");
			Point p1 = new Point(Integer.valueOf(line[line.length - 3].split(",")[0]),
					Integer.valueOf(line[line.length - 3].split(",")[1]));
			Point p2 = new Point(Integer.valueOf(line[line.length - 1].split(",")[0]),
					Integer.valueOf(line[line.length - 1].split(",")[1]));

			for (int x = p1.x; x <= p2.x; x++) {
				for (int y = p1.y; y <= p2.y; y++) {
					if (line[0].equals("toggle")) {
						grid[x][y] = grid[x][y] == 0 ? 1 : 0;
					} else if (line[1].equals("on")) {
						grid[x][y] = 1;
					} else {
						grid[x][y] = 0;
					}
				}
			}
		}		
		return Arrays.stream(grid).flatMapToInt(x -> Arrays.stream(x)).sum();
	}

	public static long partTwo(List<String> list) {
		int[][] grid = new int[1000][1000];
		for (String s : list) {
			String[] line = s.split(" ");
			Point p1 = new Point(Integer.valueOf(line[line.length - 3].split(",")[0]),
					Integer.valueOf(line[line.length - 3].split(",")[1]));
			Point p2 = new Point(Integer.valueOf(line[line.length - 1].split(",")[0]),
					Integer.valueOf(line[line.length - 1].split(",")[1]));

			for (int x = p1.x; x <= p2.x; x++) {
				for (int y = p1.y; y <= p2.y; y++) {
					if (line[0].equals("toggle")) {
						grid[x][y] += 2;
					} else if (line[1].equals("on")) {
						grid[x][y] += 1;
					} else {
						grid[x][y] = Math.max(0, grid[x][y] - 1);
					}
				}
			}
		}
		return Arrays.stream(grid).flatMapToInt(x -> Arrays.stream(x)).sum();
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day6_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
