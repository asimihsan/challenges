package ai.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;

public class RedBlackTreeTest {
	RedBlackTree<String, Integer> tester;

	/**
	 * Convert an iterable to a list.
	 * 
	 * @param iterable Iterable to iterate over.
	 * @return List<E> of elements.
	 */
	private static <E> List<E> toList(Iterable<E> iterable) {
		if (iterable instanceof List)
			return (List<E>) iterable;
		if (iterable == null)
			return Collections.emptyList();
		List<E> rv = new LinkedList<E>();
		for (E e: iterable)
			rv.add(e);
		if (rv.size() == 0)
			return Collections.emptyList();
		return rv;
	}
	
	@Before
	public void setUp() {
		tester = new RedBlackTree<String, Integer>();
	}

	@Test
	public void testIsRed() {
		RedBlackTree<String, Integer>.Node node1 = tester.new Node("E", 1, 0, RedBlackTree.Color.RED);
		RedBlackTree<String, Integer>.Node node2 = tester.new Node("E", 1, 0, RedBlackTree.Color.BLACK);
		assertTrue(tester.isRed(node1));
		assertTrue(!tester.isRed(node2));
		assertTrue(!tester.isRed(null));
	}
	
	/**
	 * Test left rotation works on a toy example.
	 */
	@Test
	public void rotateLeft1() {
		// Setup
		RedBlackTree<String, Integer>.Node nodeE = tester.new Node("E", 1, 6, RedBlackTree.Color.BLACK);
		RedBlackTree<String, Integer>.Node nodeC = tester.new Node("C", 1, 2, RedBlackTree.Color.BLACK);
		RedBlackTree<String, Integer>.Node nodeA = tester.new Node("A", 1, 1, RedBlackTree.Color.RED);
		RedBlackTree<String, Integer>.Node nodeR = tester.new Node("R", 1, 3, RedBlackTree.Color.RED);
		RedBlackTree<String, Integer>.Node nodeH = tester.new Node("H", 1, 1, RedBlackTree.Color.BLACK);
		RedBlackTree<String, Integer>.Node nodeS = tester.new Node("S", 1, 1, RedBlackTree.Color.BLACK);
		nodeE.left = nodeC;
		nodeC.left = nodeA;
		nodeE.right = nodeR;
		nodeR.left = nodeH;
		nodeR.right = nodeS;
		
		// Rotate left of H; link is right-leaning red.
		RedBlackTree<String, Integer>.Node nodeERotated = tester.rotateLeft(nodeE);
		assertEquals(nodeERotated, nodeR);
		nodeR = nodeERotated;
		
		// R OK?
		assertEquals("R", nodeR.key);
		assertEquals(RedBlackTree.Color.BLACK, nodeR.color);
		assertEquals(6, nodeR.N);
		assertEquals(nodeS, nodeR.right);
		assertEquals(nodeE, nodeR.left);
		
		// E OK?
		assertEquals("E", nodeE.key);
		assertEquals(RedBlackTree.Color.RED, nodeE.color);
		assertEquals(4, nodeE.N);
		assertEquals(nodeH, nodeE.right);
		assertEquals(nodeC, nodeE.left);
		
		// H OK?
		assertEquals("H", nodeH.key);
		assertEquals(RedBlackTree.Color.BLACK, nodeH.color);
		assertEquals(1, nodeH.N);
		assertEquals(null, nodeH.left);
		assertEquals(null, nodeH.right);
		
		// C OK?
		assertEquals("C", nodeC.key);
		assertEquals(RedBlackTree.Color.BLACK, nodeC.color);
		assertEquals(2, nodeC.N);
		assertEquals(nodeA, nodeC.left);
		assertEquals(null, nodeC.right);
		
		// A OK?
		assertEquals("A", nodeA.key);
		assertEquals(RedBlackTree.Color.RED, nodeA.color);
		assertEquals(1, nodeA.N);
		assertEquals(null, nodeA.left);
		assertEquals(null, nodeA.right);
	} // rotateLeft1
	
