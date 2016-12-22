package aoc2016.jav;

import java.util.stream.Collectors;

class Day16 {

  private static String genData(String data, int size) {
    while (data.length() <= size) {
      data = new StringBuilder(data).reverse().toString();
      data += '0' + data.chars().mapToObj(c -> String.valueOf((c == '0') ? '1' : '0')).collect(Collectors.joining());
    }
    return data.substring(0, size);
  }

  private static String genChecksum(String in) {
    do {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < in.length() - 1; i += 2)
        sb.append((in.charAt(i) == in.charAt(i + 1)) ? '1' : '0');
      in = sb.toString();
    } while (in.length() % 2 == 0);

    return in;
  }

  public static void main(String[] args) {
    System.out.println("Part One = " + genChecksum(genData("11110010111001001", 272)));
    System.out.println("Part Two = " + genChecksum(genData("11110010111001001", 35651584)));
  }
}
