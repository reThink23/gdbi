package ueb4;

import ueb3.InvalidSequenceException;
import ueb3.Sequence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FastQ {
	public static ArrayList<Sequence> readFastQ(String filePath) throws IOException, InvalidSequenceException {
		ArrayList<Sequence> sequences = new ArrayList<>();
		BufferedReader bf = new BufferedReader(new FileReader(filePath));

		String line = bf.readLine();
		String seqString = null;
		int i = 0;
		while (line != null) {
			if (i % 4 == 1) {
				seqString = line;
			} else if (i % 4 == 3) {
				Sequence seq = Sequence.createSeqObject(seqString);
				seq.setPhredScores(calcPhredScore(line));
				sequences.add(seq);
			}
			line = bf.readLine();
			i++;
		}
        bf.close();

		return sequences;
	}

	private static int[] calcPhredScore(String scoreString) {
		int[] phredScores = new int[scoreString.length()];
		for (int i = 0; i < scoreString.length(); i++) {
			phredScores[i] = scoreString.charAt(i);
		}
		return phredScores;
	}

	public static Sequence qualityTrimming(Sequence seq, int m) throws InvalidSequenceException {
		int[] phredScores = seq.getPhredScores();
		int i = 0;
		while (i < phredScores.length && phredScores[i] < m) {
			i++;
		}
		int j = phredScores.length - 1;
		while (j >= 0 && phredScores[j] < m) {
			j--;
		}
		return seq.subSeq(i, j);
	}

	public static int avgPhredScoreAt(ArrayList<Sequence> sequences, int i) {
		int sum = 0;
		int count = 0;
		for (Sequence seq: sequences) {
			try {
				sum += seq.getPhredScoreAt(i);
				count++;
			} catch (Exception e) {
				// ignore
			}
		}
		return (count > 0) ? sum / count : -1;
	}

	public static void main(String[] args) throws IOException, InvalidSequenceException {
		ArrayList<Sequence> sequences = readFastQ("C:/Users/joela/IdeaProjects/gdbi/testfiles/beispiel_ueb4.fastq");
		ArrayList<Sequence> trimmedSequences = new ArrayList<>();
		
		int min = sequences.get(0).length();
		for (int i = 1; i < sequences.size(); i++) {
			if (sequences.get(i).length() < min) {
				min = sequences.get(i).length();
			}
		}
		for (int i = 0; i < min; i++) {
			System.out.println(avgPhredScoreAt(sequences, i));
		}
		for (Sequence seq: sequences) {
			// System.out.println(qualityTrimming(seq, 32));
			trimmedSequences.add(qualityTrimming(seq, 32));
		}

		for (int i = 0; i < min; i++) {
			System.out.println(avgPhredScoreAt(trimmedSequences, i));
		}
	}
}