	/**
	 * Test right rotation works on a toy example.
	 */
	@Test
	public void rotateRight1() {
		// Setup
		RedBlackTree<String, Integer>.Node nodeR = tester.new Node("R", 1, 6, RedBlackTree.Color.BLACK);
		RedBlackTree<String, Integer>.Node nodeE = tester.new Node("E", 1, 4, RedBlackTree.Color.RED);
		RedBlackTree<String, Integer>.Node nodeC = tester.new Node("C", 1, 2, RedBlackTree.Color.BLACK);
		RedBlackTree<String, Integer>.Node nodeA = tester.new Node("A", 1, 1, RedBlackTree.Color.RED);
		RedBlackTree<String, Integer>.Node nodeH = tester.new Node("H", 1, 1, RedBlackTree.Color.BLACK);
		RedBlackTree<String, Integer>.Node nodeS = tester.new Node("S", 1, 1, RedBlackTree.Color.BLACK);
		nodeR.left = nodeE;
		nodeR.right = nodeS;
		nodeE.left = nodeC;
		nodeE.right = nodeH;
		nodeC.left = nodeA;
		nodeE.right = nodeH;
		
		
		// Rotate right of R
		RedBlackTree<String, Integer>.Node nodeRRotated = tester.rotateRight(nodeR);
		assertEquals(nodeRRotated, nodeE);
		nodeE = nodeRRotated;

		// E OK?
		assertEquals("E", nodeE.key);
		assertEquals(RedBlackTree.Color.BLACK, nodeE.color);
		assertEquals(6, nodeE.N);
		assertEquals(nodeR, nodeE.right);
		assertEquals(nodeC, nodeE.left);
		
		// R OK?
		assertEquals("R", nodeR.key);
		assertEquals(RedBlackTree.Color.RED, nodeR.color);
		assertEquals(3, nodeR.N);
		assertEquals(nodeS, nodeR.right);
		assertEquals(nodeH, nodeR.left);
		
		
		// H OK?
		assertEquals("H", nodeH.key);
		assertEquals(RedBlackTree.Color.BLACK, nodeH.color);
		assertEquals(1, nodeH.N);
		assertEquals(null, nodeH.left);
		assertEquals(null, nodeH.right);
		
		// C OK?
		assertEquals("C", nodeC.key);
		assertEquals(RedBlackTree.Color.BLACK, nodeC.color);
		assertEquals(2, nodeC.N);
		assertEquals(nodeA, nodeC.left);
		assertEquals(null, nodeC.right);
		
		// A OK?
		assertEquals("A", nodeA.key);
		assertEquals(RedBlackTree.Color.RED, nodeA.color);
		assertEquals(1, nodeA.N);
		assertEquals(null, nodeA.left);
		assertEquals(null, nodeA.right);
	} // rotateRight1
	
	private void putKeysGetKeys(List<String> inputKeys) {
		List<String> inputKeysSorted = new ArrayList<String>(inputKeys);
		System.out.println("\nGoing to insert: " + inputKeys);
		Collections.sort(inputKeysSorted);
		Integer value = 1;
		for (String key: inputKeys)
			tester.put(key,  value);
		
		Iterable<String> outputKeys = tester.keys();
		assertArrayEquals(inputKeysSorted.toArray(),
						  toList(outputKeys).toArray());
		
		System.out.println("After insertion of: " + inputKeys);
		System.out.println("RBT root is: " + tester.root);
		System.out.println("RBT is: ");
		for(RedBlackTree<String, Integer>.Node node: toList(tester.nodes())) {
			System.out.println(node);
		}
	}
	
	@Test
	public void emptyTree() {
		List<String> inputKeys = Arrays.asList();
		putKeysGetKeys(inputKeys);
	}
	
	@Test
	public void putOneGetOne() {
		List<String> inputKeys = Arrays.asList("R");
		putKeysGetKeys(inputKeys);
	}
	
	@Test
	public void putThreeGetThree() {
		List<String> inputKeys = Arrays.asList("E", "C", "R");
		putKeysGetKeys(inputKeys);
	}
	
	@Test
	public void putSixGetSix() {
		List<String> inputKeys = Arrays.asList("E", "S", "H", "A", "C", "R");
		putKeysGetKeys(inputKeys);
	}
	
	@Test
	public void putTwelveGetTwelve() {
		List<String> inputKeys = Arrays.asList("E", "S", "H", "A", "C", "R",
											   "Q", "D", "Y", "Z", "I", "O");
		putKeysGetKeys(inputKeys);		
	}
	
	@Test
	public void putTwelveSorted() {
		List<String> inputKeys = Arrays.asList("E", "S", "H", "A", "C", "R",
				   "Q", "D", "Y", "Z", "I", "O");
		Collections.sort(inputKeys);
		putKeysGetKeys(inputKeys);		
	}
	
}
