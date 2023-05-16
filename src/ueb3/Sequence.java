package ueb3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// UML-Klasse: Sequence, abstrakte Klasse
public abstract class Sequence {
    
    // Attribute aus UML-Diagramm nur in Sequence, da Vererbung
    protected String sequence;
    protected int length;
    protected int[] phredScores;

    // neue Methode um ein Sequence-Objekt zu erstellen (in readFastA) -> sauberer Code
    public static Sequence createSeqObject(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invarianten (Erlaubte Zeichen)
        if (SeqUtils.isDNA(sequence)) return new DNASequence(sequence);
        else if (SeqUtils.isRNA(sequence)) return new RNASequence(sequence);
        else if (SeqUtils.isProtein(sequence)) return new ProteinSequence(sequence);
        else throw new InvalidSequenceException("Given Sequence is not an DNA, RNA or Protein sequence");
    }

    // UML-Methode: readFASTA, Änderung: Rückgabe ArrayList<Sequence> anstatt Sequence[] (da einfachere Handhabung)
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
            // Klassenverträge - Invariante (Großbuchstaben) / abspeichern in Großbuchstaben für Einheitlichkeit
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

    private static int[] parsePhredScore(String scoreString, int offset) {
        int[] phredScores = new int[scoreString.length()];
        for (int i = 0; i < scoreString.length(); i++) {
            phredScores[i] = scoreString.charAt(i) - offset;
        }
        return phredScores;
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

    public static int avgPhredScoreAt(ArrayList<Sequence> sequences, int i) {
        int sum = 0;
        int count = 0;
        for (Sequence seq : sequences) {
            try {
                sum += seq.getPhredScoreAt(i);
                count++;
            } catch (Exception e) {
                // ignore
            }
        }
        return (count > 0) ? sum / count : -1;
    }

    public static int getExactReads(Sequence genome, ArrayList<Sequence> reads) {
        boolean found = true;
        for (int k=0; k < reads.size(); k++) {
            int genomeLength = genome.length();
            int readLength = reads.get(k).length();
            for (int i = 0; i <= genomeLength - readLength; i++) {
                found = true;
                for (int j = 0; j < reads.get(k).length(); j++) {
                    if (reads.get(k).elementAt(j) != genome.elementAt(i+j)) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i;
                }
            }
        }
        return -1;
    }

    // UML-Methode: getSequence, getLength
    public String getSequence() { return sequence;}
    public int length() { return length;}

    private int[] getPhredScores() { return phredScores;}

    public int getPhredScoreAt(int i) {
        if (phredScores == null) throw new NullPointerException("Attribute phredScores is not set");
        if (i < 0 || i >= phredScores.length) throw new IndexOutOfBoundsException("Given index is out of bounds");
        return phredScores[i];
    }

    public void setPhredScores(int[] phredScores) { 
        if (phredScores.length != this.sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        this.phredScores = phredScores; 
    }

    // UML-Methode: setSequence, abstrakte Methode
    public abstract boolean setSequence(String sequence);

    // UML-Methode: getSubSeq ("get" entfernt, um Getter klarer zu trennen), abstrakte Methode
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    // UML-Methode: getElementAt ("get" entfernt, um Getter klarer zu trennen)
    public char elementAt(int index) {
        return sequence.charAt(index);
    }

    
    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Sequence)) return false;
        Sequence s = (Sequence) o;
        return s.sequence.equals(this.sequence);
    }

    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    public abstract String toString();
}
