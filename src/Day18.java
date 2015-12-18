import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;

public class Day18 {

	public static long partOne(List<String> s) {
		Boolean[][] grid = parseGrid(s);
		for (int i = 0; i < 100; i++) {
			grid = nextGen(grid);
		}
		return count(grid);
	}

	public static long partTwo(List<String> s) {
		Boolean[][] grid = parseGrid(s);
		for (int i = 0; i < 100; i++) {
			grid = forceLights(grid);
			grid = nextGen(grid);
		}
		grid = forceLights(grid);
		return count(grid);
	}

	private static long count(Boolean[][] grid) {
		return Arrays.stream(grid).mapToLong(new ToLongFunction<Boolean[]>() {
			@Override
			public long applyAsLong(Boolean[] value) {
				return Arrays.stream(value).filter(v -> v).count();
			}
		}).sum();
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
		for (int i = 0; i < next.length; i++) {
			next[i] = grid[i].clone();
		}

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
		for (int x_off = -1; x_off <= 1; x_off++) {
			for (int y_off = -1; y_off <= 1; y_off++) {
				if (x_off == 0 && y_off == 0)
					continue;
				if (x + x_off < 0 || x + x_off >= grid.length)
					continue;
				if (y + y_off < 0 || y + y_off >= grid.length)
					continue;
				if (grid[x + x_off][y + y_off])
					count++;
			}
		}
		return count;
	}

	private static Boolean[][] parseGrid(List<String> list) {
		return list.stream().map(new Function<String, Boolean[]>() {
			@Override
			public Boolean[] apply(String t) {
				return t.chars().mapToObj(new IntFunction<Boolean>() {
					@Override
					public Boolean apply(int value) {
						return value == '#' ? true : false;
					}
				}).toArray(Boolean[]::new);
			}
		}).toArray(Boolean[][]::new);
	}

	public static void main(String[] args) throws IOException {
		List<String> s = Files.readAllLines(Paths.get("./input/Day18_input.txt"));
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part One = " + partTwo(s));
	}
}
