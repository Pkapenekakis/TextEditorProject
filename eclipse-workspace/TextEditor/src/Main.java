import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.BufferUnderflowException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		final int minWordSize = 5;
		final int maxWordSize = 20;
		final int pageSize = 128;
		int byteTotal = 0, lineTotal = 0, numberOfPages = 0;
		int CurrentLine = 1; // starts as 1 (first line)
		String line = null;
		String userInp;
		Boolean indexFileWasCreated = false;
		Boolean printLinesOption = false;
		Scanner scanner = new Scanner(System.in);
		DoubleLinkedList<String> dL = new DoubleLinkedList<String>();

		if (args.length > 0) {
			File file = new File(args[0]);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			while ((line = br.readLine()) != null) { // adds all the lines as nodes of the list and counts total
														// characters
				dL.addLast(line); // adds the whole line as the node data
				for (int i = 0; i < line.length(); i++)
					if (line.charAt(i) != ' ')
						byteTotal++;
				lineTotal++;
			}

			Node<String> pos = new Node<String>(null); // acts as a pointer, indicates current line
			pos = dL.getHead(); // init at the start of the list (head)

			switch (args[0]) {
			case "^": // Go to first line
				pos = dL.getHead();
				CurrentLine = 1;
				System.out.println("OK");
				break;
			case "$": // Go to last line
				while (pos.next != null)
					pos = pos.next;
				CurrentLine = lineTotal;
				System.out.println("OK");
				break;
			case "-": // Go up one line
				if (pos == dL.getHead())
					System.out.println("Cant go up");
				else {
					Node<String> temp = new Node<String>(null); // temp is a temporary node in order to move pos, init
																// at head
					temp = dL.getHead();

					while (temp.next != pos)
						temp = temp.next;
					pos = temp;
					CurrentLine -= 1;
					System.out.println("OK");
				}
				break;
			case "+": // Go down one line
				Node<String> tail = new Node<String>(null); // tail needed to check if we are at the last line and
															// cannot go further down
				tail = dL.getHead();
				while (tail.next != null)
					tail = tail.next;

				if (pos == tail) {
					System.out.println("Cant go down");
				} else {
					Node<String> temp = new Node<String>(null); // temp is a temporary node in order to move pos, init
																// at head
					temp = dL.getHead();

					while (temp != pos)
						temp = temp.next;
					temp = temp.next;
					pos = temp;
					CurrentLine += 1;
					System.out.println("OK");
				}
				break;
			case "a": // Add new line after current
				System.out.println("Type text for new line: ");
				userInp = scanner.nextLine();
				dL.addAfter(CurrentLine, userInp);
				break;
			case "t": // Add new line before current
				System.out.println("Type text for new line: ");
				userInp = scanner.nextLine();
				dL.addBefore(CurrentLine, userInp);
				break;
			case "d": // Del current line
				dL.remove(CurrentLine);
				pos = pos.next;
				System.out.println("line " + CurrentLine + " Deleted");
				break;
			case "l": // Print all lines
				Node<String> printNode = new Node<String>(null);
				printNode = dL.getHead();
				int pLine = 1;
				
				if (printLinesOption == true) {
					while (printNode.next != null) {
						System.out.println(pLine + " " + printNode.data);
						printNode = printNode.next;
						pLine++;
					}
				}else {
					while (printNode.next != null) {
						System.out.println(printNode.data);
						printNode = printNode.next;
						}
				}
				break;
			case "n": // Display line numbers when printing
				if(printLinesOption == true) 
					printLinesOption = false;
				else
					printLinesOption = true;
				break;
			case "p": // Print current line
				System.out.println(pos.data);
				break;
			case "q": // Quit no save
				System.out.println("Exiting");
				break;
			case "w": // write file to disk
				BufferedWriter bW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				Node<String> pNode = new Node<String>(null);
				pNode = dL.getHead();
				while (pNode.next != null) {
					bW.write(pNode.data, 0, pNode.data.length());
					bW.newLine();
					pNode = pNode.next;
				}
				bW.close();
				System.out.println("OK");
				break;
			case "x": // Exit and save TODO ask what should be done here
				BufferedWriter bW1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				Node<String> pNode1 = new Node<String>(null);
				pNode1 = dL.getHead();
				while (pNode1.next != null) {
					bW1.write(pNode1.data, 0, pNode1.data.length());
					bW1.newLine();
					pNode1 = pNode1.next;
				}
				bW1.close();
				System.out.println("Saving and exiting...");
				break;
			case "=": // Print current line number
				System.out.println(CurrentLine);
				break;
			case "#": // Print number of lines and characters
				System.out.println("Total number of lines: " + lineTotal);
				System.out.println("Total number of bytes: " + byteTotal);
				break;
			case "c": // Create the indexing file
				BufferedReader indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String indexLine = null;
				String[] wordLine = null;
				String delim = "[ ],.@#$%^&*()!~`';}{|<>/?:\"";
				ArrayList<IndexFilling> indexList = new ArrayList<IndexFilling>();
				int indexNum = 0; // indicates in which line the word is
				while ((indexLine = indexReader.readLine()) != null) {
					wordLine = indexLine.split(delim);
					indexNum++;

					for (int j = 0; j < wordLine.length; j++) { // for each word in line
						if (wordLine[j].length() < minWordSize) {
							continue;
						} else if (wordLine[j].length() > maxWordSize) {
							wordLine[j] = wordLine[j].substring(0, maxWordSize); // cuts the word
							indexList.add(new IndexFilling(wordLine[j], indexNum));
						} else {
							indexList.add(new IndexFilling(wordLine[j], indexNum));
						}
					}
				}
				Collections.sort(indexList); //indexList now contains all words with their corresponding line, sorted
				indexReader.close();
				
				int bufferSize = pageSize;
				int check = indexList.size(); //used to check if everything from the list has been written
				int loop = 0;
				int check1 = 1; //used to check if the buffer is full incement every "word-line" we write
				File pathingFile = new File(args[0] + ".ndx.");
				Path path =  pathingFile.toPath();
				
				java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(pageSize);
				while (loop <= check) {
					while (check1 <= (bufferSize / (maxWordSize + 4))) { //may need <
						if (indexList.get(loop).getWord().length() < maxWordSize) {
							bb.put(indexList.get(loop).getWord().getBytes(java.nio.charset.StandardCharsets.US_ASCII));
							for (int tmp = 0; tmp < (maxWordSize - indexList.get(loop).getWord().length()); tmp++) { // Puts space in the remaining slots after the word
								bb.put((byte) 32);
							}
							bb.putInt(indexList.get(loop).getLine());
						} else {
							bb.put(indexList.get(loop).getWord().getBytes(java.nio.charset.StandardCharsets.US_ASCII));
							bb.putInt(indexList.get(loop).getLine());
						}
						check1++;
					}
					//Reaching here the buffer cannot get another String-int
					int rem = bufferSize % (maxWordSize + 4); //The remaining bytes where a duet cannot fit
					if(rem != 0) {
						bb.put((byte) 0); //adding the null character at the end of the buffer
					}
					byte[] byteArray = bb.array();
					bb.clear();
					Files.write(path, byteArray, StandardOpenOption.APPEND);
					numberOfPages++; //A page has been written
					loop++;
					byteArray = null; //empties byteArray to be used in the next loop
				}
				indexFileWasCreated = true;
				break;
			case "v": // Print the indexing file TODO if have time at the else, create an option to make the index file ASK if we need to access the disk p-p for print
				if (indexFileWasCreated) {
					FilePageAccess fileAccess = new FilePageAccess(args[0] + ".ndx.", pageSize);
					byte byteArray[] = new byte[maxWordSize + 4];
					
					byte[] buffer = fileAccess.readAll(); //buffer now contains every byte from disk
					java.nio.ByteBuffer bbuffer = java.nio.ByteBuffer.wrap(buffer);		
					for(int temp = 0;temp < numberOfPages ;temp++) {
						try {
							bbuffer.get(byteArray, 0, maxWordSize + 4); //fills byteArray with the tuple bytes
							if (byteArray[0] != (byte) 0) { 
								String dLine = new String(byteArray, java.nio.charset.StandardCharsets.US_ASCII); //dLine contains string-int
								System.out.println(dLine); //Prints the line (string-int)
							} 						
							byteArray = null; //reset byteArray
						} catch (BufferUnderflowException e) { //reaches here when bbuffer does not contain another tuple
							break;
						}
					}
					bbuffer.clear();
				}else {
					System.out.println("Index file is not created.");
				}
				break;
			case "s": // print the requested word with serial search
				if(indexFileWasCreated) {
					FilePageAccess fileAccess = new FilePageAccess(args[0] + ".ndx.", pageSize);
					IndexFconverter converter = new IndexFconverter(maxWordSize, minWordSize, pageSize);
					ArrayList<IndexFilling> list = new ArrayList<IndexFilling>();
					ArrayList<Integer> lin = new ArrayList<Integer>();
					int page = 0;
					int dAccess = 0;
					
					System.out.println("Enter word to search for: ");
					String wordToSearch = scanner.toString();

					while (page<numberOfPages) {
						byte[] buffer = fileAccess.readPage(page); //buffer now contains every byte from the page
						list = converter.toTuples(buffer); //list now contains every tuple from the page
						for(int i=0;i<list.size();i++) {
							if(list.get(i).getWord().equals(wordToSearch))
								lin.add(list.get(i).getLine());							
						}
						page++;
						dAccess++;
					}
					System.out.print("\"" + wordToSearch+ "\" " + "is on lines: ");
					for(int j=0;j<lin.size();j++)
						System.out.print(lin.get(j)+" ");
						System.lineSeparator(); //changes the line
						System.out.println(dAccess);
					scanner.close();
				}else {
					System.out.println("Can't search Index file is not created");
				}
				break;
			case "b": // print the requested word with binary search
				FilePageAccess fileAccess = new FilePageAccess(args[0] + ".ndx.", pageSize);
				IndexFconverter converter = new IndexFconverter(maxWordSize, minWordSize, pageSize);
				ArrayList<Integer> lin = new ArrayList<Integer>();
				Bsearch search = new Bsearch(fileAccess, converter);
				int midPage = (numberOfPages/2)+1;
				int dAccess = 0;
				
				System.out.println("Enter word to search for: ");
				String wordToSearch = scanner.toString();
				
				lin = search.pageList(midPage, wordToSearch); //now lin contains the lines found
						
				while (search.getFoundStartEnd() != -1) { // when it is not found at the start or end of buffer is -1
					if (search.getFoundStartEnd() == 1) // found at the start so need to search prev page
						lin = search.pageList(midPage - 1, wordToSearch);
					else if (search.getFoundStartEnd() == 2)
						lin = search.pageList(midPage + 1, wordToSearch);
				}
				
				if(search.getWhereToNext() == 1) {
					lin = search.pageList(midPage+(midPage/2), wordToSearch);
				}else {
					lin = search.pageList(midPage-(midPage/2), wordToSearch);
				} //TODO Probably need to wrap all the above in a while loop and create a var that init at mid-page and changes accordingly to perf b search
				
				dAccess = search.getDiscAccess(); 
				
				break;
			default:
				System.out.println("Bad Command");

				br.close();
			}
		} else {
			System.out.println("You need to specify a file name!");
		}
	}
}
