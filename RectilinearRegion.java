package gis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RectilinearRegion {

	private final Set<Rectangle> rectangles;
	
	/**
	 * The constructor
	 * @param rectangles
	 */
	private RectilinearRegion(Set<Rectangle> rectangles) {
		this.rectangles = new HashSet<Rectangle>();
		for(Rectangle rectangle : rectangles)
			this.rectangles.add(rectangle);
		if(this.isOverlapping())
			throw new IllegalArgumentException("Invalid rectangles: overlapped. ");
	}
	
	/**
	 * Create a region of some rectangles
	 * @param rectangles
	 * @return
	 */
	public static final RectilinearRegion of(Set<Rectangle> rectangles) {
		RectilinearRegion region = new RectilinearRegion(rectangles);
		if(region.isOverlapping())
			throw new IllegalArgumentException("Invalid rectangles: overlapped. ");
		//TO DO: illegal exception
		else
			return region;
	}
	
	/**
	 * Clone a the rectangles of this region
	 * @return the clone of the rectangles of this region
	 */
	private List<Rectangle> cloneRectangles(){
		List<Rectangle> copy = new ArrayList<Rectangle>();
		for(Rectangle rectangle : this.rectangles)
			copy.add(rectangle);
		return copy;
	}
	
	/**
	 * Check if the rectangles of this region is overlapping
	 * @return whether the rectangles of this region is overlapping
	 */
	public boolean isOverlapping() {
		boolean isOverlapping = false;
		List<Rectangle> copy = this.cloneRectangles();
		OUTER: 
		for(int i = 0; i < rectangles.size(); i++) {
			for(int j = i + 1; j < rectangles.size(); j++) {
				isOverlapping = copy.get(i).isOverlapping(copy.get(j));
				if(isOverlapping == true)
					break OUTER;
			}
		}
		return isOverlapping;
	}
	
	
	/**
	 * Check if the input coordinate is in the region
	 * @param coordinate
	 * @return
	 */
	boolean withinRegion(Coordinate coordinate) {
		boolean withinRegion = false;
		CHECK: 
		for(Rectangle rectangle : rectangles) {
			if(rectangle.withinRectangle(coordinate)) {
				withinRegion = true;
				break CHECK;
			}
			else
				;
		}
		return withinRegion;
	}
	
	/**
	 * Get all x coordinates if this region
	 * @return the x coordinates
	 */
	Collection<BigDecimal> getXCoord(){
		List<BigDecimal> xCoord = new ArrayList<BigDecimal>();
		for(Rectangle rectangle : this.rectangles) {
			xCoord.add(rectangle.left());
			xCoord.add(rectangle.right());
		}
		return xCoord;
	}
	
	/**
	 * Get all y coordinates if this region
	 * @return the y coordinates
	 */
	Collection<BigDecimal> getYCoord(){
		List<BigDecimal> yCoord = new ArrayList<BigDecimal>();
		for(Rectangle rectangle : this.rectangles) {
			yCoord.add(rectangle.top());
			yCoord.add(rectangle.bottom());
		}
		return yCoord;
	}
	
	/**
	 * Add each rectangles to every coordinate within each of them
	 * @return the two dimensional map storing the rectangles
	 */
	public BiDimensionalMap<Rectangle> rectangleMap(){
		BiDimensionalMap<Rectangle> map = new BiDimensionalMap<Rectangle>(this.getXCoord(), this.getYCoord());
		List<Coordinate> coordinates = map.coordinateSet();
		List<Coordinate> within;
		for(Rectangle rectangle : rectangles) {
			within = rectangle.allPointsInRectangle(coordinates);
			map.addEveryWhere(within, rectangle);
		}
		return map;
	}
	
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		Rectangle Rectangle1 = new Rectangle(new Coordinate(new BigDecimal("1"), new BigDecimal("0.5")), new Coordinate(new BigDecimal("3"), new BigDecimal("2")));
		Rectangle Rectangle2 = new Rectangle(new Coordinate(new BigDecimal("2"), new BigDecimal("3")), new Coordinate(new BigDecimal("4.5"), new BigDecimal("5")));
		Set<Rectangle> rectangles = new HashSet<Rectangle>();
		rectangles.add(Rectangle1);
		rectangles.add(Rectangle2);
		RectilinearRegion region = new RectilinearRegion(rectangles);
		System.out.println(region.rectangleMap());
	}
	
	
}

















