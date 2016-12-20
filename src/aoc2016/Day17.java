package aoc2016;

import java.security.MessageDigest;
import java.util.*;

class Location extends java.awt.Point {
  final String path;

  public Location(int x, int y, String path) {
    super(x, y);
    this.path = path;
  }

  boolean isValid() {
    return x >= 0 && y >= 0 && x <= 3 && y <= 3;
  }
}

class Day17 {
  private final static char[] direction = {'U', 'D', 'L', 'R'};
  private final static int[] x_off = {0, 0, -1, 1};
  private final static int[] y_off = {-1, 1, 0, 0};

  private static String shortest = "";
  private static String longest = "";

  private static void solve(String start) throws java.security.NoSuchAlgorithmException {
    Queue<Location> queue = new ArrayDeque<>();
    queue.add(new Location(0, 0, start));

    while (!queue.isEmpty()) {
      Location curr = queue.poll();
      String pathHash = hashPath(curr.path);
      for (int i = 0; i < pathHash.length(); i++) {
        if (pathHash.charAt(i) >= 'B' && pathHash.charAt(i) <= 'F') {
          Location neuPath = new Location(curr.x + x_off[i], curr.y + y_off[i], curr.path + direction[i]);
          if (neuPath.isValid()) {
            if (neuPath.x == 3 && neuPath.y == 3) {
              longest = neuPath.path.substring(start.length());
              if (shortest.length() == 0)
                shortest = longest;
            } else
              queue.add(neuPath);
          }
        }
      }
    }
  }

  private static String hashPath(String path) throws java.security.NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("md5");
    byte[] hashBytes = digest.digest(path.getBytes());
    return javax.xml.bind.DatatypeConverter.printHexBinary(hashBytes).substring(0, 4);
  }

  public static void main(String[] args) throws java.security.NoSuchAlgorithmException {
    Day17.solve("njfxhljp");
    System.out.println("Part One = " + Day17.shortest);
    System.out.println("Part Two = " + Day17.longest.length());
  }
}
