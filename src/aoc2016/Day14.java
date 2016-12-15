package aoc2016;

import java.security.*;
import java.util.*;
import java.util.regex.*;

public class Day14 {
  static int index = 0;
  static int number = 0;

  public static long solve(String salt, int strech) throws NoSuchAlgorithmException {
    index = 0;
    number = 0;
    int cnt = 0;
    List<String> hashes = new ArrayList<>();
    Map<String, Set<Integer>> validHashes = new HashMap<>();
    while (cnt < 64) {
      while (hashes.size() <= 1001) {
        String next = genNext(salt, strech);
        hashes.add(next);
        Matcher m2 = Pattern.compile("(.)\\1\\1\\1\\1").matcher(next);
        if (m2.find())
          validHashes.computeIfAbsent(m2.group(1), s -> new HashSet<>()).add(index - 1);
      }
      String curr = hashes.remove(0);
      Matcher m = Pattern.compile("(.)\\1\\1").matcher(curr);
      if (m.find() && validHashes.getOrDefault(m.group(1), new HashSet<>()).stream().anyMatch(x -> x < number + 1000 && x > number)) {
        System.out.println(String.format("\t%05d|%s valid", number, curr));
        cnt++;
      }
      number++;
    }
    return number - 1;
  }

  public static String genNext(String salt, int strech) throws NoSuchAlgorithmException {
    String result = salt + index++;
    for (int i = 0; i <= strech; i++) {
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
