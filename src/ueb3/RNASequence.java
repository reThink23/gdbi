package ueb3;

// UML-Klasse: RNASequence
public class RNASequence extends NucleotideSequence {
    public RNASequence(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (!Sequence.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not an RNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
    }

    // UML-Methode: setSequence,
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public boolean setSequence(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        try {
            if (!Sequence.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
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
    public Sequence subSeq(int start, int end) {
        return null;
    }

    // UML-Methode: getReverseComplement ("get" entfernt, um Getter klarer zu trennen),
    // Vererbung - Polymorphie
    @Override
    public RNASequence reverseComplement() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        seq.reverse();
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case 'A':
                    seq.setCharAt(i, 'U');
                    break;
                case 'U':
                    seq.setCharAt(i, 'A');
                    break;
                case 'C':
                    seq.setCharAt(i, 'G');
                    break;
                case 'G':
                    seq.setCharAt(i, 'C');
                    break;
                default:
                    break;
            }
        }
        return new RNASequence(seq.toString());
    }

    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    // Vererbung - Polymorphie
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof RNASequence)) return false;
        RNASequence s = (RNASequence) o;
        return s.sequence.equals(this.sequence);
    }
}
