/** 
 * This is a Double linked list class
 * The head variable is a pointer at the start of the list
 * The size variable is the size of the list
 * 
 * @author pkapenekakis
*/
public class DoubleLinkedList<T> {
	private Node<T> head;
	private int size;

	public DoubleLinkedList(){
		head = null;
		size = 0;
	}
	
	public Node<T> getHead() {
		return head;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0; //returns true if size == 0
	}

	public void addFront(T data) {
		if(isEmpty())
			head = new Node<T>(data);
		else {
			Node<T> temp = head;
			head = new Node<T>(data, temp, null); // head has data, next node is temp and prev node is null
			head.next.prev = head;
		}
		size++;
	}
	
	public void addLast(T data) {
		if(isEmpty())
			head = new Node<T>(data);
		else {
			Node<T> temp = head;
			while(temp.next != null)
				temp = temp.next;
			temp.next = new Node<T>(data, null, temp);
		}
		size++;
	}
	
	public void addAfter(int currLine, T data) {
		if (isEmpty()) {
			head = new Node<T>(data);
		}else if (currLine == 1){ //that means we add after the head where the current.next is null 
			Node<T> temp = new Node<T>(data, head.next, head);
			head.next = temp;
		}else {
			Node<T> current = head;
			for (int i = 1; i < currLine; i++)
				current = current.next;
			Node<T> temp = new Node<T>(data, current.next, current);
			current.next.prev = temp;
			current.next = temp;
		}
		size++;
	}
	
	public void addBefore(int currLine, T data) {
		if (currLine == 1){ //that means we add before the head 
			this.addFront(data);
		}else {
			Node<T> current = head;
			for (int i = 1; i < currLine; i++)
				current = current.next;
			Node<T> temp = new Node<T>(data, current, current.prev);
			current.prev.next = temp;
			current.prev = temp;
		}
		size++;
	}
	
	public void remove(int currLine) {
		if(isEmpty()) { //try to remove head node
			System.out.println("List empty, cannot remove");
		}else if (currLine == 1){
			head = head.next;
		}else {
			Node<T> current = head;
			Node<T> temp = head;
			for(int i=1;i<currLine;i++)
				current = current.next;
			while(temp.next != current)
				temp = temp.next;
			temp.next = current.next;
			if(current.next != null) //if current was the last node(tail) there was a problem
				current.next.prev = temp;
		}
		size--;
	}
}
