package ai.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RedBlackTree<Key extends Comparable<Key>, Value> {
	// -----------------------------------------------------------------------
	//	Public API.
	// -----------------------------------------------------------------------
	/**
	 * 
	 * Put key-value pair into the table.
	 * (Remove key from table is value is null).
	 * 
	 * @param key Key.
	 * @param value Value.
	 */
	void put(Key key, Value value) {
		assert(key != null);
		assert(value != null); //!!AI for now, disallow delete.
		root = put(root, key, value);
		boolean check = check();
		//System.out.println("put: key=" + key + ", value=" + value + ", check=" + check);
		if (!check) throw new IllegalStateException("state is invalid.");
	}
	
	private Node put(Node h, Key key, Value value) {
		assert(h != null);
		if (h == null)
			return new Node(key, value, 1, RedBlackTree.Color.RED);
		int cmp = key.compareTo(h.key);
		if (cmp < 0)       h.left = put(h.left, key, value);
		else if (cmp > 0)  h.right = put(h.right, key, value);
		else h.value = h.value;
		
		if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))     flipColors(h);
		
		h.N = 1 + size(h.left) + size(h.right); 
		return h;
	} // private void put(Node x, Node toInsert)
	
	/**
	 * Value paired with key.
	 * (null if key is absent).
	 * 
	 * @param key Key.
	 * @return value, of type Value.
	 */
	Value get(Key key) {
		assert(key != null);
		if (size() == 0) return null;
		return get(root, key);
	}
	
	private Value get(Node x, Key key) {
		assert(x != null);
		assert(key != null);
		if (key.compareTo(x.key) == 0) return x.value;
		else if ((size(x.left) >= 1) &&
			     (key.compareTo(x.left.key) < 0))
			return get(x.left, key);
		else if ((size(x.right) >= 1) &&
			     (key.compareTo(x.right.key) > 0))
			return get(x.right, key);
		return null;
	}
	
	/**
	 * Delete smallest key.
	 */
	void deleteMin() {
		
	}
	
	/**
	 * Delete largest key.
	 */
	void deleteMax() {
		
	}
	
	/**
	 * Remove key (and its value) from table.
	 * 
	 * @param key Key.
	 */
	void delete(Key key) {
		put(key, null);
	}
	
	/**
	 * Is there a value paired with key?
	 * 
	 * @param key Key.
	 * @return boolean, whether there is a value
	 * paired with key.
	 */
	boolean contains(Key key) {
		return get(key) != null;
	}
	
	/**
	 * Is the table empty?
	 * 
	 * @return boolean, true if empty.
	 */
	boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Number of key-value pairs in the table.
	 * 
	 * @return int.
	 */
	int size() {
		return size(root);
	}
	
	/**
	 * Number of keys in [lo..hi] inclusive.
	 * 
	 * @param lo
	 * @param hi
	 * @return
	 */
	int size(Key lo, Key hi) {
		return 0;
	}
	
	/**
	 * Smallest key.
	 * 
	 * @return
	 */
	Key min() {
		return null;
	}
	
	/**
	 * Largest key.
	 * 
	 * @return
	 */
	Key max() {
		return null;
	}
	
	/**
	 * Largest key less than or equal to key.
	 * 
	 * @param key
	 * @return
	 */
	Key floor(Key key) {
		return null;
	}
	
	/**
	 * Smallest key greater than or equal to key.
	 * 
	 * @param key
	 * @return
	 */
	Key ceiling(Key key) {
		return null;
	}
	
	/**
	 * Number of keys less than key.
	 * 
	 * @param key
	 * @return
	 */
	int rank(Key key) {
		return 0;
	}
	
	/**
	 * Key of rank k.
	 * 
	 * @param k
	 * @return
	 */
	Key select(int k) {
		return null;
	}
	
	/**
	 * All the keys in the table, in sorted order.
	 * 
	 * @return
	 */
	Iterable<Key> keys() {
		if (size() == 0) return Collections.emptyList();
		List<Key> rv = new ArrayList<Key>(size());
		keys(root, rv);
		assert(rv.size() == size());
		return rv;
	}
	
	private void keys(Node x, List<Key> acc) {
		assert(x != null);
		if (size(x.left) >= 1) keys(x.left, acc);
		acc.add(x.key);
		if (size(x.right) >= 1) keys(x.right, acc);
	}
	
	protected Iterable<Node> nodes() {
		if (size() == 0) return Collections.emptyList();
		List<Node> rv = new ArrayList<Node>(size());
		nodes(root, rv);
		assert(rv.size() == size());
		return rv;
	}
	
	private void nodes(Node x, List<Node> acc) {
		assert(x != null);
		if (size(x.left) >= 1) nodes(x.left, acc);
		acc.add(x);
		if (size(x.right) >= 1) nodes(x.right, acc);
	}
	
	/**
	 * Keys in [lo..hi] inclusive, in sorted order.
	 * 
	 * @param lo
	 * @param hi
	 * @return
	 */
	Iterable<Key> keys(Key lo, Key hi) {
		return Arrays.asList();
	}
	// -----------------------------------------------------------------------
	
	// -----------------------------------------------------------------------
	//	Private fields. Protected to make unit tests work.
	// -----------------------------------------------------------------------
	protected Node root = null;
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	//	Private types. Actually protected to make unit testing
	//	possible without reflections.
	// -----------------------------------------------------------------------
	protected enum Color {RED, BLACK};
	
	protected class Node implements Comparable<Node> {
		Key key;
		Value value;
		Node left, right;
		Color color;
		int N;
		
		Node(Key key, Value value, int N, Color color) {
			this.key = key;
			this.value = value;
			this.N = N;
			this.color = color;
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("{Node: ");
			result.append("key=" + key + ", ");
			result.append("value=" + value + ", ");
			result.append("color=" + color + ", ");
			result.append("N=" + N);
			if (left != null)
				result.append(", left.key=" + left.key);
			if (right != null)
				result.append(", right.key=" + right.key);
			result.append("}");
			return result.toString();
		}

		@Override
		public int compareTo(Node other) {
			return this.key.compareTo(other.key);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object other) {
			if ( !(other instanceof RedBlackTree.Node)) return false;
			return compareTo((Node)other) == 0;
		}
	}

	// -----------------------------------------------------------------------
	//	Private methods. Actually protected to make unit testing
	//	possible without reflections.
	// -----------------------------------------------------------------------
	protected boolean isRed(Node x) {
		if (x == null) return false;
		return x.color == Color.RED;
	}
	
	protected int size(Node x) {
		if (x == null) return 0;
		return x.N;
	}
	
	protected Node rotateLeft(Node h) {
		assert(h != null);

		// Links
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		
		// Colors
		x.color = h.color;
		h.color = RedBlackTree.Color.RED;
		
		// Numbers
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right); 
		return x;
	}
	
	protected Node rotateRight(Node h) {
		assert(h != null);
		
		// Links
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		
		// Colors
		x.color = h.color;
		h.color = RedBlackTree.Color.RED;
		
		// Numbers
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right); 
		
		return x;
	}
	
	protected void flipColors(Node h) {
		assert(h != null);
		assert(h.left != null);
		assert(h.right != null);
		h.color = RedBlackTree.Color.RED;
		h.left.color = RedBlackTree.Color.BLACK;
		h.right.color = RedBlackTree.Color.BLACK;
	}
	// -----------------------------------------------------------------------
	
	// -----------------------------------------------------------------------
	//	Integrity checks
	// -----------------------------------------------------------------------
	/**
	 * Check invariants that make this a red-black tree.
	 * @return
	 */
	private boolean check() {
		//System.out.println("check() entry.");
		if (!is23())				System.out.println("Not a 2-3 tree.");
		if (!isBalanced())			System.out.println("Not balanced.");
		return is23() && isBalanced();
	}
	
	/**
	 * Does the tree have no red right links, and at most one (left)
	 * red link in a row on any path?
	 * 
	 * @return true if the tree is a 2-3 tree, false if not.
	 */
	private boolean is23() { return is23(root);}
	private boolean is23(Node x) {
		if (x == null) return true;
		//System.out.println("is23 entry. x: " + x);
		if (isRed(x.right)) return false;
		if (x != root && isRed(x) && isRed(x.left)) return false;
		return is23(x.left) && is23(x.right); 
	}
	
	/**
	 * Do all paths from root to leaf have same number of black edges?
	 * 
	 * @return true if balanced, else false if not. 
	 */
	private boolean isBalanced() {
		int black = 0;
		Node x = root;
		while (x != null) {
			if (!isRed(x)) black++;
			x = x.left;
		}
		return isBalanced(root, black);
	}
	private boolean isBalanced(Node x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	}
	// -----------------------------------------------------------------------
	
}
