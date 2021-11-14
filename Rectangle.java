package gis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Rectangle class
 * @author Xiaofan He
 *
 */
public record Rectangle(Coordinate bottomLeft, Coordinate topRight) {
	
	
	/**
	 * Validate the rectangle
	 * @return the rectangle if valid, and throw an exception if invalid
	 */
	public final Rectangle validate() {
		//Validate both bottom left and top right coordinate
		Coordinate.validate(bottomLeft);
		Coordinate.validate(topRight);
		//If bottomLeft is not on the left and below topRight, the rectangle is invalid and throw an exception
		if((bottomLeft.x().compareTo(topRight.x()) > 0) || (bottomLeft.y().compareTo(topRight.y()) > 0))
			throw new NullPointerException("Invalid rectangle: invalid bottomLeft and topRight coordinate combination. ");
		else
			return this;
	}
	
	/**
	 * Validate the rectangle
	 * @param rectangle
	 * @return the input rectangle if valid, and throw an exception if invalid
	 */
	public static final Rectangle validate(Rectangle rectangle) {
		return rectangle.validate();
	}
	
	/**
	 * Get the left coordinate of the rectangle
	 * @return the left coordinate value
	 */
	public final BigDecimal left() {
		this.validate();
		return bottomLeft.x();
	}
	
	/**
	 * Get the right coordinate of the rectangle
	 * @return the right coordinate value
	 */
	public final BigDecimal right() {
		this.validate();
		return topRight.x();
	}
	
	/**
	 * Get the bottom coordinate of the rectangle
	 * @return the bottom coordinate value
	 */
	public final BigDecimal bottom() {
		this.validate();
		return bottomLeft.y();
	}
	
	/**
	 * Get the top coordinate of the rectangle
	 * @return the top coordinate value
	 */
	public final BigDecimal top() {
		this.validate();
		return topRight.y();
	}
	
	/**
	 * Get the top left coordinate of the rectangle
	 * @return the top left coordinate value
	 */
	Coordinate topLeft() {
		return new Coordinate(this.left(), this.top());
	}
	
	/**
	 * Get the bottom right coordinate of the rectangle
	 * @return the bottom right coordinate value
	 */
	Coordinate bottomRight() {
		return new Coordinate(this.right(), this.bottom());
	}
	
	/**
	 * Check if the input yCoordinate is within the rectangle's bottom and top
	 * @param yCoordinate
	 * @return
	 */
	private final boolean yWithinRectangle(BigDecimal yCoordinate) {
		if(yCoordinate.compareTo(this.top()) < 0 && this.bottom().compareTo(yCoordinate) <= 0 )
			return true;
		else
			return false;
	}
	
	/**
	 * Check if the input xCoordinate is within the rectangle's left and right
	 * @param xCoordinate
	 * @return
	 */
	private final boolean xWithinRectangle(BigDecimal xCoordinate) {
		if(xCoordinate.compareTo(this.right()) < 0 && this.left().compareTo(xCoordinate) <= 0 )
			return true;
		else
			return false;
	}
	
	
	/**
	 * Get the list of the coordinates of the rectangle
	 * @return
	 */
	private List<Coordinate> getCoordinates() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(this.bottomLeft());
		coordinates.add(this.topRight());
		coordinates.add(this.topLeft());
		coordinates.add(this.bottomRight());
		return coordinates;
	}
	
	/**
	 * 
	 * @param rectangle
	 * @return
	 */
	final boolean isOverlapping(Rectangle rectangle) {
		List<Coordinate> coordinates = rectangle.getCoordinates();
		for(Coordinate coordinate : coordinates) {
			if(this.withinRectangle(coordinate))
				return true;
			else
				;
		}
		return false;
	}
	
	/**
	 * Check if the input coordinate is within the rectangle
	 * @param coordinate
	 * @return whether the coordinate is within rectangle
	 */
	final boolean withinRectangle(Coordinate coordinate) {
		if(this.yWithinRectangle(coordinate.y()) && this.xWithinRectangle(coordinate.x()))
			return true;
		else
			return false;
	}
	
	/**
	 * Filter the points that are within the rectangle
	 * @param coordinates
	 * @return the list of coordinates that are within the rectangle
	 */
	List<Coordinate> allPointsInRectangle(List<Coordinate> coordinates){
		List<Coordinate> list = new ArrayList<Coordinate>();
		for(Coordinate coordinate : coordinates) {
			if(this.withinRectangle(coordinate))
				list.add(coordinate);
			else
				;
		}
		return list;
	}
	
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		Rectangle re1 = new Rectangle(new Coordinate(new BigDecimal("1.0"), new BigDecimal("0.5")), new Coordinate(new BigDecimal("3.0"), new BigDecimal("2.0")));
		Rectangle re2 = new Rectangle(new Coordinate(new BigDecimal("2.0"), new BigDecimal("3.0")), new Coordinate(new BigDecimal("4.5"), new BigDecimal("5.0")));
		System.out.println(re1.isOverlapping(re2));
		System.out.println(re1.withinRectangle(new Coordinate(new BigDecimal("1.0"), new BigDecimal("0.5"))));
	}
	
	
	
}
