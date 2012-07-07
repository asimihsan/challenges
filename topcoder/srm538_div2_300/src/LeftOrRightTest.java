import static org.junit.Assert.*;

import org.junit.Test;


public class LeftOrRightTest {
	LeftOrRight test = new LeftOrRight();
	
	@Test
	public void testMaxDistance1() {
		assertEquals(test.maxDistance("LLLRLRRR"), 3);
	}

	@Test
	public void testMaxDistance2() {
		assertEquals(test.maxDistance("R???L"), 4);
	}
	
	@Test
	public void testMaxDistance3() {
		assertEquals(test.maxDistance("??????"), 6);
	}
	
	@Test
	public void testMaxDistance4() {
		assertEquals(test.maxDistance("LL???RRRRRRR???"), 11);
	}

	
	@Test
	public void testMaxDistance5() {
		assertEquals(test.maxDistance("L?L?"), 4);
	}

	@Test
	public void testMaxDistance6() {
		assertEquals(test.maxDistance("LRLRRRLRRLL?RRR?L??L?R???L?R?RLL"), 14);
	}	
	
}
