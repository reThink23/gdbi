package ueb3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Sequence {
    
    protected String sequence;
    protected int length;
    protected int[] phredScores;

    public static ArrayList<Sequence> readFastA(String filePath) throws IOException, InvalidSequenceException {
        ArrayList<Sequence> sequences = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(filePath));
        
        String line = bf.readLine();
        while (line != null) {
            if (line.startsWith(";")) {line = bf.readLine(); continue;}
            if (line.startsWith(">")) {
                if (sb.length() > 0) sequences.add(createSeqObject(sb.toString()));
                sb = new StringBuilder();
            } 
            else sb.append(line);
            line = bf.readLine();
        }
        sequences.add(createSeqObject(sb.toString()));
        bf.close();
        return sequences;
    }

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
                seq.setPhredScores(parsePhredScore(line, 33));
                sequences.add(seq);
            }
            line = bf.readLine();
            i++;
        }
        bf.close();
        return sequences;
    }

    public static Sequence createSeqObject(String sequence) throws InvalidSequenceException {
        if (SeqUtils.isDNA(sequence)) return new DNASequence(sequence);
        else if (SeqUtils.isRNA(sequence)) return new RNASequence(sequence);
        else if (SeqUtils.isProtein(sequence)) return new ProteinSequence(sequence);
        else throw new InvalidSequenceException("Given Sequence is not an DNA, RNA or Protein sequence");
    }
    
    public static int avgPhredScoreAt(ArrayList<Sequence> sequences, int i) {
        int sum = 0;
        int count = 0;
        for (Sequence seq : sequences) {
            try {
                sum += seq.getPhredScoreAt(i);
                count++;
            } catch (NullPointerException | IndexOutOfBoundsException e) {}
        }
        return (count > 0) ? sum / count : -1;
    }
    
    public static Sequence trimByQuality(Sequence seq, int minQuality) throws InvalidSequenceException {
        int[] phredScores = seq.getPhredScores();
        int i = 0;
        while (i < phredScores.length && phredScores[i] < minQuality) {
            i++;
        }
        int j = phredScores.length - 1;
        while (j > 0 && phredScores[j] < minQuality) {
            j--;
        }
        if (i >= j ) throw new InvalidSequenceException("Sequence is empty after quality trimming");
        return seq.subSeq(i, j);
    }

    public ArrayList<Integer> getExactReads(ArrayList<Sequence> reads) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int k = 0; k < reads.size(); k++) {
            int pos = checkRead(this, reads.get(k));
            if (pos != -1) positions.add(pos);
        }
        return positions;
    }

    public int getPhredScoreAt(int i) {
        if (phredScores == null) throw new NullPointerException("Attribute phredScores is not set");
        if (i < 0 || i >= phredScores.length) throw new IndexOutOfBoundsException("Given index is out of bounds");
        return phredScores[i];
    }
    
    public char elementAt(int index) {
        return sequence.charAt(index);
    }
    
    private static int checkRead(Sequence genome, Sequence read) {
        int genomeLength = genome.length();
        int readLength = read.length();
        for (int i = 0; i <= genomeLength - readLength; i++) {
            boolean found = true;
            for (int j = 0; j < read.length(); j++) {
                if (read.elementAt(j) != genome.elementAt(i+j)) {
                    found = false;
                    break;
                }
            }
            if (found) { return i; }
        }
        return -1;
    }

    private static int[] parsePhredScore(String scoreString, int offset) {
        int[] phredScores = new int[scoreString.length()];
        for (int i = 0; i < scoreString.length(); i++) {
            phredScores[i] = scoreString.charAt(i) - offset;
        }
        return phredScores;
    }
        

    public String getSequence() { return sequence;}
    public int length() { return length;}
    public boolean hasPhredScore() { return phredScores != null; }
    
    private int[] getPhredScores() { return phredScores;}

    public void setPhredScores(int[] phredScores) { 
        if (phredScores.length != this.sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        this.phredScores = phredScores; 
    }

    public boolean isDNA() { return this instanceof DNASequence; }
    public boolean isRNA() { return this instanceof RNASequence; }
    public boolean isProtein() { return this instanceof ProteinSequence; }

    public abstract boolean setSequence(String sequence);
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Sequence)) return false;
        Sequence s = (Sequence) o;
        return s.sequence.equals(this.sequence);
    }

    public abstract String toString();
}
