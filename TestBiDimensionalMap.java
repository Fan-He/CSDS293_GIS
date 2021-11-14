package gis;

import static org.junit.Assert.assertEquals;



import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.junit.Test;


class TestBiDimensionalMap {

	Coordinate c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
	Coordinate c2 = new Coordinate(null, new BigDecimal(10.5));
	InterestPoint<String> po = new InterestPoint<String>(c1, "LAB");
	Collection<String> pointC = new ArrayList<String>();
	Collection<String> pointC2 = new ArrayList<String>();
	Collection<BigDecimal> initial = new ArrayList<BigDecimal>();
	SortedMap<BigDecimal, Collection<String>> yMap = new TreeMap<BigDecimal, Collection<String>>();
	SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<String>>> xMap = new TreeMap<BigDecimal, SortedMap<BigDecimal, Collection<String>>>();
	BiDimensionalMap<String> map = new BiDimensionalMap<String>(initial, initial);
	BiDimensionalMap<String>.Updater updater = map.new Updater();
	boolean a = pointC.add("LAB");
	
	void setTest() {
		pointC.add("LAB");
		pointC.add("GYM");
		updater.setValues(pointC);
		updater.setCoordinate(c1);
		updater.set();
	}
	
	@Test
	public void testGet() {
		//Properly get the collection
		setTest();
		assertEquals("The collection should be pointC: ", pointC, map.get(c1));
		//When the input coordinate is not valid, the method should throw an exception
		Exception exp = assertThrows(NullPointerException.class, () -> {map.get(c2);});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
	}
	
	@Test
	public void testGet2() {
		//Properly get the collection
		setTest();
		assertEquals("The collection should be pointC: ", pointC, map.get(c1.x(), c1.y()));
		//When the input coordinate is not valid, the method should throw an exception
		Exception exp = assertThrows(NullPointerException.class, () -> {map.get(c2.x(), c2.y());});
		assertTrue(exp.getMessage().contains("Invalid coordinate"));
	}
	
	@Test
	public void testSet() {
		pointC2.add("LAB");
		pointC2.add("RESIDENCE");
		updater.setValues(pointC2);
		//when the coordinate is invalid, the method should throw an exception
		Exception exp = assertThrows(NullPointerException.class, () -> {updater.setCoordinate(c2);});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		//when the coordinate is valid, properly set values
		Coordinate c3 = new Coordinate(new BigDecimal(6.3), new BigDecimal(4.2));
		updater.setCoordinate(c3);
		updater.set();
		assertEquals("The collection should be pointC2: ", pointC2, map.get(c3));
	}
	
	
	
