package ueb4;

import ueb3.InvalidSequenceException;
import ueb3.SeqUtils;
import ueb3.Sequence;

import java.io.IOException;
import java.util.ArrayList;

public class FastQ {

	public static void main(String[] args) throws IOException, InvalidSequenceException {
		if (args.length != 1) {
			System.out.println("Usage: java FastQ <path to fastq file>");
			System.exit(1);
		}

		ArrayList<Sequence> sequences = Sequence.readFastQ(args[0]);
		ArrayList<Sequence> trimmedSequences = new ArrayList<>();
		
		int max = sequences.get(0).length();
		System.out.println("Avg Phred Score:");
		for (int i = 0; i < max; i++) {
			System.out.println(Sequence.avgPhredScoreAt(sequences, i));
		}

		for (Sequence seq: sequences) {
			Sequence trimmedSeq = Sequence.qualityTrimming(seq, 70);
			trimmedSequences.add(trimmedSeq);
		}
		
		System.out.println("Trimmed Avg Phred Score:");
		int maxTrimmed = SeqUtils.getShortestSeq(trimmedSequences).length();
		for (int i = 0; i < maxTrimmed; i++) {
			System.out.println(Sequence.avgPhredScoreAt(trimmedSequences, i));
		}
	}
}