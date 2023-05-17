package ueb3;

public class ProteinSequence extends Sequence {
    public ProteinSequence(String sequence) throws InvalidSequenceException {
        if (!SeqUtils.isProtein(sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = null;
    }

    public ProteinSequence(String sequence, int[] phredScores) throws InvalidSequenceException {
        if (phredScores.length != sequence.length()) throw new IllegalArgumentException("Given Sequence and PhredScores have different length");
        if (!SeqUtils.isProtein(sequence)) throw new InvalidSequenceException("Given Sequence is not a protein sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
        this.phredScores = phredScores;
    }

    @Override
    public ProteinSequence subSeq(int start, int end) throws InvalidSequenceException {
        if (start < 0 || end > this.sequence.length() || start > end) throw new IndexOutOfBoundsException("Given start or end index is out of bounds");
        if (this.phredScores != null) {
            int[] phredScores = new int[end - start];
            System.arraycopy(this.phredScores, start, phredScores, 0, end-start);
            return new ProteinSequence(this.sequence.substring(start, end), phredScores);
        }
        return new ProteinSequence(this.sequence.substring(start, end));
    }

    @Override
    public boolean setSequence(String sequence) {
        if (sequence == null || sequence.equals("")) return false;
        try {
            if (!SeqUtils.isProtein(sequence)) throw new InvalidSequenceException("Given Sequence is not a protein sequence");
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
        if (!(o instanceof ProteinSequence)) return false;
        ProteinSequence s = (ProteinSequence) o;
        return s.sequence.equals(this.sequence);
    }

    @Override
    public String toString() { return "Protein:(" + length + ")" + sequence; }
}
