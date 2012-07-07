import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import java.util.List;

public class QuickTest {
	Quick<Integer> tester = new Quick<Integer>();

	@Test
	public void testPartition() {
		List<Integer> input = Arrays.asList(5, 7, 9, 1, 3, 4);
		List<Integer> expectedOutput = Arrays.asList(1, 3, 4, 5, 7, 9);
		int newPivot = tester.partition(input, 0, input.size() - 1);
		assertEquals(2, newPivot);
		assertArrayEquals(expectedOutput.toArray(), input.toArray());
	}

	@Test
	public void testQuicksort() {
		List<Integer> input = Arrays.asList(7, 3, 1, 0, 2, 9, 10, 1);
		List<Integer> expectedOutput = Arrays.asList(0, 1, 1, 2, 3, 7, 9, 10);
		tester.quicksort(input, 0, input.size() - 1);
		assertArrayEquals(expectedOutput.toArray(), input.toArray());
	}
	
	@Test
	public void testRandomizedQuicksort() {
		List<Integer> input = Arrays.asList(7, 3, 1, 0, 2, 9, 10, 1);
		List<Integer> expectedOutput = Arrays.asList(0, 1, 1, 2, 3, 7, 9, 10);
		for (int i = 0; i < 10; i++) {
			tester.quicksort(input, 0, input.size() - 1);
			assertArrayEquals(expectedOutput.toArray(), input.toArray());
		}
	}

}
