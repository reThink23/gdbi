package ueb3;

// UML-Klasse: ProteinSequence
public class ProteinSequence extends Sequence {
    public ProteinSequence(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (!Sequence.isProtein(sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
    }

    // UML-Methode: setSequence, 
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public boolean setSequence(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        try {
            if (!Sequence.isProtein(sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
            this.sequence = sequence;
            this.length = this.sequence.length();
            return true;
        } catch (InvalidSequenceException e) {
            return false;
        }
    }

    // UML-Methode: getSubSeq ("get" entfernt, um Getter klarer zu trennen),
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public ProteinSequence subSeq(int start, int end) throws InvalidSequenceException {
        return new ProteinSequence(this.sequence.substring(start, end));
    }

    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    // Vererbung - Polymorphie
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ProteinSequence)) return false;
        ProteinSequence s = (ProteinSequence) o;
        return s.sequence.equals(this.sequence);
    }
}
