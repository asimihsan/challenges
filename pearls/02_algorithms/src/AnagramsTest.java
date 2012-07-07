import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class AnagramsTest {
	Anagrams tester = new Anagrams();
	
	@Test
	public void testGetAnagrams() {
		List<String> anagrams = tester.getAnagrams("caret");
		assertTrue(anagrams.contains("cater"));
		assertTrue(anagrams.contains("crate"));
		assertTrue(anagrams.contains("react"));
		assertTrue(anagrams.contains("recta"));
		assertTrue(anagrams.contains("trace"));
	}

}
