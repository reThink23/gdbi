package ueb12;

import java.io.IOException;
import java.util.ArrayList;

import ueb3.InvalidSequenceException;
import ueb3.Sequence;

public class Main {

	private static String padding(String s, int length, String padChar) {
		int pad = (length - s.length()) / 2;
		String padding = "";
		for (int i = 0; i < pad; i++) {
			padding += padChar;
		}
		return padding + s + padding;
	}

	private static String truncate(String s, int length) {
		if (s.length() > length) {
			return s.substring(0, length);
		}
		return s;
	}

	public static double[][] pwmEstimate(ArrayList<Sequence> sequences) {
		int len = sequences.get(0).length();
		int numSeq = sequences.size();
		// int[][] count = new int[4][len];
		double[][] pwm = new double[4][len];
		double addVal = 1.0/numSeq;

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < numSeq; j++) {
				char nucleotide = sequences.get(j).elementAt(i);
				switch (nucleotide) {
					case 'A': pwm[0][i]+= addVal; break;
					case 'G': pwm[1][i]+= addVal; break;
					case 'C': pwm[2][i]+= addVal; break;
					case 'T': pwm[3][i]+= addVal; break;
					// case 'A': pwm[0][i]++; break;
					// case 'G': pwm[1][i]++; break;
					// case 'C': pwm[2][i]++; break;
					// case 'T': pwm[3][i]++; break;
				}
			}
			
		}
		return pwm;
	}

	public static String printPWM(double[][] pwm) {
		int padding = 5;
		String s = "  |";
		for (int j = 0; j < pwm[0].length; j++) { s += padding(String.valueOf(j), 5, " ") + "|"; }
		s += "\n" + padding("", pwm[0].length * (padding+1) + 5, "-");
		for (int i = 0; i < pwm.length; i++) {
			switch (i) {
				case 0: s += "\nA |"; break;
				case 1: s += "\nG |"; break;
				case 2: s += "\nC |"; break;
				case 3: s += "\nT |"; break;
			}
			for (int j = 0; j < pwm[i].length; j++) {
				s += padding(truncate(String.valueOf(pwm[i][j]), 3), 5, " ") + "|";
			}
			// s += "\n";
		}
		return s;
	}

	public static void main(String[] args) throws IOException, InvalidSequenceException {
		if (args.length != 1) {
			System.out.println("Usage: java Main <path to fasta file>");
			System.exit(1);
		}

		ArrayList<Sequence> sequences = Sequence.readFastA(args[0]);
		double[][] pwm = pwmEstimate(sequences);
		System.out.println(printPWM(pwm));
		


	}
}
