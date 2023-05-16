package ueb5;

import java.io.IOException;
import java.util.ArrayList;

import ueb3.InvalidSequenceException;
import ueb3.Sequence;

public class Main {
	public static void main(String[] args) throws InvalidSequenceException {
		if(args.length < 2) {
			System.err.println("No file specified.");
		}else {
			try {
				Sequence genome = Sequence.readFastA(args[0]).get(0);
				ArrayList<Sequence> reads = Sequence.readFastA(args[1]);
				int firstOccurence = Sequence.getExactReads(genome, reads);
				System.out.println(firstOccurence);

				
			} catch (IOException e) {
				System.err.println("File "+args[0]+" not found or not readable.");
			}
		}
	}
}
