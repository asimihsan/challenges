package ai.graph;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.HashSet;

public class Search {
	public interface PathPredicate {
		boolean isPathValid(String path);
	}
	public interface PathCallback {
		void callback(String path);
	}
	
	private Graph G;
	private PathPredicate pathPredicate;
	private PathCallback pathCallback;
	
	public Search(Graph G,
		          PathPredicate pathPredicate,
		          PathCallback pathCallback) {
		this.G = G;
		this.pathPredicate = pathPredicate;
		this.pathCallback = pathCallback;
	}
	
	public void visit() {
		for (int v = 0; v < G.V(); v++) {
			Set<Integer> previousVertices = new HashSet<Integer>();
			ArrayDeque<Integer> currentPath = new ArrayDeque<Integer>();
			dfsVisit(v, currentPath, previousVertices);
		}
	}
	
	private void dfsVisit(int v,
						  ArrayDeque<Integer> currentPath,
						  Set<Integer> previousVertices) {
		currentPath.addLast(v);
		previousVertices.add(v);
		String currentPathString = currentPathToString(G, currentPath);
		//System.out.println("v before: " + v + ", currentPath: " + currentPath + ", G.adj(v): "  + G.adj(v) + ", previousVertices: " + previousVertices);
		if (pathPredicate.isPathValid(currentPathString)) {
			pathCallback.callback(currentPathString);
			for (int w : G.adj(v)) {
				if (!(previousVertices.contains(w))) {
					dfsVisit(w, currentPath, previousVertices);
				}
			}
		}
		currentPath.removeLast();
		previousVertices.remove(v);
		//System.out.println("v after: " + v + ", currentPath: " + currentPath + ", G.adj(v): "  + G.adj(v) + ", previousVertices: " + previousVertices);
	}
	
	public static String currentPathToString(Graph G, Queue<Integer> currentPath) {
		StringBuilder rv = new StringBuilder();
		for (Integer v : currentPath)
			rv.append(G.intToNode(v).getData());
		return rv.toString();
	}
}
