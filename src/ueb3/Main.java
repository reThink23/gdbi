package ueb3;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException, InvalidSequenceException {
		if (args.length != 1) {
			System.out.println("Usage: java Main <path to fasta file>");
			System.exit(1);
		}
		ArrayList<Sequence> sequences = Sequence.readFastA(args[0]);
		for (Sequence seq: sequences) {
			if (seq instanceof ProteinSequence) {
				System.out.println(seq.subSeq(0, 10));
			} else {
				if (seq instanceof DNASequence) {
					System.out.println(((DNASequence) ((DNASequence) seq.subSeq(0, 10)).reverseComplement()).transcribeToRNA());
				}
				System.out.println(((NucleotidSequence) seq.subSeq(0, 10)).reverseComplement());
			}
		}
	}
}
