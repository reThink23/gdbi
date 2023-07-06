package ueb12;

import java.io.IOException;
import java.util.ArrayList;

import ueb3.DNASequence;
import ueb3.InvalidSequenceException;
import ueb3.Sequence;

public class PWM {

	private static String padding(String s, int length, String padChar, char direction) {
		int pad;
		switch (direction) {
			case 'c':
				pad = (length - s.length()) / 2;
				break;
			case 'r':
			case 'l':
			default:
				pad = length - s.length();
				break;
		}

		String padding = "";
		for (int i = 0; i < pad; i++) {
			padding += padChar;
		}
		String res;
		switch (direction) {
			case 'c':
				res = padding + s + padding;
				break;
				case 'l':
				res = padding + s;
				break;
			case 'r':
			default:
				res = s + padding;
				break;
		}
		return res.length() < length ? res + padChar : res;
	}
	private static String padding(String s, int length, String padChar) {
		return padding(s, length, padChar, 'c');
	}
	private static String padding(String s, int length) {
		return padding(s, length, " ", 'c');
	}

	private static double round(double d, int places) {
		double factor = Math.pow(10, places);
		return Math.round(d * factor) / factor;
	}

	private static String truncate(String s, int length) {
		if (s.length() > length) {
			return s.substring(0, length-1) + "_";
		}
		return s;
	}

	public static double[][] pwmEstimate(ArrayList<Sequence> sequences) throws InvalidSequenceException {
		for (Sequence s : sequences) {
			if (!(s instanceof DNASequence)) {
				throw new InvalidSequenceException("Sequence is not a DNA sequence.");
			}
		}
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
				}
			}
			
		}
		return pwm;
	}

	public static String printPWM(double[][] pwm) {
		int padLen = 7;
		String s = "  |";
		for (int j = 1; j <= pwm[0].length; j++) { s += padding(String.valueOf(j), padLen) + "|"; }
		s += "\n" + padding("", pwm[0].length * (padLen+1) + 3, "-");
		for (int i = 0; i < pwm.length; i++) {
			switch (i) {
				case 0: s += "\nA |"; break;
				case 1: s += "\nG |"; break;
				case 2: s += "\nC |"; break;
				case 3: s += "\nT |"; break;
			}
			for (int j = 0; j < pwm[i].length; j++) {
				s += padding(truncate(String.valueOf(round(pwm[i][j], 2)), 4), padLen) + "|";
			}
			// s += "\n";
		}
		return s;
	}

	public static void main(String[] args) throws IOException, InvalidSequenceException {
		if (args.length != 1) {
			System.out.println("Usage: java PWM <path to fasta file>");
			System.exit(1);
		}

		ArrayList<Sequence> sequences = Sequence.readFastA(args[0]);
		double[][] pwm = pwmEstimate(sequences);
		System.out.println(printPWM(pwm));


	}
}
