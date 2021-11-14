package gis;

import static org.junit.Assert.assertEquals;


import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

class TestCoordinate {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	Coordinate c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
	Coordinate c2 = new Coordinate(new BigDecimal(4.0), new BigDecimal(10.5));
	
	@Test
	public void testCompareTo() {
		assertEquals("c1 should be smaller than c2", -1, c1.compareTo(c2));
		c2 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		assertEquals("c1 should be smaller than c2", -1, c1.compareTo(c2));
		c1 = new Coordinate(new BigDecimal(8.0), new BigDecimal(4.0));
		assertEquals("c1 should be larger than c2", 1, c1.compareTo(c2));
		c2 = null;
		assertThrows(NullPointerException.class, () -> c1.compareTo(c2));
		c2 = new Coordinate(new BigDecimal("8.0"), new BigDecimal("4"));
		assertEquals("c1 should be equal to c2", 0, c1.compareTo(c2));
	}
	
	@Test
	public void testValidate() {
		//If one of x and y is null, a NullPointerException should be thrown and return message: Invalid coordinate: invalid longitude or latitude.
		c2 = new Coordinate(null, new BigDecimal(10.5));
		Exception exp = assertThrows(NullPointerException.class, () -> {c2.validate();});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		c2 = new Coordinate(new BigDecimal(10.5), null);
		Exception exp2 = assertThrows(NullPointerException.class, () -> {c2.validate();});
		assertTrue(exp2.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		//If both x and y are valid, return current coordinate. 
		c2 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		assertEquals("c2 should be valid. ", c2, c2.validate());
	}
	
	@Test
	public void testValidate2() {
		//If one of x and y is null, a NullPointerException should be thrown and return message: Invalid coordinate: invalid longitude or latitude.
		c2 = new Coordinate(null, new BigDecimal(10.5));
		Exception exp = assertThrows(NullPointerException.class, () -> {Coordinate.validate(c2);});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
	    c2 = new Coordinate(new BigDecimal(10.5), null);
		Exception exp2 = assertThrows(NullPointerException.class, () -> {Coordinate.validate(c2);});
		assertTrue(exp2.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		//If both x and y are valid, return current coordinate. 
		c2 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		assertEquals("c2 should be valid. ", c2, Coordinate.validate(c2));
	}
	
	@Test
	public void testToSimpleString() {
		//Properly print the coordinate
		c2 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		String message = c2.toSimpleString();
		assertTrue(message.contains("(4, 2.5)"));
		//when one of x and y is null
		c2 = new Coordinate(null, new BigDecimal(2.5));
		Exception exp2 = assertThrows(NullPointerException.class, () -> {c2.toSimpleString();});
		assertTrue(exp2.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
	}
	
	
	
	
	

}
