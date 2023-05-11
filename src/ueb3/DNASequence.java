package ueb3;

// UML-Klasse: DNASequence
public class DNASequence extends NucleotideSequence {
    public DNASequence(String sequence) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (!SeqUtils.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a DNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = null;
    }

    public DNASequence(String sequence, int[] phredScores) throws InvalidSequenceException {
        
        // Klassenverträge - Invariante
        if (phredScores.length != sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        if (!SeqUtils.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a DNA sequence");
        
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
            if (!SeqUtils.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a DNA sequence");
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
        if (start < 0 || end > this.sequence.length() || start > end) throw new IndexOutOfBoundsException("Given start or end index is out of bounds");
        if (this.phredScores != null) {
            int[] phredScores = new int[end - start];
            System.arraycopy(this.phredScores, start, phredScores, 0, end-start);
            return new DNASequence(this.sequence.substring(start, end), phredScores);
        }
        return new DNASequence(this.sequence.substring(start, end));
    }

    @Override
    protected char getComplement(char nucleotide) {
        switch (nucleotide) {
            case 'A' -> {return 'T';}
            case 'T' -> {return 'A';}
            case 'C' -> {return 'G';}
            case 'G' -> {return 'C';}
            default -> {throw new IllegalArgumentException("Given nucleotide is not a valid DNA nucleotide");}
        }
    }
    
    // UML-Methode: getReverseComplement ("get" entfernt, um Getter klarer zu trennen),
    // Implementierung abstrakter Methode, Vererbung - Polymorphie
    @Override
    public DNASequence reverseComplement() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        seq.reverse();
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case 'A' -> seq.setCharAt(i, 'T');
                case 'T' -> seq.setCharAt(i, 'A');
                case 'C' -> seq.setCharAt(i, 'G');
                case 'G' -> seq.setCharAt(i, 'C');
                default -> {}
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
