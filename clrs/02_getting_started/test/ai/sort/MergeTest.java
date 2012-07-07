package ai.sort;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class MergeTest {
	Comparator<Integer> comparatorAscending = new IntegerComparatorAscending();
	Comparator<Integer> comparatorDescending = new IntegerComparatorDescending();
	Merge<Integer> sorter = new Merge<Integer>();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBasicSortAscending() {
		List<Integer> inputList = Arrays.asList(1, 3, 9, 6, 5, 2, 8, 10);
		List<Integer> expectedOutputList = Arrays.asList(1, 2, 3, 5, 6, 8, 9, 10);
		sorter.sort(inputList, comparatorAscending);
		assertArrayEquals(expectedOutputList.toArray(), inputList.toArray());
	}
	
	@Test
	public void testBasicSortDescending() {
		List<Integer> inputList = Arrays.asList(1, 3, 9, 1, 5, 2);
		List<Integer> expectedOutputList = Arrays.asList(9, 5, 3, 2, 1, 1);
		sorter.sort(inputList, comparatorDescending);
		assertArrayEquals(expectedOutputList.toArray(), inputList.toArray());
	}	
	
	@Test
	public void testEmptyList() {
		List<Integer> inputList = Collections.emptyList();
		sorter.sort(inputList, comparatorAscending);
		assertArrayEquals(inputList.toArray(), Collections.emptyList().toArray());
	}
	
	@Test
	public void testOneElementList() {
		List<Integer> inputList = Arrays.asList(1);
		List<Integer> expectedOutputList = Arrays.asList(1);
		sorter.sort(inputList, comparatorAscending);
		assertArrayEquals(expectedOutputList.toArray(), inputList.toArray());
	}
	
	@Test
	public void testTwoElementListAscending() {
		List<Integer> inputList = Arrays.asList(1, 2);
		List<Integer> expectedOutputList = Arrays.asList(1, 2);
		sorter.sort(inputList, comparatorAscending);
		assertArrayEquals(expectedOutputList.toArray(), inputList.toArray());
	}
	
	@Test
	public void testTwoElementListDescending() {
		List<Integer> inputList = Arrays.asList(1, 2);
		List<Integer> expectedOutputList = Arrays.asList(2, 1);
		sorter.sort(inputList, comparatorDescending);
		assertArrayEquals(expectedOutputList.toArray(), inputList.toArray());
	}	
	
}
