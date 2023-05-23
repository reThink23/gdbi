package ueb6;

public class DNASequence extends NucleotideSequence {

	public DNASequence(String sequence) {
		super(sequence);
		if(!sequence.matches("^[ACGTNacgtn$]+")){
			throw new IllegalArgumentException("No DNA alphabet.");
		}
	}
	
	@Override
	public NucleotideSequence getReverseComplement() {
		return new DNASequence(this.getReverseComplementString());
	}

	@Override
	public DNASequence getSubSequence(int start, int end) {
		return new DNASequence(this.sequence.substring(start, end));
	}

	@Override
	protected char getComplement(char current) {
		switch(current){
		case 'A': return 'T';
		case 'C': return 'G';
		case 'G': return 'C';
		case 'T': return 'A';
		case 'N': return 'N';
		case 'a': return 't';
		case 'c': return 'g';
		case 'g': return 'c';
		case 't': return 'a';
		case 'n': return 'n';
		default: throw new RuntimeException("Illegal DNA nucleotide");
		}
	}

	
	public RNASequence transcribe(){
		return new RNASequence(this.sequence.replace('T', 'U').replace('t', 'u'));
	}
	
}
