package ueb6;

public class ProteinSequence extends Sequence {

	public ProteinSequence(String sequence){
		super(sequence);
		if(!sequence.matches("^[ARNDBCEQZGHILKMFPSTWYVXarndbceqzghilkmfpstwyvx]+$")){
			throw new IllegalArgumentException("No protein alphabet.");
		}
	}
	
	@Override
	public Sequence getSubSequence(int start, int end) {
		return new ProteinSequence(this.sequence.substring(start, end));
	}

}
