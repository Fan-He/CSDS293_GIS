package gis;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

//import gis.BiDimensionalMap.Updater;

import java.util.stream.Collectors;

/**
 * BiDimensional class
 * @author Xiaofan He
 *
 * @param <T>
 */
public final class BiDimensionalMap<T> {
	
	private final SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>> points;
	
	/**
	 * Get the collection on the cooresponding point
	 * @param x, y  the x and y coordinate of the point
	 * @return the collection cooresponding to the input coordinate
	 */
	public final Collection<T> get(BigDecimal x, BigDecimal y){
		//validate the coordinate
		Coordinate.validate(new Coordinate(x, y));
		//If there is no such point in the map, return null
		if(points.get(x) == null)
			return null;
		return points.get(x).get(y);
	}
	
	/**
	 * Get the collection on the cooresponding point
	 * @param coordinate  the coordinate of the point
	 * @return the collection cooresponding to the input coordinate
	 */
	public final Collection<T> get(Coordinate coordinate){
		coordinate.validate();
		return this.get(coordinate.x(), coordinate.y());
	}
	
	/**
	 * Get the points
	 * @return points
	 */
	public final SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>> getPoints(){
		return points;
	}
	
	/**
	 * The constructor
	 */
	BiDimensionalMap(Collection<BigDecimal> xCoord, Collection<BigDecimal> yCoord) {
		points = new TreeMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>>();
		for(BigDecimal x : xCoord) {
			for(BigDecimal y : yCoord) {
				Coordinate coordinate = new Coordinate(x, y).validate();
				BiDimensionalMap<T>.Updater updater = this.new Updater();
				updater.setCoordinate(coordinate);
				updater.add();
			}
		}
	}
	
	/**
	 * Adds the given value everywhere in the map
	 * @param value
	 */
	public final void addEveryWhere(T value) {
		Collection<Coordinate> coordinates = this.coordinateSet();
		BiDimensionalMap<T>.Updater updater = this.new Updater();
		updater.addValue(value);
		for(Coordinate coordinate : coordinates) {
			updater.setCoordinate(coordinate);
			updater.add();
		}
	}
	
	/**
	 * Add the given value on every given coordinate in the map
	 */
	void addEveryWhere(List<Coordinate> coordinates, T value) {
		BiDimensionalMap<T>.Updater updater = this.new Updater();
		updater.addValue(value);
		for(Coordinate coordinate : coordinates) {
			updater.setCoordinate(coordinate);
			updater.add();
		}
	}
	
	
	
	/**
	 * Updater class, help set and update the map
	 * @author Xiaofan He
	 *
	 */
	public final class Updater{
		private BigDecimal x = new BigDecimal(0.0);
		private BigDecimal y = new BigDecimal(0.0);
		private Supplier<Collection<T>> collectionFactory = HashSet::new;
		private Collection<T> values = collectionFactory.get();
		
		/**
		 * Set the x coordinate
		 * @param x
		 */
		public final Updater setX(BigDecimal x) {
			this.x = x;
			return this;
		}
		
		/**
		 * Set the y coordinate
		 * @param y
		 */
		public final Updater setY(BigDecimal y) {
			this.y = y;
			return this;
		}
		
		/**
		 * Set the collection
		 * @param sup the collection
		 * @return this updater
		 */
		public final Updater setCollectionFactory(Supplier<Collection<T>> sup) {
			this.collectionFactory = sup;
			return this;
		}
		
		/**
		 * Set the values of the updater
		 * @param col the collection
		 * @return this updater
		 */
		public final Updater setValues(Collection<T> col) {
			this.values = col;
			return this;
		}
		
		/**
		 * Set the coordinate of this updater
		 * @param coordinate
		 * @return this updater
		 */
		public final Updater setCoordinate(Coordinate coordinate) {
			coordinate.validate();
			x = coordinate.x();
			y = coordinate.y();
			return this;
		}
		
		/**
		 * Add a value to the values of this updater
		 * @param value the value to be added
		 */
		public final Updater addValue(T value) {
			if(value == null)
				throw new NullPointerException();
			values.add(value);
			return this;
		}
		
		/**
		 * Set the values of the map at the coordinate of the updater
		 * @return pre the collection after the values were set to the point
		 */
		public final Collection<T> set(){
			Collection<T> pre = get(x, y);
			if(points.get(x) == null) {
				points.put(x, new TreeMap<BigDecimal, Collection<T>>());
			}
			points.get(x).put(y, values);
			return pre;
		}
		
		/**
		 * Add values to the map at the coordinate of the updater
		 * @return res whether the collection at that point has changed after updating
		 */
		public final boolean add() {
			if(points.get(x) == null) {
				points.put(x, new TreeMap<BigDecimal, Collection<T>>());
			}
			Collection<T> cur = points.get(x).get(y);
			if(cur == null) {
				cur = getUpdater().values;
			}
			Collection<T> combination = Stream.of(cur, values).flatMap(Collection::stream).collect(Collectors.toList());
			boolean res = combination.equals(cur);
			points.get(x).put(y, combination);
			return res;
		}
		
//		public void existXPoints() {
//			if(points.get(x) == null) {
//				SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>> xMap = new TreeMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>>();
//				
//			}
//		}
	}
	
	/**
	 * Get the updater
	 * @return updater
	 */
	public final Updater getUpdater() {
		return new Updater();
	}
	
