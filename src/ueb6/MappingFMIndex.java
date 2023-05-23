package ueb6;

import java.io.FileNotFoundException;
import java.io.IOException;
// import java.util.Arrays;
import java.util.List;

public class MappingFMIndex {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Sequence genome = Sequence.parseFasta(args[0]).get(0);
		
		List<Sequence> reads = Sequence.parseFasta(args[1]);
		
		
		long time = System.currentTimeMillis();
		FMIndex index = new FMIndex(genome);
		
		System.out.println("Index bauen: "+(System.currentTimeMillis()-time));
		
		time = System.currentTimeMillis();
		
		int[] poss = new int[reads.size()];
		int i=0;
		for(Sequence read : reads){
			
			int[] pos = index.search(read);
			
			if(pos.length>0){
				poss[i] = pos[0];
			}else{
				poss[i] = -1;
			}
			
			i++;
		}
		
		System.out.println("Suche: "+(System.currentTimeMillis()-time));
		
		
		//System.out.println(Arrays.toString(poss));
		
	}

}
