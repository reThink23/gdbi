package ueb3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// UML-Klasse: Sequence, abstrakte Klasse
public abstract class Sequence {
    // Attribute aus UML-Diagramm nur in Sequence, da Vererbung
    protected String sequence;
    protected int length;

    // neue konstante Attribute für die Pattern, um Redundanzen zu vermeiden
    protected static final String DNAPATTERN = "^[ATCG\\\\-]+$";
    protected static final String RNAPATTERN = "^[AUCG\\\\-]+$";
    protected static final String PROTEINPATTERN = "^[ACDEFGHIKLMNPQRSTVWYZXBU\\\\-\\\\*]+$";

    protected static boolean testLetters(String pattern, String sequence) {
        Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(sequence);
        return m.find();
    }

    protected static Sequence createSeqObject(String sequence) throws InvalidSequenceException {
        // Klassenverträge - Invarianten
        if (testLetters(DNAPATTERN, sequence)) return new DNASequence(sequence);
        else if (testLetters(RNAPATTERN, sequence)) return new RNASequence(sequence);
        else if (testLetters(PROTEINPATTERN, sequence)) return new ProteinSequence(sequence);
        else throw new InvalidSequenceException("Given Sequence is not an DNA, RNA or Protein sequence");
    }

    public static ArrayList<Sequence> readFastA(String filePath) throws IOException, InvalidSequenceException {
        ArrayList<Sequence> sequences = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(filePath));
        
        String line = bf.readLine();
        while (line != null) {
            if (line.startsWith(";")) continue;
            if (line.startsWith(">")) {
                if (sb.toString().length() > 0) sequences.add(createSeqObject(sb.toString()));
                sb = new StringBuilder();
            } 
            else sb.append(line.toUpperCase());
            
            line = bf.readLine();
        }
        sequences.add(createSeqObject(sb.toString()));

        bf.close();

        return sequences;
    }

    public String getSequence() { return sequence;}
    public int getLength() { return length;}

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    // abstrakte Methode
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    public char elementAt(int index) {
        return sequence.charAt(index);
    }

    public String toString() {
        return sequence;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Sequence)) return false;
        Sequence s = (Sequence) o;
        return s.sequence.equals(this.sequence);
    }
}
