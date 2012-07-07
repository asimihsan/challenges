package ai.exposed;

public class Node<T> {
	private T mData;
	private Node<T> mNext;
	
	// -----------------------------------------------------------------------
	//	Constructors
	// -----------------------------------------------------------------------
	public Node(T data) {
		this(data, null);
	}
	
	public Node(Node<T> other) {
		this(other.getData(), other.getNext());
	}
	
	public Node(T data, Node<T> next) {
		this.mData = data;
		this.mNext = next;
	}
	// -----------------------------------------------------------------------
	
	public T getData() {
		return this.mData;
	}
	
	public void setData(T data) {		
		this.mData = data;
	}
	
	public Node<T> getNext() {
		return this.mNext;
	}
	
	public void setNext(Node<T> next) {
		this.mNext = next;
	}
	
}
