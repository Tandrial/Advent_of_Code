package aoc2016.jav;

import aoc2015.jav.StringHex;

import java.security.*;

class Day05 {
  private static String solve(String s, boolean partTwo) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("md5");
    char[] sb = new char[8];
    String result;
    long cnt = 0;
    int charsFound = 0;
    while (charsFound < 8) {
      do {
        byte[] inputBytes = (s + cnt++).getBytes();
        byte[] hashBytes = digest.digest(inputBytes);
        result = StringHex.bytesToHex(hashBytes);
      } while (!result.startsWith("00000"));
      if (!partTwo) {
        sb[charsFound++] = result.charAt(5);
      } else {
        if (result.charAt(5) < '8' && sb[result.charAt(5) - '0'] == 0) {
          sb[result.charAt(5) - '0'] = result.charAt(6);
          charsFound++;
        }
      }
    }
    return String.valueOf(sb);
  }

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String s = "ugkcyxxp";
    System.out.println("Part One = " + solve(s, false));
    System.out.println("Part Two = " + solve(s, true));
  }
}
