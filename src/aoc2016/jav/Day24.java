package aoc2016.jav;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Day24 {
    private static class Node extends Point {
        final int num;
        final int steps;

        public Node(int x, int y, int num, int steps) {
            super(x, y);
            this.num = num;
            this.steps = steps;
        }
    }

    private static int solve(int[][] maze, List<Node> goals, boolean part2) {
        List<String> permutations =
                permutation("", IntStream.range(1, goals.size()).mapToObj(String::valueOf).collect(Collectors.joining()));
        int fewestSteps = Integer.MAX_VALUE;

        Map<Integer, Integer> results = new HashMap<>();
        for (String permutation : permutations) {
            int steps = 0;
            for (int i = 0; i < permutation.length() - 1; i++) {
                steps += stepsFromTo(maze, goals.get(permutation.charAt(i) - '0'), goals.get(permutation.charAt(i + 1) - '0'));
            }
            Node end = goals.get(permutation.charAt(permutation.length() - 1) - '0');
            results.put(end.num, Math.min(steps, results.getOrDefault(end.num, steps)));
            fewestSteps = Math.min(fewestSteps, steps);
        }
        return part2 ? results.entrySet().stream().mapToInt(e -> e.getValue() + stepsFromTo(maze, goals.get(e.getKey()), goals.get(0))).min().getAsInt() : fewestSteps;
    }

    private static final Map<Integer, Integer> shortestWayLookUp = new HashMap<>();

    private static int stepsFromTo(int[][] maze, Node start, Node end) {
        if (shortestWayLookUp.containsKey(start.hashCode() + end.hashCode()))
            return shortestWayLookUp.get(start.hashCode() + end.hashCode());
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            visited.add(curr);
            if (curr.equals(end)) {
                shortestWayLookUp.put(start.hashCode() + end.hashCode(), curr.steps);
                return curr.steps;
            }
            Stream.of(new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0))
                    .map(p -> new Node(curr.x + p.x, curr.y + p.y, -100, curr.steps + 1))
                    .filter(c -> maze[c.x][c.y] != -1 && !visited.contains(c) && !queue.contains(c))
                    .forEach(queue::add);
        }
        return -1;
    }


    private static List<String> permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) return Collections.singletonList("0" + prefix);
        else {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < n; i++)
                result.addAll(permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n)));
            return result;
        }
    }

    private static int[][] parse(List<String> m, List<Node> goals) {
        int[][] maze = new int[m.size()][m.get(0).length()];

        for (int i = 0; i < maze.length; i++) {
            String curr = m.get(i);
            for (int j = 0; j < maze[i].length; j++) {
                int num = curr.charAt(j) - '0';
                maze[i][j] = curr.charAt(j) == '#' ? -1 : num;

                if (num >= 0 && num <= 9)
                    goals.add(new Node(i, j, num, 0));
            }
        }
        return maze;
    }

    public static void main(String[] args) throws IOException {
        List<String> s = Files.readAllLines(Paths.get("./input/2016/Day24_input.txt"));
        List<Node> goals = new ArrayList<>();
        int[][] maze = parse(s, goals);
        goals.sort(Comparator.comparingInt(n -> n.num));
        System.out.println("Part One = " + solve(maze, goals, false));
        System.out.println("Part Two = " + solve(maze, goals, true));
    }
}
