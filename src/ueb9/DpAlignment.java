package ueb9;

public class DpAlignment {

	private static int min(int a, int b, int c) {
		return Math.min(Math.min(a,b),c);
	}

	private static int cost(char a, char b) {
		if (a == b) {
			return 0;
		} else {
			return 1;
		}
	}

	private static String findAlignment(String seq1, String seq2) {
		int[][] d = new int[seq1.length()][seq2.length()];

		for (int i = 0; i < seq1.length(); i++) {
			d[i][0] = i;
		}

		for (int j = 0; j < seq2.length(); j++) {
			d[0][j] = j;
		}

		for (int i = 1; i < seq1.length(); i++) {
			for (int j = 1; j < seq2.length(); j++) {
				d[i][j] = min(
					d[i][j-1], 
					d[i-1][j], 
					d[i-1][j-1]+cost(seq1.charAt(i), seq2.charAt(j))
				);
			}
		}

		int i = seq1.length()-1;
		int j = seq2.length()-1;
		String seq1_ = "";
		String seq2_ = "";

		while (i > 0 && j > 0) {
			if (d[i][j] == d[i-1][j-1] + cost(seq1.charAt(i), seq2.charAt(j))) {
				seq1_ = seq1.charAt(i--) + seq1_;
				seq2_ = seq2.charAt(j--) + seq2_;
			}

			if (d[i][j] == d[i-1][j] + 1) {
				seq1_ = seq1.charAt(i--) + seq1_;
				seq2_ = "-" + seq2_;
			} else {
				seq1_ = "-" + seq1_;
				seq2_ = seq2.charAt(j--) + seq2_;
			}
		}

		while (i > 0) {
			seq1_ = seq1.charAt(i--) + seq1_;
			seq2_ = "-" + seq2_;
		}

		while (j > 0) {
			seq1_ = "-" + seq1_;
			seq2_ = seq2.charAt(j--) + seq2_;
		}

		return seq1_ + "\n" + seq2_;
	}

	public static void main(String[] args) {
		String s1 = "AAGT";
		String s2 = "ACAT";
		System.out.println(findAlignment(s1, s2));
	}
}