package ueb3;

public class DNASequence extends NucleotideSequence {
    public DNASequence(String sequence) throws InvalidSequenceException {
        if (!SeqUtils.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a DNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = null;
    }

    public DNASequence(String sequence, int[] phredScores) throws InvalidSequenceException {
        if (phredScores.length != sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        if (!SeqUtils.isDNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a DNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = phredScores;
    }

    public RNASequence transcribeToRNA() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == 'T') seq.setCharAt(i, 'U');
        }
        // seq.reverse();
        return new RNASequence(seq.toString());
    }

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
    public DNASequence reverseComplement() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        seq.reverse();
        for (int i = 0; i < seq.length(); i++) {
            seq.setCharAt(i, getComplement(seq.charAt(i)));
        }
        return new DNASequence(seq.toString());
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DNASequence)) return false;
        DNASequence s = (DNASequence) o;
        return s.sequence.equals(this.sequence);
    }

    @Override
    public String toString() { return "DNA:("+ length + ")" + sequence; }
}
