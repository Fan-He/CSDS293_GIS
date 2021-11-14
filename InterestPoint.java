package gis;

import java.math.BigDecimal;

/**
 * 
 * @author Xiaofan He
 *
 * @param <M>
 */
public record InterestPoint<M>(Coordinate coordinate, M marker) {
	
	/**
	 * Validate the interest point
	 * @return the interest point if valid
	 */
	public final InterestPoint<M> validate() {
		//Validate the coordinate
		Coordinate.validate(coordinate);
		//If the marker is null, the interestPoint is invalid
		if(marker == null)
			throw new NullPointerException("Invalid marker. ");
		return this;
	}
	
	/**
	 * Validate the InterestPoint
	 * @param interestPoint
	 * @return the interest point if valid
	 */
	public static final InterestPoint<?> validate(InterestPoint<?> interestPoint) {
		return interestPoint.validate();
	}
	
	/**
	 * Get the x coordinate
	 * @return the x coordinate
	 */
	public BigDecimal x() {
		this.validate();
		return coordinate.x();
	}
	
	/**
	 * Get the y coordinate
	 * @return the y coordinate
	 */
	public BigDecimal y() {
		this.validate();
		return coordinate.y();
	}
	
	/**
	 * Check if this InterestPoint has the input marker
	 * @param marker
	 * @return boolean result
	 */
	public boolean hasMarker(M marker) {
		return this.marker.equals(marker);
	}
	
	/**
	 * Describes this InterestPoint
	 * @return rep the string that describes this InterestPoint
	 */
	public String toString() {
		this.validate();
		String rep = "Coordinate: " + coordinate.toSimpleString() + " Marker: " + marker.toString();
		return rep;
	}
	

	public static void main(String[] args) {
		Coordinate c1 = new Coordinate(new BigDecimal(4.0), new BigDecimal(10.5));
		InterestPoint<String> po = new InterestPoint<String>(c1, null);
		System.out.println(po.toString());
	}
	
}
