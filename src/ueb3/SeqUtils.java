package ueb3;

import java.util.ArrayList;

public abstract class SeqUtils {
	public static enum type { DNA, RNA, PROTEIN }
	public static final String allowedDNAChars = "ATCG";
	public static final String allowedRNAChars = "AUCG";
	public static final String allowedProteinChars = "ACDEFGHIKLMNPQRSTVWYZXBU*";

	public static boolean is(type type, String sequence) {
		switch (type) {
			case DNA: return isDNA(sequence);
			case RNA: return isRNA(sequence);
			case PROTEIN: return isProtein(sequence);
			default: return false;
		}
	}

	public static boolean isDNA(String sequence) {
		return sequence != null && sequence.matches("(?i)^[ATCG\\\\-]+$");
	}

	public static boolean isRNA(String sequence) {
		return sequence != null && sequence.matches("(?i)^[AUCG\\\\-]+$");
	}

	public static boolean isProtein(String sequence) {
		return sequence != null && sequence.matches("(?i)^[ACDEFGHIKLMNPQRSTVWYZXBU\\\\-\\\\*]+$");
	}

	public static Sequence getLongestSeq(ArrayList<Sequence> sequences) {
		Sequence max = sequences.get(0);
		for (Sequence s : sequences) {
			if (s.length() > max.length()) max = s;
		}
		return max;
	}

	public static Sequence getShortestSeq(ArrayList<Sequence> sequences) {
		Sequence min = sequences.get(0);
		for (Sequence s : sequences) {
			if (s.length() < min.length()) min = s;
		}
		return min;
	}
}
