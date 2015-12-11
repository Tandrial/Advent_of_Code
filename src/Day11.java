import java.util.regex.Pattern;

public class Day11 {

	public static String partOne(String s) {
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
			if (Pattern.matches(".*(i|o|l).*", s))
				continue;
			if (!Pattern.matches(".*(.)\\1.*(.)\\2.*", s))
				continue;
			break;
		}
		return s;
	}

	private static String genNext(String s) {
		char[] chars = s.toCharArray();
		chars[chars.length - 1]++;
		for (int i = chars.length - 1; i >= 0; i--) {
			if (chars[i] > 'z') {
				chars[i] = 'a';
				if (i != 0)
					chars[i - 1]++;
			} else
				break;
		}
		return new String(chars);
	}

	public static void main(String[] args) {
		String s = "hepxcrrq";
		s = partOne(s);
		System.out.println("Part One = " + s);
		System.out.println("Part Two = " + partOne(s));
	}
}
