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

    public static enum type {DNA, RNA, PROTEIN}

    public boolean is(type type) {
        // Pattern pat;

        switch (type) {
            case DNA:
                // pat = Pattern.compile("^[ATCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
                return isDNA(this.sequence);
                case RNA:
                // pat = Pattern.compile("^[AUCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
                return isRNA(this.sequence);
                case PROTEIN:
                // pat = Pattern.compile("^[ACDEFGHIKLMNPQRSTVWYZXBU\\\\-\\\\*]+$", Pattern.CASE_INSENSITIVE);
                return isProtein(this.sequence);
            default:
                return false;
        }
        // Matcher m = pat.matcher(sequence);
        // return m.find();
    }


    // neue Methoden isDNA, isRNA, isProtein um zu checken ob string nur erlaubte Zeichen enthält 
    // (in Konstruktor der Subklassen & createSeqObject) -> Redundanzvermeidung
    public static boolean isDNA(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        Pattern pat = Pattern.compile("^[ATCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(sequence);
        return m.find();
    }

    public static boolean isRNA(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        Pattern pat = Pattern.compile("^[AUCG\\\\-]+$", Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(sequence);
        return m.find();
    }

    public static boolean isProtein(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        Pattern pat = Pattern.compile("^[ACDEFGHIKLMNPQRSTVWYZXBU\\\\-\\\\*]+$", Pattern.CASE_INSENSITIVE);
        Matcher m = pat.matcher(sequence);
        return m.find();
    }

    // neue Methode um ein Sequence-Objekt zu erstellen (in readFastA) -> sauberer Code
    private static Sequence createSeqObject(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invarianten (Erlaubte Zeichen)
        if (isDNA(sequence)) return new DNASequence(sequence);
        else if (isRNA(sequence)) return new RNASequence(sequence);
        else if (isProtein(sequence)) return new ProteinSequence(sequence);
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
                if (sb.toString().length() > 0) sequences.add(createSeqObject(sb.toString()));
                sb = new StringBuilder();
            } 
            // Klassenverträge - Invariante (Großbuchstaben) / abspeichern in Großbuchstaben für Einheitlichkeit
            else sb.append(line.toUpperCase());
            
            line = bf.readLine();
        }
        sequences.add(createSeqObject(sb.toString()));

        bf.close();

        return sequences;
    }

    // UML-Methode: getSequence, getLength
    public String getSequence() { return sequence;}
    public int getLength() { return length;}

    // UML-Methode: setSequence, abstrakte Methode
    public abstract boolean setSequence(String sequence);

    // UML-Methode: getSubSeq ("get" entfernt, um Getter klarer zu trennen), abstrakte Methode
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    // UML-Methode: getElementAt ("get" entfernt, um Getter klarer zu trennen)
    public char elementAt(int index) {
        return sequence.charAt(index);
    }

    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    public String toString() {
        return sequence;
    }
    
    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Sequence)) return false;
        Sequence s = (Sequence) o;
        return s.sequence.equals(this.sequence);
    }
}
