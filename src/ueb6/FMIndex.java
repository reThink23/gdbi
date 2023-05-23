package ueb6;
import java.util.Arrays;
import java.util.Comparator;



public class FMIndex {

	private char[] bwt;
	private int[] first;
	private int[] charMap;
	private int[] r;
	private int[][] rank;
	
	private Integer[] suffar;
	
	
	public FMIndex(Sequence genome){
		genome = genome.append('$');
		suffar = new Integer[genome.getLength()];
		charMap = new int[256];
		for(int i=0;i<suffar.length;i++){
			suffar[i] = i;
			char fc = genome.getSymbolAt(i);
			charMap[ (int)fc ] = 1;
		}
		int k=0;
		for(int i=0;i<charMap.length;i++){
			if(charMap[i] == 1){
				charMap[i] = k;
				k++;
			}
		}
		first = new int[k];
		Arrays.fill( first, -1 );
		r = new int[suffar.length];
		rank = new int[suffar.length][k];
		
		final Sequence genome2 = genome;
		
		Arrays.sort(suffar, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				int comp = 0;
				int i=0;
				for(;o1+i<genome2.getLength() & o2+i<genome2.getLength();i++){
					if(genome2.getSymbolAt(o1+i)>genome2.getSymbolAt(o2+i)){
						comp = 1;
						break;
					}else if(genome2.getSymbolAt(o1+i)<genome2.getSymbolAt(o2+i)){
						comp = -1;
						break;
					}
				}
				if(i+o1==genome2.getLength()){
					comp = 1;
				}else if(i+o2==genome2.getLength()){
					comp = -1;
				}
				
				return comp;
			}
			
		});
		
		
		bwt = new char[suffar.length];
		for(int i=0;i<suffar.length;i++){
			int idx = suffar[i];
			if(idx == 0){
				bwt[i] = '$';
			}else{
				bwt[i] = genome.getSymbolAt( idx-1 );
			}
			char F = genome.getSymbolAt(suffar[i]);
			if(first[ charMap[(int)F] ] < 0){
				first[ charMap[(int)F] ] = i;
			}
			if(i > 0){
				System.arraycopy(rank[i-1], 0, rank[i], 0, rank[i-1].length);
			}
			rank[i][ charMap[(int)bwt[i]] ]++;
			r[i] = rank[i][ charMap[(int)bwt[i]] ];
		}
		
	}
		
	public int[] search(Sequence read){
		int L=0;
		int R=bwt.length-1;
		for(int i=read.getLength()-1;i>=0;i--){
			
			char a = read.getSymbolAt(i);
			int Lp = first[charMap[ (int)a ]]  + (L > 0 ? rank[L-1][charMap[ (int)a ]] : 0 );
			int Rp = first[charMap[ (int)a ]] + (R >= 0 ? rank[R][charMap[ (int)a ]] : 0 ) - 1;
			L = Lp;
			R = Rp;
		}
		int[] matches = new int[R-L+1];
		for(int i=L;i<=R;i++){
			matches[i-L] = suffar[i];
		}
		return matches;
	}
	
}