	@Test
	public void testXSet() {
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("7.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Collection<BigDecimal> col = new ArrayList<BigDecimal>();
		col.add(c1.x());
		col.add(c2.x());
		col.add(c4.x());
		updater.setCoordinate(c1);
		updater.add();
		updater.setCoordinate(c2);
		updater.add();
		updater.setCoordinate(c3);
		updater.add();
		updater.setCoordinate(c4);
		updater.add();
		assertEquals("The collection should be same as col: ", true, map.xSet().containsAll(col));
	}
	
	@Test 
	public void testYSet() {
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("7.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Collection<BigDecimal> col = new ArrayList<BigDecimal>();
		col.add(c1.y());
		col.add(c3.y());
		updater.setCoordinate(c1);
		updater.add();
		updater.setCoordinate(c2);
		updater.add();
		updater.setCoordinate(c3);
		updater.add();
		updater.setCoordinate(c4);
		updater.add();
		assertEquals("The collection should be same as col: ", true, map.ySet(new BigDecimal("2.5")).containsAll(col));
		col.clear();
		col.add(new BigDecimal("0.2"));
		assertEquals("The collection should be same as col: ", true, map.ySet(new BigDecimal("5.3")).containsAll(col));
	}
	
	@Test
	public void testCoordinateSet() {
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("7.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Collection<Coordinate> coor = new ArrayList<Coordinate>();
		updater.setCoordinate(c1);
		updater.add();
		updater.setCoordinate(c2);
		updater.add();
		updater.setCoordinate(c3);
		updater.add();
		updater.setCoordinate(c4);
		updater.add();
		assertEquals("The collection should contains all coordinates added: ", true, map.coordinateSet().containsAll(coor));
	}
	
	@Test
	public void testCollectionList() {
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("7.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Collection<String> col1 = new ArrayList<String>();
		Collection<String> col2 = new ArrayList<String>();
		Collection<String> col3 = new ArrayList<String>();
		Collection<String> col4 = new ArrayList<String>();
		List<Collection<String>> list = new ArrayList<Collection<String>>();
		col1.add("LAB");
		col1.add("RESIDENCE");
		col2.add("LAB");
		col3.add("DINING");
		col4.add("LECTURE");
		list.add(col1);
		list.add(col2);
		list.add(col3);
		list.add(col4);
		Collection<Coordinate> coor = new ArrayList<Coordinate>();
		updater.setCoordinate(c1);
		updater.setValues(col1);
		updater.add();
		updater.setCoordinate(c2);
		updater.setValues(col2);
		updater.add();
		updater.setCoordinate(c3);
		updater.setValues(col3);
		updater.add();
		updater.setCoordinate(c4);
		updater.setValues(col4);
		updater.add();
		assertEquals("The collection should contains all collections added: ", true, map.collectionList().containsAll(list));
		assertEquals("The collection should contains all collections added: ", true, list.containsAll(map.collectionList()));
	}
	
	@Test
	public void testCollectionSize() {
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("7.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Collection<String> col1 = new ArrayList<String>();
		Collection<String> col2 = new ArrayList<String>();
		Collection<String> col3 = new ArrayList<String>();
		Collection<String> col4 = new ArrayList<String>();
		col1.add("LAB");
		col1.add("RESIDENCE");
		col2.add("LAB");
		col3.add("DINING");
		col4.add("LECTURE");
		updater.setCoordinate(c1);
		updater.setValues(col1);
		updater.add();
		updater.setCoordinate(c2);
		updater.setValues(col2);
		updater.add();
		updater.setCoordinate(c3);
		updater.setValues(col3);
		updater.add();
		updater.setCoordinate(c4);
		updater.setValues(col4);
		updater.add();
		assertEquals("The collection size should equals to the number of all entries added: ", 5, map.collectionSize());
	}
	
	@Test
	public void testCollectionSizeWithFilter() {
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("7.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Collection<String> col1 = new ArrayList<String>();
		Collection<String> col2 = new ArrayList<String>();
		Collection<String> col3 = new ArrayList<String>();
		Collection<String> col4 = new ArrayList<String>();
		col1.add("LAB");
		col1.add("RESIDENCE");
		col2.add("LAB");
		col3.add("DINING");
		col4.add("LECTURE");
		updater.setCoordinate(c1);
		updater.setValues(col1);
		updater.add();
		updater.setCoordinate(c2);
		updater.setValues(col2);
		updater.add();
		updater.setCoordinate(c3);
		updater.setValues(col3);
		updater.add();
		updater.setCoordinate(c4);
		updater.setValues(col4);
		updater.add();
		Predicate<String> filter = i -> (i.equals("LAB"));
		assertEquals("The collection size should equals to the number of all entries added that is LAB: ", 2, map.collectionSize(filter));
	}
	
	@Test
	public void testSlice(){
		c1 = new Coordinate(new BigDecimal("2.5"), new BigDecimal("4.0"));
		c2 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("0.2"));
		Coordinate c3 = new Coordinate(new BigDecimal("3.5"), new BigDecimal("4.7"));
		Coordinate c4 = new Coordinate(new BigDecimal("5.3"), new BigDecimal("0.2"));
		Coordinate bottomLeft = new Coordinate(new BigDecimal("1.0"), new BigDecimal("2.0"));
		Coordinate topRight = new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0"));
		updater.setCoordinate(c1);
		updater.add();
		updater.setCoordinate(c2);
		updater.add();
		updater.setCoordinate(c3);
		updater.add();
		updater.setCoordinate(c4);
		updater.add();
		List<Coordinate> list = new ArrayList<Coordinate>();
		list.add(c1);
		list.add(c3);
		assertEquals("The map after slicing would only contain c1 and c3: ", true, map.slice(new Rectangle(bottomLeft, topRight)).coordinateSet().equals(list));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
