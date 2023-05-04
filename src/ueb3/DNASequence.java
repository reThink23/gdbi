package ueb3;

// UML-Klasse: DNASequence
public class DNASequence extends NucleotideSequence {
    public DNASequence(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (!Sequence.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not an DNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
    }

    // UML-Methode: setSequence,
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public boolean setSequence(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        try {
            if (!Sequence.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
            this.sequence = sequence;
            this.length = this.sequence.length();
            return true;
        } catch (InvalidSequenceException e) {
            return false;
        }
    }

    // UML-Methode: transcribeToRNA
    public RNASequence transcribeToRNA() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'T') seq.setCharAt(i, 'U');
        }
        // seq.reverse();
        return new RNASequence(seq.toString());
    }

    // UML-Methode: getSubSeq ("get" entfernt, um Getter klarer zu trennen),
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public Sequence subSeq(int start, int end) throws InvalidSequenceException {
        return new DNASequence(this.sequence.substring(start, end));
    }
    
    // UML-Methode: getReverseComplement ("get" entfernt, um Getter klarer zu trennen),
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public DNASequence reverseComplement() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        seq.reverse();
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case 'A':
                    seq.setCharAt(i, 'T');
                    break;
                case 'T':
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
        return new DNASequence(seq.toString());
    }

    // neue Methode, um weitere Implementierungen/Ausweitungen zu vereinfachen
    // Vererbung - Polymorphie
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DNASequence)) return false;
        DNASequence s = (DNASequence) o;
        return s.sequence.equals(this.sequence);
    }
}
