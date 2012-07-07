package ai.topcoder.TurtleSpy;

public class TurtleSpy {
	
	// -----------------------------------------------------------------------
	//	Points and constants.
	// -----------------------------------------------------------------------
	protected static class Location {
		private static double DEGREES_TO_RADIANS = Math.PI / 180;
		
		private double x;
		private double y;
		private double theta;
		
		Location(double x, double y, double theta) { this.x = x; this.y = y; this.theta = theta; }
		Location(Integer x, Integer y, Integer theta) { this.x = x.doubleValue(); this.y = y.doubleValue(); this.theta = theta.doubleValue(); }
		Location(Location other) { this.x = other.x; this.y = other.y; this.theta = other.theta; }
		
		public Double getX() { return x; }
		public Double getY() { return y; }
		public Double getTheta() { return theta; }
		
		public void setX(double x) {this.x = x; }
		public void setY(double y) { this.y = y; }
		public void setTheta(double theta) { this.theta = theta; }
		public void move(double z) {
			x += z * Math.sin(DEGREES_TO_RADIANS * theta);
			y += z * Math.cos(DEGREES_TO_RADIANS * theta);
		}
		
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Location)) return false;
			Location other2 = (Location)other;
			return (x == other2.getX() &&
					y == other2.getY() &&
					theta == other2.getTheta());
		}
		@Override
		public int hashCode() {
			long xl = Double.doubleToLongBits(x);
			long yl = Double.doubleToLongBits(y);
			long thetal = Double.doubleToLongBits(theta);
			
			int rv = 17;
			rv = 31 * rv + (int)(xl ^ (xl >>> 32));
			rv = 31 * rv + (int)(yl ^ (yl >>> 32));
			rv = 31 * rv + (int)(thetal ^ (thetal >>> 32));
			return rv;
			
		}
		@Override
		public String toString() {
			StringBuilder rv = new StringBuilder();
			rv.append("{Location: ");
			rv.append("x=" + x + ", ");
			rv.append("y=" + y + ", ");
			rv.append("theta=" + theta);
			rv.append("}");
			return rv.toString();
		}
		
		public double distance(Location other) {
			return distance(this, other);
		}
		
		static double distance(Location point1, Location point2) {
			double xDiff = point1.getX() - point2.getX();
			double yDiff = point1.getY() - point2.getY();
			double rv = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
			return rv;			
		}
		
		public void applyCommand(String command) {
			/*
			 * 'right X'
			 * 'left X'
			 * 'forward X'
			 * 'backward X'
			 * 
			 * right/left are turning commands.
			 * forward/backward are displacement commands.
			 */
			
			// ---------------------------------------------------------------
			//	Initialize local variables.
			// ---------------------------------------------------------------
			String action;
			Double value;
			// ---------------------------------------------------------------
			
			// ---------------------------------------------------------------
			// Validate inputs
			// ---------------------------------------------------------------
			String[] elems = command.split("\\s+");
			if (elems.length != 2) 
				throw new IllegalArgumentException("invalid command; more than two elements.");
			action = elems[0];
			if ((!action.equals("right")) &&
				(!action.equals("left")) &&
				(!action.equals("forward")) &&
				(!action.equals("backward")))
				throw new IllegalArgumentException("invalid command; unrecognised token: " + action);
			try {
				value = Double.parseDouble(elems[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("invalid command; second element unparsable: " + elems[1]);
			}
			// ---------------------------------------------------------------
			
			// ---------------------------------------------------------------
			// Apply inputs
			// ---------------------------------------------------------------
			if (action.equals("right"))          setTheta(theta + value); 
			else if (action.equals("left"))      setTheta(theta - value);
			else if (action.equals("forward"))   move(value); 
			else if (action.equals("backward"))  move(-value);
			// ---------------------------------------------------------------
		}
	}
	// -----------------------------------------------------------------------
	private Location ORIGIN = new Location(0, 0, 0);
		
	public double maxDistance(String[] commands) {
		return 0;
	}
	
	protected double evaluateDistance(String[] commands) {
		Location location = new Location(ORIGIN);
		for (String command: commands) {
			location.applyCommand(command);
		}
		return location.distance(ORIGIN);
	}
	
}
