package ueb6;

public abstract class NucleotideSequence extends Sequence {

	public NucleotideSequence(String sequence){
		super(sequence);
	}

	
	public abstract NucleotideSequence getReverseComplement();
	
	protected String getReverseComplementString(){
		StringBuilder build = new StringBuilder();//str = "";
		for(int i=this.sequence.length()-1;i>=0;i--){
			char orig = this.sequence.charAt(i);
			char compl = this.getComplement(orig);
			build.append(compl);// str = str + compl;
		}
		
		return build.toString();
	}
	
	protected abstract char getComplement(char current);
	
}
