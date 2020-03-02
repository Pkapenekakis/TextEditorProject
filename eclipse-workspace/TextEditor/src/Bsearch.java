import java.util.ArrayList;

public class Bsearch {
	private FilePageAccess fileAccess;
	private IndexFconverter converter;
	private int foundStartEnd =-1; //1 found at start 2 found at end
	private int discAccess = 0;
	private int whereToNext = 2; //1 if we need to search on the right -1 if we need on the left
	
	public Bsearch(FilePageAccess f, IndexFconverter co) {
		this.fileAccess = f;
		this.converter = co;
	}
	
	public ArrayList<Integer> pageList(int page, String word){
		ArrayList<IndexFilling> list = new ArrayList<IndexFilling>();
		ArrayList<Integer> lin = new ArrayList<Integer>();
		byte[] buffer = fileAccess.readPage(page); //buffer now contains every byte from the mid page
		list = converter.toTuples(buffer); //list now contains every tuple from the page
		int end = list.size(); // used to see if the word is found at the end of buffer
		
		foundStartEnd = -1;
		for(int i=0;i<list.size();i++) { //searches the whole page, its cost-efficient to search the whole page, instead of making comparisons for one page
			if(list.get(i).getWord().equals(word))
				lin.add(list.get(i).getLine());	
			if(list.get(0).getWord().equals(word))
				foundStartEnd = 1;
			if(list.get(end).getWord().equals(word)) //may need end-1
				foundStartEnd = 2;
		}
		
		if(lin.isEmpty()) //it was not found on this page
			whereToNext = list.get(0).getWord().compareTo(word);

		
		list.clear(); //empties the list
		buffer = null; //empties buffer
		discAccess++;
		return lin;
		
	}

	public int getWhereToNext() {
		return whereToNext;
	}

	public int getFoundStartEnd() {
		return foundStartEnd;
	}

	public int getDiscAccess() {
		return discAccess;
	}
	
}
