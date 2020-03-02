import java.util.ArrayList;

public class IndexFconverter {
	private int maxLength,minLength, pageSize;
	
	public IndexFconverter(int max ,int min ,int p) {
		this.maxLength = max;
		this.minLength = min;
		this.pageSize = p;
	}
	
	public ArrayList<IndexFilling> toTuples(byte[] buffer){ //Typically a page size
		ArrayList<IndexFilling> list = new ArrayList<IndexFilling>();
		int check = this.pageSize/(maxLength+4); //max tuples = (page Size) / (size per tuple)
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(buffer);	
		byte bArray[] = new byte[maxLength+4];
		for(int i=0;i<check;i++) { //for all the tuples of the buffer (typically page)
			bb.get(bArray, 0, maxLength); //fills bArray with the string of the tuple
			if (bArray[0] != (byte) 0) {
				String data = new String(bArray, java.nio.charset.StandardCharsets.US_ASCII);
				int line = bb.getInt();
				list.add(new IndexFilling(data, line));
			}
			
		}

		return list;
	}
	
}
