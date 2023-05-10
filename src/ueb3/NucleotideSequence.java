package ueb3;

// Klasse nach Korrektur hinzugefügt um Redundanz zu vermeiden, abstrakte Klasse
public abstract class NucleotideSequence extends Sequence {

    // UML-Methode: getReverseComplement, getSubSeq ("get" entfernt, um Getter klarer zu trennen),
    // abstrakte Methoden
    public abstract NucleotideSequence reverseComplement() throws InvalidSequenceException;
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    protected void reversePhredScore() {
        if (this.phredScores != null) {
            int[] phredScores = new int[this.phredScores.length];
            for (int i = 0; i < phredScores.length; i++) {
                phredScores[i] = this.phredScores[phredScores.length - i - 1];
            }
            this.phredScores = phredScores;
        }
    }

    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    // Vererbung - Polymorphie
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NucleotideSequence)) return false;
        NucleotideSequence s = (NucleotideSequence) o;
        return s.sequence.equals(this.sequence);
    }
}
