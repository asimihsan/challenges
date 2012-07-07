package ai.graph;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Graph {
	private List<Set<Integer>> adj;		// adjacency sets
	private List<Node> vertices;
	private int V; // number of vertices
	private int E; 		 // number of edges
	
	public Graph(Iterable<Node> nodes) {
		vertices = new ArrayList<Node>();
		adj = new ArrayList<Set<Integer>>();
		for (Node node : nodes) {
			adj.add(new HashSet<Integer>());
			vertices.add(node);
		}
		V = vertices.size();
	}
	
	public int V() { return V; }
	public int E() { return E; }
	
	public Graph addEdge(int v, int w) {
		adj.get(v).add(w);
		adj.get(w).add(v);
		E++;
		return this;
	}
	
	public Iterable<Integer> adj(int v) {
		return Collections.unmodifiableSet(adj.get(v));
	}
	
	public Node intToNode(int v) {
		return vertices.get(v);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges\n");
		for (int v = 0; v < V(); v++) {
			s.append("v: " + v + ", " + intToNode(v) + " ");
			for (int w : this.adj(v))
				s.append(intToNode(w) + " ");
			s.append("\n");
		}
		return s.toString();
	}
}
