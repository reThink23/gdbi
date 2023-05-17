package ueb5;

import java.io.IOException;
import java.util.ArrayList;

import ueb3.InvalidSequenceException;
import ueb3.Sequence;

public class Main {
	public static void main(String[] args) throws InvalidSequenceException, IOException {
		if(args.length < 2) {
			System.out.println("Usage: java Main <path to genom fasta file> <path to reads fasta file>");
			System.exit(1);
		}
		Sequence genome = Sequence.readFastA(args[0]).get(0);
		ArrayList<Sequence> reads = Sequence.readFastA(args[1]);
		ArrayList<Integer> firstOccurences = Sequence.getExactReads(genome, reads);
		System.out.println(firstOccurences);

		
		System.out.println("Reads found in genome " + genome + ":");
		int i = 0;
		for (Integer firstOccurence : firstOccurences) {
			// System.out.println("Read " + i + " starts at position " + firstOccurence);
			// System.out.println("Read " + reads.get(i) + " starts at position " + firstOccurence + " in genome " + genome + "\n");
			// System.out.println("Read " + reads.get(i) + " at position " + firstOccurence + "to " + (firstOccurence + reads.get(i).length()) + " in genome: " + genome.subSeq(firstOccurence, firstOccurence + reads.get(i).length()));
			System.out.println("Read " + reads.get(i) + " at position " + firstOccurence + " to " + (firstOccurence + reads.get(i).length()) + " in genome");

			i++;
		}
	}
}
