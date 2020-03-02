import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FilePageAccess {
	private String fileName;
	private int pageSize;
	
	public FilePageAccess(String fn, int pSize) {
		this.fileName = fn;
		this.pageSize = pSize;
	}

	
	public byte[] readAll() {
		byte[] buffer = null;
		int check = 0, pagesWrote = 0;
		try {
			RandomAccessFile file = new RandomAccessFile(fileName,"r");
			while(check != -1 ) { //until it reaches EOF
				check = file.read(buffer, pagesWrote*pageSize, pageSize); //Writes to the buffer pageSize bytes from the file
				pagesWrote++;
			}
			file.close();
			return buffer;
		} catch (FileNotFoundException e) {
			System.out.println("File to be read cannot be found!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}
	
	public byte[] readPage(int pageNum) {
		byte[] buffer = null;
		try {
			RandomAccessFile file = new RandomAccessFile(fileName,"r");
			file.seek((pageNum-1)*pageSize); //sets the file pointer to the required page in order to start reading that page
			file.read(buffer, 0, pageSize); //Writes to the buffer pageSize bytes from the file
			file.close();
			return buffer;
		} catch (FileNotFoundException e) {
			System.out.println("File to be read cannot be found!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public void write(int pageNum,Byte[] buffer) {
		
	}
}
