package ai.com;

import java.io.IOException;

import java.util.Collections;
import java.util.Queue;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;

import ai.graph.Node;
import ai.graph.Search;
import ai.graph.Graph;
import ai.datastructure.Trie;

public class Scramble {
	
	public static void main(String[] args) throws IOException {
		String input = "todj bess oori lcge";
		List<Node> nodes = Node.stringToNodes(input);
		Graph G = new Graph(nodes);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int ii = (i-1); ii <= (i+1); ii++) {
					for (int jj = (j-1); jj <= (j+1); jj++) {
						if ((ii < 0) || (ii >= 4) ||
							(jj < 0) || (jj >= 4))
							continue;
						int k = 4*i + j;
						int kk = 4*ii + jj;
						if (k != kk)
							G.addEdge(k, kk);
					}
				}
			}
		}
		System.out.println("G: " + G);
		
		Trie T = new Trie(input);
		System.out.println("isWord(noise): " + T.isWord("noise"));
		System.out.println("isPrefix(noi): " + T.isPrefix("noi"));
		
		class MyPathPredicate implements Search.PathPredicate {
			private Trie T;
			public MyPathPredicate(Trie T) { this.T = T; }
			
			@Override
			public boolean isPathValid(String path) {
				return T.isPrefix(path);
			}			
		}
		MyPathPredicate predicate = new MyPathPredicate(T);
		
		class MyPathCallback implements Search.PathCallback {
			private Trie T;
			private Set<String> wordsUnsorted;
			private List<String> words;
			public MyPathCallback(Trie T) {
				this.T = T;
				this.words = new ArrayList<String>();
				this.wordsUnsorted = new HashSet<String>();
			}
			
			@Override
			public void callback(String path) {
				if ((path.length() >= 3) && ((T.isWord(path)))) {
					wordsUnsorted.add(path);
				}
			}
			
			class LengthComparator implements Comparator<String> {
				@Override
				public int compare(String o1, String o2) {
					if (o1.length() < o2.length())
						return 1;
					else if (o1.length() > o2.length())
						return -1;
					else
						return o1.compareTo(o2);
				}
			}
			
			public void print() {
				for (String word: wordsUnsorted)
					words.add(word);
				Collections.sort(words, new LengthComparator());
				for (String word: words)
					System.out.println(word);
			}
		}
		MyPathCallback callback = new MyPathCallback(T);
		
		Search S = new Search(G, predicate, callback);
		S.visit();
		callback.print();
	}
	
}