	public static void main(String[] args) {
		Collection<String> pointC = new ArrayList<String>();
		Coordinate c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(2.0));
		Coordinate c2 = new Coordinate(new BigDecimal(1.0), new BigDecimal(2.5));
		Coordinate c3 = new Coordinate(new BigDecimal(10.0), new BigDecimal(5.5));
		Coordinate c4 = new Coordinate(new BigDecimal(8.0), new BigDecimal(3.5));
		Coordinate c5 = new Coordinate(new BigDecimal(5.0), new BigDecimal(3.0));
		BiDimensionalMap<String> map = new BiDimensionalMap<String>(null, null);
		BiDimensionalMap<String>.Updater updater = map.new Updater();
		updater.setCoordinate(c1);
		Collection<String> pointC2 = new ArrayList<String>();
		pointC.add("RESIDENCE");
		pointC.add("DINING");
		updater.setValues(pointC);
		updater.add();
		pointC2.add("LAB");
		pointC2.add("GYM");
		updater.setValues(pointC2);
		updater.setCoordinate(c2);
		updater.add();
		updater.setCoordinate(c3);
		pointC.add("LECTURE");
		updater.setValues(pointC);
		updater.add();
		updater.setCoordinate(c4);
		pointC.add("GYM");
		pointC.remove("DINING");
		updater.add();
		pointC.remove("DINING");
		pointC.remove("LECTURE");
		updater.setCoordinate(c5);
		updater.add();
		System.out.println(map.collectionList());
		System.out.println(map.coordinateSet());
		System.out.println(map.slice(new Rectangle(new Coordinate(new BigDecimal(1.5), new BigDecimal(0.5)), new Coordinate(new BigDecimal(9.5), new BigDecimal(5.0)))).coordinateSet());
		Predicate<String> filter = i -> (i.equals("RESIDENCE"));
		System.out.println(map.collectionSize(filter));
		System.out.println(map.toString());
	}
	
	/**
	 * Get all x coordinates of the points in the map
	 * @return a set containing all x coordinates within the map
	 */
	public final Set<BigDecimal> xSet(){
		return points.keySet();
	}
	
	/**
	 * Get all y coordinates of the points in the map
	 * @return a set containing all y coordinates within the map
	 */
	public final Set<BigDecimal> ySet(BigDecimal x){
		return points.get(x).keySet();
	}
	
	/**
	 * Get all coordinates of the points in the map
	 * @return a list containing all coordinates within the map
	 */
	public final List<Coordinate> coordinateSet(){
		List<Coordinate> list = new ArrayList<Coordinate>();
		for(BigDecimal xCoordinate : this.xSet()) {
			for(BigDecimal yCoordinate : this.ySet(xCoordinate)) {
				list.add(new Coordinate(xCoordinate, yCoordinate));
			}
		}
		return list;
	}
	
	
	/**
	 * Get all collections at all points in the map
	 * @return a set containing all collections within the map
	 */
	public final List<Collection<T>> collectionList(){
		List<Coordinate> coordinateList = this.coordinateSet();
		List<Collection<T>> collectionList = new ArrayList<Collection<T>>();
		for(Coordinate co : coordinateList) {
			collectionList.add(this.get(co));
		}
		return collectionList;
	}
	
	
	/**
	 * Get the total number of entries
	 * @return the total number of entries
	 */
	public final long collectionSize() {
		Collection<T> entries = new ArrayList<T>();
		for(Collection<T> collection : this.collectionList()) {
			for(T entry : collection) {
				entries.add(entry);
			}
		}
		return entries.size();
	}
	
	
	/**
	 * Get number of the entries that meets the filter
	 * @return the size of the list containing all entries after filter
	 */
	public final long collectionSize(Predicate<? super T> filter) {
		Collection<T> entries = new ArrayList<T>();
		for(Collection<T> collection : this.collectionList()) {
			for(T entry : collection) {
				if(filter.test(entry))
					entries.add(entry);
			}
		}
		return entries.size();
	}
	
	
	/**
	 * Check whether the coordinate is in the rectangle
	 * @param coordinate
	 * @param rectangle
	 * @return whether the coordinate is in the rectangle
	 */
	private final static boolean isInRectangle(Coordinate coordinate, Rectangle rectangle) {
		BigDecimal left = rectangle.left();
		BigDecimal right = rectangle.right();
		BigDecimal top = rectangle.top();
		BigDecimal bottom = rectangle.bottom();
		BigDecimal x = coordinate.x();
		BigDecimal y = coordinate.y();
		if(left.compareTo(x) <= 0 && x.compareTo(right) <= 0 && y.compareTo(top) <= 0 && bottom.compareTo(y) <= 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Select all coordinates that is in the input rectangle and return the cooresponding map
	 * @param rectangle
	 * @return map containing all coordinates with the rectangle
	 */
	public final BiDimensionalMap<T> slice(Rectangle rectangle){
		Collection<BigDecimal> initial = new ArrayList<BigDecimal>();
		BiDimensionalMap<T> map = new BiDimensionalMap<T>(initial, initial);
		List<Coordinate> list = this.coordinateSet();
		BiDimensionalMap<T>.Updater updater = map.new Updater();
		for(Coordinate coordinate : list) {
			if(BiDimensionalMap.isInRectangle(coordinate, rectangle)) {
				Collection<T> values = this.get(coordinate);
				updater.setCoordinate(coordinate);
				updater.setValues(values);
				updater.add();
			}
			else
				;
		}
		return map;
	}
	
	public String toString() {
		String res = "";
		List<Coordinate> list = this.coordinateSet();
		for(Coordinate coordinate : list) {
			res = res + "{" + coordinate.toString() + " Entries: " + this.get(coordinate) + "} \n";
		}
		return res;
	}
	
	Collection<T> getAllElementsWithCoordinates(Collection<Coordinate> coordinates) {
		Collection<T> collection = new ArrayList<T>();
		for(Coordinate coordinate : coordinates) {
			for(T element : this.get(coordinate))
				collection.add(element);
		}
		return collection;
	}
	
	
}
