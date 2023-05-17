package ueb3;

public class RNASequence extends NucleotideSequence {
    public RNASequence(String sequence) throws InvalidSequenceException {
        if (!SeqUtils.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a RNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = null;
    }

    public RNASequence(String sequence, int[] phredScores) throws InvalidSequenceException {
        if (phredScores.length != sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        if (!SeqUtils.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not a RNA sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = phredScores;
    }

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
    public RNASequence reverseComplement() throws InvalidSequenceException {
        StringBuilder seq = new StringBuilder(sequence);
        seq.reverse();
        for (int i = 0; i < seq.length(); i++) {
            seq.setCharAt(i, getComplement(seq.charAt(i)));
        }
        return new RNASequence(seq.toString());
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

    @Override
    public boolean setSequence(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        try {
            if (!SeqUtils.isRNA(sequence)) throw new InvalidSequenceException("Given Sequence is not an RNA sequence");
            this.sequence = sequence;
            this.length = this.sequence.length();
            return true;
        } catch (InvalidSequenceException e) { return false; }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof RNASequence)) return false;
        RNASequence s = (RNASequence) o;
        return s.sequence.equals(this.sequence);
    }

    @Override
    public String toString() { return "RNA:(" + length + ")" + sequence; }
}
