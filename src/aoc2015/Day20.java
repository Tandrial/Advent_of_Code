package aoc2015;

class Day20 {
  private static int partOne(int min) {
    int[] houses = new int[min / 10];

    for (int i = 1; i < houses.length; i++)
      for (int e = i; e < houses.length; e += i) {
        houses[e] += i * 10;
        if (houses[i] >= min)
          return i;
      }
    return -1;
  }

  private static int partTwo(int min) {
    int[] houses = new int[min / 10];

    for (int i = 1; i < houses.length; i++)
      for (int e = i, cnt = 0; e < houses.length && cnt < 50; cnt++, e += i) {
        houses[e] += i * 11;
        if (houses[i] >= min)
          return i;
      }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(partOne(36000000));
    System.out.println(partTwo(36000000));
  }
}
