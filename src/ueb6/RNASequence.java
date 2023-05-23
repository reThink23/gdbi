package ueb6;

public class RNASequence extends NucleotideSequence {

	public RNASequence(String sequence) {
		super(sequence);
		if(!sequence.matches("^[ACGUNacgun$]+")){
			throw new IllegalArgumentException("No RNA alphabet.");
		}
	}
	
	@Override
	public NucleotideSequence getReverseComplement() {
		return new RNASequence(this.getReverseComplementString());
	}

	@Override
	protected char getComplement(char current) {
		switch(current){
		case 'A': return 'U';
		case 'C': return 'G';
		case 'G': return 'C';
		case 'U': return 'A';
		case 'N': return 'N';
		case 'a': return 'u';
		case 'c': return 'g';
		case 'g': return 'c';
		case 'u': return 'a';
		case 'n': return 'n';
		default: throw new RuntimeException("Illegal RNA nucleotide");
		}
	}

	@Override
	public Sequence getSubSequence(int start, int end) {
		return new RNASequence(this.sequence.substring(start, end));
	}

}
