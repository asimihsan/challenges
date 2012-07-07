package ai.topcoder.TurtleSpy;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import java.lang.Math;

public class TurtleSpyTest {
	TurtleSpy tester;
	static double PRECISION = Math.pow(10, -9);
	
	@Before
	public void setUp() {
		tester = new TurtleSpy();
	}
	
	@Test
	public void testLocation() {
		TurtleSpy.Location origin = new TurtleSpy.Location(0, 0, 0);
		TurtleSpy.Location location = new TurtleSpy.Location(100, -100, 225);
		double distance = location.distance(origin);
		assertEquals(141.4213562373095, distance, PRECISION);
	}

	@Test
	public void testEvaluateDistance1() {		
		String[] commands = (String[]) Arrays.asList("forward 100", "left 90", "backward 100").toArray();
		Double distance = tester.evaluateDistance(commands);
		assertEquals(141.4213562373095, distance, PRECISION);

	}
	
	@Test
	public void testMaxDistance1() {
		String[] commands = (String[]) Arrays.asList("forward 100", "backward 100", "left 90").toArray();
		Double distance = tester.maxDistance(commands);
		assertEquals(141.4213562373095, distance, PRECISION);
	}

}
