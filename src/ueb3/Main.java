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
				System.out.println("Protein: " + seq.subSeq(0, 60));
			} else {
				System.out.println("Nucleotide: " + ((NucleotideSequence) seq.subSeq(0, 60)).reverseComplement());
			}
		}
	}
}
