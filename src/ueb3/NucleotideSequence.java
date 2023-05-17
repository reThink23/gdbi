package ueb3;

public abstract class NucleotideSequence extends Sequence {
    
    protected void reversePhredScore() {
        if (this.phredScores != null) {
            int[] phredScores = new int[this.phredScores.length];
            for (int i = 0; i < phredScores.length; i++) {
                phredScores[i] = this.phredScores[phredScores.length - i - 1];
            }
            this.phredScores = phredScores;
        }
    }

    public abstract NucleotideSequence reverseComplement() throws InvalidSequenceException;
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    protected abstract char getComplement(char nucleotide);


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NucleotideSequence)) return false;
        NucleotideSequence s = (NucleotideSequence) o;
        return s.sequence.equals(this.sequence);
    }
}
