package ueb3;

// UML-Klasse: ProteinSequence
public class ProteinSequence extends Sequence{
    public ProteinSequence(String sequence) throws InvalidSequenceException {
        // Klassenverträge - Invariante
        if (!testLetters(Sequence.PROTEINPATTERN, sequence)) throw new InvalidSequenceException("Given Sequence is not an protein sequence");
        
        this.sequence = sequence;
        this.length = this.sequence.length();
    }

    // Vererbung - Polymorphie
    @Override
    public ProteinSequence subSeq(int start, int end) throws InvalidSequenceException {
        return new ProteinSequence(this.sequence.substring(start, end));
    }
}
