package ueb3;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException, InvalidSequenceException {
		if (args.length != 1) {
			System.out.println("Usage: java Main <path to fasta file>");
			System.exit(1);
		}

		// Testen der Invarianten
		try { new DNASequence("ATTTGUCT");} catch (InvalidSequenceException e) { System.out.println("Is DNA? " + e.getMessage());}
		try { new RNASequence("ATGCCCAUUU");} catch (InvalidSequenceException e) { System.out.println("Is RNA? " + e.getMessage());}
		try { new ProteinSequence("ABCFFFDJAYYTTAADDLM");} catch (InvalidSequenceException e) { System.out.println("Is Protein? " + e.getMessage());}
		try { DNASequence d = new DNASequence("ATCG"); d.setSequence("ATTTCGAF");} catch (InvalidSequenceException e) { System.out.println("Can't set DNA sequence " + e.getMessage());}
		try { RNASequence r = new RNASequence("AUCG"); r.setSequence("ATTTCGA");} catch (InvalidSequenceException e) { System.out.println("Can't set RNA sequence " + e.getMessage());}
		try { ProteinSequence d = new ProteinSequence("AAAFFFTKL"); d.setSequence("AAAKLJJJJJ");} catch (InvalidSequenceException e) { System.out.println("Can't set protein sequence " + e.getMessage());}

		ArrayList<Sequence> sequences = Sequence.readFastA(args[0]);
		for (Sequence seq: sequences) {
			if (seq instanceof ProteinSequence) {
				System.out.println("Protein: " + seq.subSeq(0, 60));
			} else {
				System.out.println("Nucleotide: " + ((NucleotideSequence) seq.subSeq(0, 60)).reverseComplement());
			}
		}
	}
}
