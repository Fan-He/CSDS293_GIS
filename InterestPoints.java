package gis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

/**
 * InterestPoint class
 * @author Xiaofan He
 * @param <M>
 *
 */
public class InterestPoints<M> {
	
	
	private final BiDimensionalMap<InterestPoint> points;
	
	/**
	 * Builder class
	 * @author Xiaofan He
	 *
	 */
	public static class Builder{
		
		private final BiDimensionalMap<InterestPoint> points = new BiDimensionalMap<InterestPoint>(null, null);
		/**
		 * Add an interest point to the points
		 * @param interestPoint
		 * @return whether the InterestPoint is added to the points
		 */
		public final boolean add(InterestPoint interestPoint) {
			InterestPoint.validate(interestPoint);
			BiDimensionalMap<InterestPoint>.Updater updater = points.new Updater(); 
			ArrayList<InterestPoint> list = new ArrayList<InterestPoint>();
			list.add(interestPoint);
			updater.setCoordinate(interestPoint.coordinate());
			updater.setValues(list);
			updater.add();
			return true;
		}
		
		public final InterestPoints build() {
			return new InterestPoints(this);
		}
	}
	
	/**
	 * Constructor
	 * @param builder
	 */
	private InterestPoints(Builder builder) {
		points = builder.points;
	}
	
	public final Collection<InterestPoint> get(Coordinate coordinate){
		Coordinate.validate(coordinate);
		return points.get(coordinate);
	}
	
	/**
	 * Get the collections of the points
	 * @return
	 */
	public final List<Collection<InterestPoint>> interestPoints(){
		return points.collectionList();
	}
	
	/**
	 * Describe the interest points
	 * @return the String that describes the points
	 */
	public String toString() {
		return points.toString();
	}
	
	/**
	 * Count the number of interest points within the given non-overlapping region with the given marker
	 * @param region
	 * @param marker
	 * @return the number of interest points within the given non-overlapping region with the given marker
	 */
	public final long count(RectilinearRegion region, M marker) {
		Collection<Coordinate> coordinates = new ArrayList<Coordinate>();
		long count = 0;
		//First check if the coordinates are in the region
		for(Coordinate coordinate : points.coordinateSet()) {
			if(region.withinRegion(coordinate))
				coordinates.add(coordinate);
		}
		//Then check if the points within the region has the given marker
		for(InterestPoint point : points.getAllElementsWithCoordinates(coordinates)) {
			if(point.hasMarker(marker))
				count++;
		}
		return count;
	}

}
