package ai.exposed;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void testEmpty() {
		LinkedList<Integer> test = new LinkedList<Integer>();
		assertEquals(test.isEmpty(), true);
		assertEquals(test.size(), 0);
	}
	
	@Test
	public void testOnePushPop() {
		LinkedList<Integer> test = new LinkedList<Integer>();
		Integer datum = 1;
		test.push(datum);
		assertEquals(test.isEmpty(), false);
		assertEquals(test.size(), 1);
		
		Integer datumOut = test.pop();
		assertEquals(datum, datumOut);
		assertEquals(test.isEmpty(), true);
		assertEquals(test.size(), 0);
	}
	
	@Test
	public void testTwoPushPop() {
		LinkedList<Integer> test = new LinkedList<Integer>();
		Integer datum1 = 1;
		Integer datum2 = 2;
		
		test.push(datum1);
		assertEquals(test.isEmpty(), false);
		assertEquals(test.size(), 1);
		
		test.push(datum2);
		assertEquals(test.isEmpty(), false);
		assertEquals(test.size(), 2);		
		
		Integer datum2Out = test.pop();
		assertEquals(test.isEmpty(), false);
		assertEquals(test.size(), 1);
		assertEquals(datum2Out, datum2);
		
		Integer datum1Out = test.pop();
		assertEquals(test.isEmpty(), true);
		assertEquals(test.size(), 0);
		assertEquals(datum1Out, datum1);
	}

}
