/** 
 * This is a node class for the Double linked list
 * The next variable is a pointer to the next node
 * The prev variable pointer to the previous node
 * 
 * @author pkapenekakis
*/
public class Node<T> {
	T data;
	Node<T> next;
	Node<T> prev;
	
	/**
	 * Constructor 1
	 *  
	 *  @param data is the node data
	 *  @param next reference to the next node
	 *  @param prev reference to the previous node
	 * 
	 */
	
	public Node(T data, Node<T> next , Node<T> prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}
	
	/**
	 * Constructor 2
	 *  
	 *  next and prev variables are set to null
	 *  @param data is the node data
	 */
	
	public Node(T d) {
		this.data = d;
		this.next = null;
		this.prev = null;
	}
}
