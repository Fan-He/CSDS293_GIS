package gis;

import java.math.BigDecimal;


/**
 * Coordinate
 * @author Xiaofan He
 *
 */
public record Coordinate(BigDecimal x, BigDecimal y) implements Comparable<Coordinate>{
	
	public static final Coordinate ORIGIN = new Coordinate(new BigDecimal(0.0), new BigDecimal(0.0));
	
	/**
	 * @param c1 the second coordinate that this is comparing to
	 * @return whether this >, =, or < c2
	 * Compare the two coordinates and return the result of comparison
	 */
	@Override
	public int compareTo(Coordinate c2) {
		//Validate c2
		Coordinate.validate(c2);
		//If x1<x2 or x1==x2 and y1<y2, c1 would be smaller than c2
		if(x.compareTo(c2.x()) < 0 || (x.compareTo(c2.x()) == 0 && y.compareTo(c2.y()) < 0))
			return -1;
		//If x1 == x2 and y1 == y2, c1 is equal to c2
		else if (x.compareTo(c2.x()) == 0 && y.compareTo(c2.y()) == 0) {
			return 0;
		}
		else
			return 1;
	}
	
	/**
	 * Validate the coordinate
	 * Throw an exception if invalid
	 * @return the coordinate
	 */
	public final Coordinate validate() {
		//If either x is null or y is null, the coordinate is invalid
		if (x == null || y == null)
			throw new NullPointerException("Invalid coordinate: invalid longitude or latitude. ");
		return this;
	}
	
	/**
	 * Validate the coordinate
	 * and throw an exception if invalid
	 * @param coordinate
	 * @return
	 */
	public static final Coordinate validate(Coordinate coordinate) {
		//If the input coordinate is null, it would be invalid
		if(coordinate == null)
			throw new NullPointerException("Invalid coordinate. ");
		return coordinate.validate();
	}
	
	/**
	 * Describe the coordinate
	 * @return the String that describes the coordinate
	 */
	public String toSimpleString() {
		validate();
		return "(" + x + ", " + y + ")";
	}
	
	public static void main(String[] args) {
		Coordinate c1 = new Coordinate(new BigDecimal(4.5), new BigDecimal(8.0));
		Coordinate c2 = new Coordinate(new BigDecimal("5"), new BigDecimal(10.5));
		System.out.println(c2.toSimpleString());
		System.out.println(c1.compareTo(c2));
	}
}
