package ai.datastructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

public class Trie {
	private Node root;
	
	private static class Node {
		char c;					// character
		Node left, mid, right;  // left, middle, and right subtries
		Integer val;		    // value associated with string
		
		@Override
		public String toString() {
			ToStringHelper rv = Objects.toStringHelper(this);
			rv.add("c", c);
			if (left != null)
				rv.add("left.val", left.val);
			if (mid != null)
				rv.add("mid.val", mid.val);
			if (right != null)
				rv.add("right.val", right.val);
			rv.add("val", val);
			return rv.toString();
		}
	}
	
	public Integer get(String key) {
		Node x = get(root, key, 0);
		if (x == null) return null;
		return x.val;
	}
	
	private Node get(Node x, String key, int d) {
		if (x == null) return null;
		char c = key.charAt(d);
		if			(c < x.c)  return get(x.left, key, d);
		else if		(c > x.c)  return get(x.right, key, d);
		else if		(d < key.length() - 1)
							   return get(x.mid, key, d+1);
		else return x;
		
	}
	
	public void put(String key, Integer val) {
		root = put(root, key, val, 0);
	}
	
	private Node put(Node x, String key, Integer val, int d) {
		char c = key.charAt(d);
		if (x == null) { x = new Node(); x.c = c; }
		if			(c < x.c)  x.left = put(x.left, key, val, d);
		else if 	(c > x.c)  x.right = put(x.right, key, val, d);
		else if     (d < key.length() - 1)
							   x.mid = put(x.mid, key, val, d+1);
		else        x.val = val;
		return x;
	}
	
	public boolean isPrefix(String prefix) {
		Node x = get(root, prefix, 0);
		if (x == null) return false;
		return true;
	}
	
	public boolean isWord(String word) {
		Node x = get(root, word, 0);
		if (x == null)     return false;
		if (x.val == null) return false;
		if (x.val == 1)    return true;
		return false;
	}
	
	final String dictionaryPath = "/usr/share/dict/words";
	
	public Trie(String filter) throws IOException {
		Set<Character> charFilter = new HashSet<Character>();
		for (int i = 0; i < filter.length(); i++) {
			Character c = filter.charAt(i);
			if (Character.isLetter(c))
				charFilter.add(Character.toLowerCase(c));
		}
		
		FileReader fr = new FileReader(dictionaryPath);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			boolean validWord = true;
			for (int i = 0; i < line.length(); i++) {
				if (!(charFilter.contains(line.charAt(i)))) {
					validWord = false;
					break;
				}
			}
			if (validWord)
				put(line, 1);
		}
		br.close();
		fr.close();
	}

}
