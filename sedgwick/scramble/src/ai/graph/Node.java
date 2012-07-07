package ai.graph;

import com.google.common.base.*;
import java.util.List;
import java.util.ArrayList;

public class Node {
	private final char data;
	public char getData() { return data; }
	
	public static List<Node> stringToNodes(String input) {
		List<Node> rv = new ArrayList<Node>(input.length());
		for (int i = 0; i < input.length(); i++) {
			Character c = input.charAt(i);
			if (Character.isLetter(c))
				rv.add(new Node(c));
		}
		return rv;
	}
	
	Node(char data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
					  .add("data", data)
					  .toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Node)) return false;
		Node o = (Node)other;
		return Objects.equal(this.data, o.getData());
	}
}
