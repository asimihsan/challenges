import java.util.Map;
import java.util.HashMap;

public class LeftOrRight {
	static String sL = new String("L");
	static String sR = new String("R");
	static String sQ = new String("?");
	
	class InputAndPosition {
		public String input;
		public Integer position;
		public InputAndPosition(String input, Integer position) {
			this.input = input;
			this.position = position;
		}
		
		@Override
		public int hashCode() {
			return input.hashCode() ^ position.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof InputAndPosition) {
				InputAndPosition other = (InputAndPosition)o;
				return (input.equals(other.input) && position.equals(other.position) );
			}
			return false;
		}
	}
	
	static Map<InputAndPosition, Integer> inputToMaximumPosition = new HashMap<InputAndPosition, Integer>();
	
	public int maxDistance(String input) {
		int rv = calculateDistance(input,
							       0,
							       0);
		return Math.abs(rv); 
	}
	
	private int calculateDistance(String input,
								  int position,
								  int maximumPosition) {
		//System.out.println("calculateDistance. input: " + input + ", position: " + position + ", maximumPosition: " + maximumPosition);
		InputAndPosition key = new InputAndPosition(input, position);
		if (inputToMaximumPosition.containsKey(key))
			return inputToMaximumPosition.get(key);
		if (input.length() == 0) {
			return maximumPosition;
		}
		String rest = input.substring(1);
		String current = Character.toString(input.charAt(0));
		//System.out.println("rest: " + rest + ", current: " + current);
		if (current.equals(sQ)) {
			String sLeft = sL + rest;
			int maximumLeft = calculateDistance(sLeft.toString(),
												position,
												maximumPosition);
			
			String sRight = sR + rest;
			int maximumRight = calculateDistance(sRight.toString(),
												 position,
												 maximumPosition);
			
			if (Math.abs(maximumLeft) > Math.abs(maximumRight)) {
				current = sL;
				inputToMaximumPosition.put(new InputAndPosition(sQ + rest, position),
										   maximumLeft);
			} else {
				current = sR;
				inputToMaximumPosition.put(new InputAndPosition(sQ + rest, position),
										   maximumRight);
			}
			//System.out.println("decided to go: " + current);
		}
		int positionNew = position;
		int maximumPositionNew = maximumPosition;
		if (current.equals(sL))
			positionNew -= 1;
		else if (current.equals(sR))
			positionNew += 1;
		if (Math.abs(positionNew) > Math.abs(maximumPosition))
			maximumPositionNew = positionNew;
		return calculateDistance(rest,
				                 positionNew,
				                 maximumPositionNew);
	}
}
