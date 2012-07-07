import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import java.io.FileWriter;
import java.io.BufferedWriter;

public class StoreCreditTest {
	StoreCredit tester;
	
	@Before
	public void setUp() throws Exception {
		tester = new StoreCredit();
	}
	
	@Test
	public void testInsertSort() {
		List<Integer> unsorted = Arrays.asList(9, 10, 1, 2, 56, 4, 5, 11, 3);
		List<Integer> sorted = Arrays.asList(1, 2, 3, 4, 5, 9, 10, 11, 56);
		StoreCredit.insertionSort(unsorted, 0, unsorted.size() - 1);
		assertArrayEquals(sorted.toArray(), unsorted.toArray());
	}

	@Test
	public void testMergeSort() {
		String line = "9 10 1 2 56 4 5 11 3";
		List<Integer> sorted = Arrays.asList(1, 2, 3, 4, 5, 9, 10, 11, 56);
		List<Integer> output = StoreCredit.lineToSortedInts(line);
		assertArrayEquals(sorted.toArray(), output.toArray());
	}
	
	@Test
	public void testSort2() {
		String line = "20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1";
		List<Integer> sorted = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
		List<Integer> output = StoreCredit.lineToSortedInts(line);
		assertArrayEquals(sorted.toArray(), output.toArray());
	}
	
	@Test
	public void solveProblem1() {
		String line = "5 75 25";
		int credit = 100;
		List<Integer> expectedAnswer = Arrays.asList(2, 3);
		List<Integer> answer = (new StoreCredit.Problem(credit, line)).solveProblem();
		assertArrayEquals(expectedAnswer.toArray(), answer.toArray());		
	}
	
	@Test
	public void solveProblem2() {
		String line = "150 24 79 50 88 345 3";
		int credit = 200;
		List<Integer> expectedAnswer = Arrays.asList(1, 4);
		List<Integer> answer = (new StoreCredit.Problem(credit, line)).solveProblem();
		assertArrayEquals(expectedAnswer.toArray(), answer.toArray());
	}
	
	@Test
	public void solveProblem3() {
		String line = "2 1 9 4 4 56 90 3";
		int credit = 8;
		List<Integer> expectedAnswer = Arrays.asList(4, 5);
		List<Integer> answer = (new StoreCredit.Problem(credit, line)).solveProblem();
		assertArrayEquals(expectedAnswer.toArray(), answer.toArray());
	}
	
	@Test
	public void testFile1() throws IOException {
		System.out.println("testFile1");
		List<String> output = StoreCredit.convertFileToOutput("/Users/ai/Programming/javakata/codejam/store_credit/src/small_example.txt");
		for (String elem: output)
			System.out.println(elem);
	}

	@Test
	public void testFile2() throws IOException {
		System.out.println("A-small-practice.in\n");
		List<String> output = StoreCredit.convertFileToOutput("/Users/ai/Programming/javakata/codejam/store_credit/src/A-small-practice.in");

		FileWriter fw = new FileWriter("/Users/ai/Programming/javakata/codejam/store_credit/src/A-small-practice.out");
		BufferedWriter bw = new BufferedWriter(fw);
		for (String elem: output)
			bw.write(elem + "\n");
		bw.close();
	}

	@Test
	public void testFile3() throws IOException {
		System.out.println("A-large-practice.in\n");
		List<String> output = StoreCredit.convertFileToOutput("/Users/ai/Programming/javakata/codejam/store_credit/src/A-large-practice.in");

		FileWriter fw = new FileWriter("/Users/ai/Programming/javakata/codejam/store_credit/src/A-large-practice.out");
		BufferedWriter bw = new BufferedWriter(fw);
		for (String elem: output)
			bw.write(elem + "\n");
		bw.close();
	}
}
