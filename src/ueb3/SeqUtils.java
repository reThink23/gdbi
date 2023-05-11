package ueb3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SeqUtils {
	public static enum type {
		DNA, RNA, PROTEIN
	}

	public static boolean is(type type, String sequence) {
		// Pattern pat;

		switch (type) {
			case DNA:
				// pat = Pattern.compile("^[ATCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
				return isDNA(sequence);
			case RNA:
				// pat = Pattern.compile("^[AUCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
				return isRNA(sequence);
			case PROTEIN:
				// pat = Pattern.compile("^[ACDEFGHIKLMNPQRSTVWYZXBU\\\\-\\\\*]+$",
				// Pattern.CASE_INSENSITIVE);
				return isProtein(sequence);
			default:
				return false;
		}
		// Matcher m = pat.matcher(sequence);
		// return m.find();
	}

	// neue Methoden isDNA, isRNA, isProtein um zu checken ob string nur erlaubte
	// Zeichen enthält
	// (in Konstruktor der Subklassen & createSeqObject) -> Redundanzvermeidung
	public static boolean isDNA(String sequence) {
		if (sequence == null || sequence.equals(""))
			return false;
		Pattern pat = Pattern.compile("^[ATCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
		Matcher m = pat.matcher(sequence);
		return m.find();
	}

	public static boolean isRNA(String sequence) {
		if (sequence == null || sequence.equals(""))
			return false;
		Pattern pat = Pattern.compile("^[AUCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
		Matcher m = pat.matcher(sequence);
		return m.find();
	}

	public static boolean isProtein(String sequence) {
		if (sequence == null || sequence.equals(""))
			return false;
		Pattern pat = Pattern.compile("^[ACDEFGHIKLMNPQRSTVWYZXBU\\\\-\\\\*]+$", Pattern.CASE_INSENSITIVE);
		Matcher m = pat.matcher(sequence);
		return m.find();
	}
}
