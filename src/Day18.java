import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Day18 {

	public static long solve(List<String> s, boolean force) {
		Boolean[][] grid = parseGrid(s);
		for (int i = 0; i < 100; i++) {
			if (force)
				grid = forceLights(grid);
			grid = nextGen(grid);
		}
		if (force)
			grid = forceLights(grid);
		return Arrays.stream(grid).mapToLong((Boolean[] value) -> Arrays.stream(value).filter(v -> v).count()).sum();
	}

	private static Boolean[][] forceLights(Boolean[][] grid) {
		grid[0][0] = true;
		grid[0][grid[0].length - 1] = true;
		grid[grid[0].length - 1][0] = true;
		grid[grid[0].length - 1][grid[0].length - 1] = true;
		return grid;
	}

	private static Boolean[][] nextGen(Boolean[][] grid) {
		Boolean[][] next = new Boolean[grid.length][];
		for (int i = 0; i < next.length; i++)
			next[i] = grid[i].clone();

		for (int x = 0; x < next.length; x++) {
			for (int y = 0; y < next[x].length; y++) {
				int count = countNeighbours(x, y, grid);
				if (count == 3)
					next[x][y] = true;
				else if (count != 2)
					next[x][y] = false;
			}
		}
		return next;
	}

	private static int countNeighbours(int x, int y, Boolean[][] grid) {
		int count = 0;
		for (int x_off = x == 0 ? 0 : -1; x_off <= ((x == grid.length - 1) ? 0 : 1); x_off++) {
			for (int y_off = y == 0 ? 0 : -1; y_off <= ((y == grid.length - 1) ? 0 : 1); y_off++) {
				if ((x_off != 0 || y_off != 0) && grid[x + x_off][y + y_off])
					count++;
			}
		}
		return count;
	}

	private static Boolean[][] parseGrid(List<String> list) {
		return list.stream()
				.map((String s) -> s.chars().mapToObj((int i) -> i == '#' ? true : false).toArray(Boolean[]::new))
				.toArray(Boolean[][]::new);
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day18_input.txt"));
		System.out.println("Part One = " + solve(s, false));
		System.out.println("Part One = " + solve(s, true));
	}
}
