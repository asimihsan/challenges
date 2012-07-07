package ai.heapsort;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HeapTest {
	Heap<Integer> tester = new Heap<Integer>();
	Heap.IntegerComparator comp = new Heap.IntegerComparator();

	@Test
	public void testMaxHeapify() {
		System.out.println("testMaxHeapify entry.");
		List<Integer> before = Arrays.asList(16, 4, 10, 14, 7, 9, 3, 2, 8, 1);
		List<Integer> expected = Arrays.asList(16, 14, 10, 8, 7, 9, 3, 2, 4, 1);
		List<Integer> after = new ArrayList<Integer>(before.size());
		for(Integer i: before) after.add(i);
		tester.maxHeapify(after, comp, 1, after.size());
		System.out.println("after: " + after);
		assertArrayEquals(expected.toArray(), after.toArray());
	}

	@Test
	public void testBuildMaxHeap() {
		System.out.println("testBuildMaxHeap entry.");
		List<Integer> before = Arrays.asList(4, 1, 3, 2, 16, 9, 10, 14, 8, 7);
		List<Integer> expected = Arrays.asList(16, 14, 10, 8, 7, 9, 3, 2, 4, 1);
		List<Integer> after = new ArrayList<Integer>(before.size());
		for(Integer i: before) after.add(i);
		tester.buildMaxHeap(after, comp, after.size());
		System.out.println("after: " + after);
		assertArrayEquals(expected.toArray(), after.toArray());
	}

	@Test
	public void testHeapsort() {
		System.out.println("testHeapsort entry.");
		List<Integer> before = Arrays.asList(4, 1, 3, 2, 16, 9, 10, 14, 8, 7);
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 7, 8, 9, 10, 14, 16);
		List<Integer> after = new ArrayList<Integer>(before.size());
		for(Integer i: before) after.add(i);
		Heap.heapsort(after);
		System.out.println("after: " + after);
		assertArrayEquals(expected.toArray(), after.toArray());		
	}

}
