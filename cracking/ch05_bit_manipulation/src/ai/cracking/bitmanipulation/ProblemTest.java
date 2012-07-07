package ai.cracking.bitmanipulation;

import static org.junit.Assert.*;
import org.junit.Test;

public class ProblemTest {

	// ------------------------------------------------------------------
	//	Problem 1.
	// ------------------------------------------------------------------
	@Test
	public void testApplySubstring1() {
		long N = Long.parseLong("10000000000", 2);
		long M = Long.parseLong("10101", 2);
		int i = 2;
		int j = 6;
		int expectedOutput = Integer.parseInt("10001010100", 2);
		assertEquals(expectedOutput, Problem1.applySubstring(N, M, i, j));
	}

	@Test
	public void testApplySubstring2() {
		long N = Long.parseLong("11111111111111111111111111111111", 2);
		long M = Long.parseLong("0", 2);
		int i = 0;
		int j = 31;
		int expectedOutput = Integer.parseInt("0", 2);
		assertEquals(expectedOutput, Problem1.applySubstring(N, M, i, j));
	}
	// ------------------------------------------------------------------
	
	// ------------------------------------------------------------------
	//	Problem 2.
	// ------------------------------------------------------------------
	@Test
	public void testPrintBinaryString1() {
		double input = 3.25;
		String expectedOutput = "11.01";
		assertEquals(expectedOutput, Problem2.printBinaryString(input));
	}
	
	@Test
	public void testPrintBinaryString2() {
		double input = 1.1;
		String expectedOutput = "ERROR";
		assertEquals(expectedOutput, Problem2.printBinaryString(input));
	}

	// ------------------------------------------------------------------
	
}
