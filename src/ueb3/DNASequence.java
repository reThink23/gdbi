package ueb3;

public class DNASequence extends NucleotidSequence {
    public DNASequence(String sequence) throws InvalidSequenceException {
        if (!testLetters(Sequence.RNAPATTERN, sequence)) throw new InvalidSequenceException("Given Sequence is not an DNA sequence");
        this.sequence = sequence;
        this.length = this.sequence.length();
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
        return new DNASequence(this.sequence.substring(start, end));
    }

    @Override
    public NucleotidSequence reverseComplement() throws InvalidSequenceException {
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
}
