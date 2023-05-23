package ueb6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Sequence {

	protected String sequence;
	
	public Sequence(String sequence){
		this.sequence = sequence;
	}
	
	public char getSymbolAt(int i){
		return this.sequence.charAt(i);
	}
	
	public int getLength(){
		return sequence.length();
	}
	
	public abstract Sequence getSubSequence(int start, int end);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<Sequence> parseFasta(String filename) throws FileNotFoundException, IOException{
		
		BufferedReader read = new BufferedReader(new FileReader(filename));
		
		ArrayList<Sequence> seqs = new ArrayList<Sequence>(); 
		
		StringBuilder curr = new StringBuilder();
		String str = null;
		while( (str = read.readLine()) != null ){
			if(str.startsWith(">")){
				if(curr.length()>0){
					seqs.add(createSequence(curr.toString()));
				}
				curr.delete(0, curr.length());
			}else{
				curr.append(str);
			}
		}
		if(curr.length()>0){
			seqs.add(createSequence(curr.toString()));
		}

		read.close();
		
		return seqs;		
	}

	private static Sequence createSequence(String string) {//also allow '$'
		if(string.matches("^[ACGTNacgtn$]+")){
			return new DNASequence(string);
		}else if(string.matches("^[ACGUNacgun$]+")){
			return new RNASequence(string);
		}else{
			return new ProteinSequence(string);
		}
	}
	
	public String toString(){
		return sequence;
	}
	
	
	public Sequence append(char appended){
		return createSequence(this.sequence+appended);
	}
	
	
}
