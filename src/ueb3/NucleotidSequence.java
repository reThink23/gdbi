package ueb3;

public abstract class NucleotidSequence extends Sequence {
    public abstract NucleotidSequence reverseComplement() throws InvalidSequenceException;
    public abstract Sequence subSeq(int start, int end) throws InvalidSequenceException;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NucleotidSequence)) return false;
        NucleotidSequence s = (NucleotidSequence) o;
        return s.sequence.equals(this.sequence);
    }
}
