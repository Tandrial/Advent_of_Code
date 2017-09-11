package aoc2015.jav;

import java.util.regex.Pattern;

class Day11 {

  private static String solve(String s) {
    while (true) {
      s = genNext(s);
      boolean check = false;
      for (int i = 0; i < s.length() - 2; i++) {
        if (s.charAt(i) + 1 == s.charAt(i + 1) && s.charAt(i) + 2 == s.charAt(i + 2)) {
          check = true;
          break;
        }
      }
      if (!check)
        continue;
      if (Pattern.matches(".*[iol].*", s))
        continue;
      if (!Pattern.matches(".*(.)\\1.*(.)\\2.*", s))
        continue;
      break;
    }
    return s;
  }

  private static String genNext(String s) {
    char[] chars = s.toCharArray();
    for (int i = chars.length - 1; i >= 0; i--) {
      if (chars[i] == 'z') {
        chars[i] = 'a';
      } else {
        chars[i]++;
        break;
      }
    }
    return new String(chars);
  }

  public static void main(String[] args) {
    String s = "hepxcrrq";
    s = solve(s);
    System.out.println("Part One = " + s);
    System.out.println("Part Two = " + solve(s));
  }
}
