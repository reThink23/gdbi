package ueb3;

public class ProteinSequence extends Sequence{
    public ProteinSequence(String sequence) throws InvalidSequenceException {
        if (!testLetters("[ACDEFGHIKLMNPQRSTVWYZXBU-*]+", sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
        this.sequence = sequence;
        this.length = this.sequence.length();
    }

    @Override
    public ProteinSequence subSeq(int start, int end) throws InvalidSequenceException {
        return new ProteinSequence(this.sequence.substring(start, end));
    }
}
