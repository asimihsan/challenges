package ai.mlfsr;

import static org.junit.Assert.*;

import org.junit.Test;
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;

public class MlfsrTest {

	@Test
	public void testNext() {
		int seed = 0xACE1;
		Mlfsr16 tester = new Mlfsr16(seed);
		assertEquals("Next value from 0xACE1", 0x5670, tester.next());
		
	}
	
	@Test
	public void testExhaustion() {
		Mlfsr16 tester = new Mlfsr16(1);
		int maximum = (int)(Math.pow(2, 16) - 1);
		for(int i = 0; i < maximum; i++) {
			int value = tester.next();
			assert(value != -1);
		}
		assertEquals(tester.next(), -1);
	}
	
	@Test
	public void testUniqueness() {
		Mlfsr16 tester = new Mlfsr16(1);
		int maximum = (int)(Math.pow(2, 16) - 1);
		Set<Integer> intsSeen = new HashSet<Integer>();
		for (int i = 0; i < maximum; i++) {
			Integer value = tester.next();
			assertFalse(intsSeen.contains(value));
			intsSeen.add(value);
		}
	}

} // public class MlfsrTest
