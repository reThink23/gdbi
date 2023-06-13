package ueb9;

import java.io.IOException;
import java.util.ArrayList;

import ueb3.InvalidSequenceException;
import ueb3.Sequence;

public class Alignment {
	private String s1;
	private String s2;
	private int[][] d;

	public Alignment(Sequence seq1, Sequence seq2) {
		this.s1 = "-" + seq1.getSequence();
		this.s2 = "-" + seq2.getSequence();
		this.d = new int[s1.length()][s2.length()];
	}
	public Alignment(String seq1, String seq2) {
		this.s1 = "-" + seq1;
		this.s2 = "-" + seq2;
		this.d = new int[s1.length()][s2.length()];
	}

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

	public void buildMatrix() {
		for (int i = 0; i < s1.length(); i++) {
			d[i][0] = i;
		}

		for (int j = 0; j < s2.length(); j++) {
			d[0][j] = j;
		}

		for (int i = 1; i < s1.length(); i++) {
			for (int j = 1; j < s2.length(); j++) {
				int smallest = min(
					d[i-1][j-1]+cost(s1.charAt(i), s2.charAt(j)),
					d[i][j-1]+1, 
					d[i-1][j]+1
				);
				d[i][j] = smallest;
			}
		}
	}

	public String[] backtracking() {
		int i = s1.length()-1;
		int j = s2.length()-1;
		String seq1_ = "";
		String seq2_ = "";

		while (i > 0 && j > 0) {
			if (d[i][j] == d[i-1][j-1] + cost(s1.charAt(i), s2.charAt(j))) {
				seq1_ = s1.charAt(i--) + seq1_;
				seq2_ = s2.charAt(j--) + seq2_;
			} else if (d[i][j] == d[i-1][j] + 1) {
				seq1_ = s1.charAt(i--) + seq1_;
				seq2_ = "-" + seq2_;
			} else {
				seq1_ = "-" + seq1_;
				seq2_ = s2.charAt(j--) + seq2_;
			}
		}

		while (i > 0) {
			seq1_ = s1.charAt(i--) + seq1_;
			seq2_ = "-" + seq2_;
		}

		while (j > 0) {
			seq1_ = "-" + seq1_;
			seq2_ = s2.charAt(j--) + seq2_;
		}

		return new String[] {seq1_, seq2_};
	}

	public void printMatrix() {
		System.out.print("  ");
		for (int i = 0; i < s2.length(); i++) { System.out.print(s2.charAt(i) + " "); }
		System.out.println();
		for (int i = 0; i < s1.length(); i++) {
			System.out.print(s1.charAt(i) + " ");
			for (int j = 0; j < s2.length(); j++) {
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException, InvalidSequenceException {
		if (args.length != 1) {
			System.out.println("Usage: java Alignment <fasta file>");
			System.exit(1);
		}
		ArrayList<Sequence> sequences = Sequence.readFastA(args[0]);
		Sequence seq1 = sequences.get(0);
		Sequence seq2 = sequences.get(1);
		// String s1 = "AAGT";
		// String s2 = "ACAT";
		
		Alignment align = new Alignment(seq1, seq2);
		align.buildMatrix();
		// align.printMatrix();
		String[] alignments = align.backtracking();
		System.out.println(alignments[0] + "\n" + alignments[1]);
	}
}