package aoc2016;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class Day02 {
  private static String solve(Point pos, char[][] keypad, List<String> xs) {
    StringBuilder password = new StringBuilder();
    for (String s : xs) {
      for (char c : s.toCharArray()) {
        Point old = new Point(pos);
        if (c == 'U') pos.x = Math.max(0, pos.x - 1);
        else if (c == 'L') pos.y = Math.max(0, pos.y - 1);
        else if (c == 'D') pos.x = Math.min(keypad.length - 1, pos.x + 1);
        else if (c == 'R') pos.y = Math.min(keypad[0].length - 1, pos.y + 1);

        if (keypad[pos.x][pos.y] == 0)
          pos = old;
      }
      password.append(keypad[pos.x][pos.y]);
    }
    return password.toString();
  }

  public static void main(String[] args) throws IOException {
    List<String> s = Files.readAllLines(Paths.get("./input/2016/Day02_input.txt"));
    char[][] keypad1 = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
    char[][] keypad2 = {{0, 0, '1', 0, 0}, {0, '2', '3', '4', 0}, {'5', '6', '7', '8', '9'}, {0, 'A', 'B', 'C', 0}, {0, 0, 'D', 0, 0}};

    System.out.println("Part One = " + solve(new Point(1, 1), keypad1, s));
    System.out.println("Part Two = " + solve(new Point(2, 0), keypad2, s));
  }
}
