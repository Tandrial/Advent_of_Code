package aoc2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Day04 {

  private static long partOne(String s) throws NoSuchAlgorithmException {
    return solve(s, "00000");
  }

  private static long partTwo(String s) throws NoSuchAlgorithmException {
    return solve(s, "000000");
  }

  private static long solve(String s, String difficulty) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("md5");
    String result;
    long cnt = 0;
    do {
      byte[] inputBytes = (s + cnt++).getBytes();
      byte[] hashBytes = digest.digest(inputBytes);
      result = javax.xml.bind.DatatypeConverter.printHexBinary(hashBytes);
    } while (!result.startsWith(difficulty));
    return cnt - 1;
  }

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String s = "yzbqklnj";
    System.out.println("Part One = " + partOne(s));
    System.out.println("Part Two = " + partTwo(s));
  }
}
