package ueb3;

// UML-Klasse: RNASequence
public class RNASequence extends NucleotideSequence {
    public RNASequence(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (!SeqUtils.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a RNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = null;
    }

    public RNASequence(String sequence, int[] phredScores) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (phredScores.length != sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        if (!SeqUtils.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a RNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = phredScores;
    }

    // UML-Methode: setSequence,
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public boolean setSequence(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        try {
            if (!SeqUtils.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not an RNA sequence");
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
    public Sequence subSeq(int start, int end) throws InvalidSequenceException {
        if (start < 0 || end > this.sequence.length() || start > end) throw new IndexOutOfBoundsException("Given start or end index is out of bounds");
        if (this.phredScores != null) {
            int[] phredScores = new int[end - start];
            System.arraycopy(this.phredScores, start, phredScores, 0, end-start);
            return new RNASequence(this.sequence.substring(start, end), phredScores);
        }
        return new RNASequence(this.sequence.substring(start, end));
    }

    @Override
    protected char getComplement(char nucleotide) {
        switch (nucleotide) {
            case 'A' -> {return 'U';}
            case 'U' -> {return 'A';}
            case 'C' -> {return 'G';}
            case 'G' -> {return 'C';}
            default -> {throw new IllegalArgumentException("Given nucleotide is not a valid RNA nucleotide");}
        }
    }

    // UML-Methode: getReverseComplement ("get" entfernt, um Getter klarer zu trennen),
    // Vererbung - Polymorphie
    @Override
    public RNASequence reverseComplement() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        seq.reverse();
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case 'A' -> seq.setCharAt(i, 'U');
                case 'U' -> seq.setCharAt(i, 'A');
                case 'C' -> seq.setCharAt(i, 'G');
                case 'G' -> seq.setCharAt(i, 'C');
                default -> {
                }
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
