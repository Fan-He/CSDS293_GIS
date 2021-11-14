package gis;

import static org.junit.Assert.assertEquals;


import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

class TestInterestPoint {

	
	Coordinate c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
	Coordinate c2 = new Coordinate(new BigDecimal(4.0), new BigDecimal(10.5));
	InterestPoint<String> po = new InterestPoint<String>(c1, "LAB");
	
	@Test
	public void testValidate() {
		//If both coordinate and marker are valid, the method should return current coordinate. 
		c1 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		assertEquals("c2 should be valid. ", po, po.validate());
		//if the coordinate is invalid, the method should throw an exception. 
		c1 = new Coordinate(null, new BigDecimal(10.5));
		po = new InterestPoint<String>(c1, "LAB");
		Exception exp = assertThrows(NullPointerException.class, () -> {po.validate();});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		c1 = new Coordinate(new BigDecimal(10.5), null);
		po = new InterestPoint<String>(c1, "LAB");
		Exception exp2 = assertThrows(NullPointerException.class, () -> {po.validate();});
		assertTrue(exp2.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		//if the marker is invalid, the method should throw an exception
		c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
		po = new InterestPoint<String>(c1, null);
		Exception exp3 = assertThrows(NullPointerException.class, () -> {po.validate();});
		assertTrue(exp3.getMessage().contains("Invalid marker."));
	}
	
	@Test
	public void testValidate2() {
		//If both coordinate and marker are valid, the method should return current coordinate. 
		c1 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		assertEquals("c2 should be valid. ", po, InterestPoint.validate(po));
		//if the coordinate is invalid, the method should throw an exception. 
		c1 = new Coordinate(null, new BigDecimal(10.5));
		po = new InterestPoint<String>(c1, "LAB");
		Exception exp = assertThrows(NullPointerException.class, () -> {InterestPoint.validate(po);});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		c1 = new Coordinate(new BigDecimal(10.5), null);
		po = new InterestPoint<String>(c1, "LAB");
		Exception exp2 = assertThrows(NullPointerException.class, () -> {InterestPoint.validate(po);});
		assertTrue(exp2.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		//if the marker is invalid, the method should throw an exception
		c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
		po = new InterestPoint<String>(c1, null);
		Exception exp3 = assertThrows(NullPointerException.class, () -> {InterestPoint.validate(po);});
		assertTrue(exp3.getMessage().contains("Invalid marker."));
	}

	@Test
	public void testX() {
		c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
		po = new InterestPoint<String>(c1, "LAB");
		assertEquals("longitude should be 2", new BigDecimal(2.0), po.x());
	}
	
	@Test
	public void testY() {
		c1 = new Coordinate(new BigDecimal(2.0), new BigDecimal(4.0));
		po = new InterestPoint<String>(c1, "LAB");
		assertEquals("latitude should be 4", new BigDecimal(4.0), po.y());
	}
	
	@Test
	public void testHasMarker() {
		assertTrue(po.hasMarker("LAB"));
		assertTrue(!po.hasMarker("GYM"));
		assertTrue(!po.hasMarker(null));
	}
	
	@Test
	public void testToString() {
		//Properly print the coordinate
		c1 = new Coordinate(new BigDecimal(4.0), new BigDecimal(2.5));
		po = new InterestPoint<String>(c1, "LAB");
		String message = po.toString();
		assertTrue(message.contains("Coordinate: (4, 2.5) Marker: LAB"));
		//when one of x and y of the coordinate is null, the method should throw an exception. 
		c1 = new Coordinate(null, new BigDecimal(2.5));
		po = new InterestPoint<String>(c1, "LAB");
		Exception exp = assertThrows(NullPointerException.class, () -> {po.toString();});
		assertTrue(exp.getMessage().contains("Invalid coordinate: invalid longitude or latitude."));
		//when the coordinate is invalid, the method should throw an exception. 
		c1 = null;
		po = new InterestPoint<String>(c1, "LAB");
		Exception exp2 = assertThrows(NullPointerException.class, () -> {po.toString();});
		assertTrue(exp2.getMessage().contains("Invalid coordinate."));
		//when the marker is null, the method should throw an exception. 
		c1 = new Coordinate(new BigDecimal(4.5), new BigDecimal(2.5));
		po = new InterestPoint<String>(c1, null);
		Exception exp3 = assertThrows(NullPointerException.class, () -> {po.toString();});
		assertTrue(exp3.getMessage().contains("Invalid marker. "));
	}
	
	
	
}
