public class Day10 {

	public static int partOne(String s) {
		for (int i = 0; i < 40; i++)
			s = doIter(s);
		return s.length();
	}

	public static int partTwo(String s) {
		for (int i = 0; i < 50; i++)
			s = doIter(s);
		return s.length();
	}

	public static String doIter(String s) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length();) {
			int count = 1;
			for (int j = i + 1; j < s.length(); j++) {
				if (s.charAt(i) == s.charAt(j))
					count++;
				else
					break;
			}
			sb.append(count);
			sb.append(s.charAt(i));
			i += count;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String s = "3113322113";
		System.out.println("Part One = " + partOne(s));
		System.out.println("Part Two = " + partTwo(s));
	}
}
