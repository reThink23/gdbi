package ueb3;

public class RNASequence extends NucleotidSequence {
    public RNASequence(String sequence) throws InvalidSequenceException {
        if (!testLetters("[ACGU-]+", sequence)) throw new InvalidSequenceException("Given Sequence is not an RNA sequence");
        this.sequence = sequence;
        this.length = this.sequence.length();
    }
    @Override
    public Sequence subSeq(int start, int end) {
        return null;
    }

    @Override
    public NucleotidSequence reverseComplement() throws InvalidSequenceException {
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
}
