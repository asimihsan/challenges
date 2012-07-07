package ai.exposed;

public class LinkedList<T> {
	Node<T> head = null;
	
	public int size() {
		int rv = 0;
		Node<T> current = head;
		while (current != null) {
			current = current.getNext();
			rv += 1;
		}
		return rv;
	}
	
	public T get(int index) { 
		return getNode(index).getData();
	}
	
	private Node<T> getNode(int index) {
		if (head == null) {
			return null;
		}
		int cnt = 0;
		Node<T> current = head;
		while (current != null) {
			current = head.getNext();
			cnt += 1;
			if (cnt > index) {
				break;
			}
		}
		if (cnt > index) {
			return null;
		} else {
			return current;
		}
	}
	
	public boolean isEmpty() {
		return (head == null);
	}
	
	public void push(T data) {
		insert(0, data);
	}
	
	public void insert(int index, T data) {
		Node<T> node = new Node<T>(data);
		if (this.isEmpty()) {
			head = node;
			return;
		}
		assert(head != null);
		if (index == 0) {
			Node<T> temp = new Node<T>(head);
			node.setNext(temp);
			head = node;
		} else {
			Node<T> insertPoint = getNode(index - 1);
			Node<T> oldNext = insertPoint.getNext();
			insertPoint.setNext(node);
			node.setNext(oldNext);
		}
	}
	
	public T pop() {
		T data = delete(0);
		return data;
	}
	
	public T delete(int index) {
		T rv;
		if (this.isEmpty()) {
			return null;
		}
		if (index == 0) {
			rv = head.getData();
			head = head.getNext();
		} else {
			Node<T> deletePoint = getNode(index - 1);
			Node<T> next = deletePoint.getNext();
			rv = next.getData();
			deletePoint.setNext(next.getNext());
		}
		return rv;
	}
}
