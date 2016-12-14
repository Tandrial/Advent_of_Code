package aoc2016;

import java.security.*;
import java.util.*;
import java.util.regex.*;

public class Day14 {
  static int index = 0;

  public static long solve(String salt, int strech) throws NoSuchAlgorithmException {
    index = 0;
    int number = 0;
    int cnt = 0;
    List<String> hashes = new ArrayList<>();
    while (cnt < 64) {
      while (hashes.size() <= 1001)
        hashes.add(genNext(salt, strech));
      String curr = hashes.remove(0);
      Matcher m = Pattern.compile("(.)\\1\\1").matcher(curr);
      if (m.find()) {
        for (int idx = 0; idx < 1000; idx++) {
          Matcher m2 = Pattern.compile(String.format("(%s)\\1\\1\\1\\1", m.group(1))).matcher(hashes.get(idx));
          if (m2.find()) {
            System.out.println(String.format("\t%05d|%s valid because %03d|%s", number, curr, idx, hashes.get(idx)));
            cnt++;
            break;
          }
        }
      }
      number++;
    }
    return number - 1;
  }

  public static String genNext(String salt, int cnt) throws NoSuchAlgorithmException {
    String result = salt + index++;
    for (int i = 0; i <= cnt; i++) {
      MessageDigest digest = MessageDigest.getInstance("md5");
      byte[] hashBytes = digest.digest(result.getBytes());
      result = javax.xml.bind.DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
    }
    return result;
  }

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String s = "yjdafjpo";
    System.out.println("Part One = " + solve(s, 0));
    System.out.println("Part Two = " + solve(s, 2016));
  }
}
