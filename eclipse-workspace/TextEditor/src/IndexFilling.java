/** 
 * This is the class used to create the index file
 * 
 * 
 * @author pkapenekakis
*/
public class IndexFilling implements Comparable<IndexFilling> {
	private int line;
	private String word;
	
	public IndexFilling(String w, int l) {
		this.line =l;
		this.word = w;
	}
	
	public int getLine() {
		return line;
	}

	public String getWord() {
		return word;
	}

	@Override
	public int compareTo(IndexFilling iF) {
		int comp = this.word.compareTo(iF.word); //if word is greater or equal than iF.word returns <=0
		if(comp <= 0) 
			return 1;
		else
			return -1;	
	}
}
