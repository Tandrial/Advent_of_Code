package aoc2016.jav;


import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Day24 {
    private static class Node extends Point {
        final int num;
        final int step;

        public Node(int x, int y, int num, int step) {
            super(x, y);
            this.num = num;
            this.step = step;
        }

        @Override
        public boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        public String toString() {
            return num + " " + super.toString();
        }
    }


    public static int solve(int[][] maze, List<Node> goals, boolean part2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < goals.size(); i++)
            sb.append(i);

        List<String> permutations = new ArrayList<>();
        permutation("", sb.toString(), permutations);
        int fewestSteps = Integer.MAX_VALUE;
        int checked = 0;

        Map<Integer, Integer> results = new HashMap<>();

        outer:
        for (String curr_permu : permutations) {
            checked++;
            if (checked % 250 == 0)
                System.out.println(String.format("%d/%d (%d prozent)", checked, permutations.size(), (int)(checked * 100.0f / permutations.size())));
            Node start = goals.get(0);
            Node end = goals.get(curr_permu.charAt(0) - '0');
            int steps = stepsFromTo(maze, start, end);

            for (char c : curr_permu.substring(1).toCharArray()) {
                start = end;
                end = goals.get(c - '0');
                steps += stepsFromTo(maze, start, end);
            }
            if (!results.containsKey(end.num) || results.get(end.num) > steps) {
                results.put(end.num, steps);
            }
            if (steps < fewestSteps) {
                fewestSteps = steps;
            }
        }
        if (part2) {
            int fewestPart2 = Integer.MAX_VALUE;
            for (Map.Entry<Integer, Integer> e : results.entrySet()) {
                int steps = stepsFromTo(maze, goals.get(e.getKey()), goals.get(0));
                if (e.getValue() + steps < fewestPart2)
                    fewestPart2 = e.getValue() + steps;
            }
            return fewestPart2;
        }

        return fewestSteps;
    }

    static Map<String, Integer> shortesWayLookUp = new HashMap<>();

    private final static int[] x_off = {0, 0, -1, 1};
    private final static int[] y_off = {-1, 1, 0, 0};

    public static int stepsFromTo(int[][] maze, Node start, Node end) {
        if (shortesWayLookUp.containsKey(start.num + ";" + end.num))
            return shortesWayLookUp.get(start.num + ";" + end.num);
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr.equals(end)) {
                shortesWayLookUp.put(start.num + ";" + end.num, curr.step);
                shortesWayLookUp.put(end.num + ";" + start.num, curr.step);
                return curr.step;
            }
            visited.add(curr);
            for (int i = 0; i < x_off.length; i++) {
                if (maze[curr.x + x_off[i]][curr.y + y_off[i]] != -1) {
                    Node neu = new Node(curr.x + x_off[i], curr.y + y_off[i], -100, curr.step + 1);
                    if (!visited.contains(neu))
                        queue.add(neu);
                }
            }
        }

        return -1;
    }


    private static void permutation(String prefix, String str, List<String> permu) {
        int n = str.length();
        if (n == 0) permu.add(prefix);
        else
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), permu);
    }

    public static int[][] parse(List<String> m, List<Node> goals) {
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
        long start = System.currentTimeMillis();
        System.out.println("Part One = " + solve(maze, goals, false));
        long part1 = System.currentTimeMillis();
        System.out.println("Part Two = " + solve(maze, goals, true));
        System.out.println("p1 = " + (part1 - start) + " p2 = " + (System.currentTimeMillis() - part1));
    }
}


