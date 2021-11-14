package gis;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;


class TestRectangle{

	Rectangle rectangle = new Rectangle(null, new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
	
	@Test
	public void testValidate() {
		rectangle = new Rectangle(null, new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		Exception exp = assertThrows(NullPointerException.class, () -> {rectangle.validate();});
		assertTrue(exp.getMessage().contains("Invalid coordinate."));
		rectangle = new Rectangle(new Coordinate(new BigDecimal("10.0"), new BigDecimal("10.0")), new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		Exception exp2 = assertThrows(NullPointerException.class, () -> {rectangle.validate();});
		assertTrue(exp2.getMessage().contains("Invalid rectangle: invalid bottomLeft and topRight coordinate combination. "));
	}
	
	@Test
	public void testValidate2() {
		rectangle = new Rectangle(null, new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		Exception exp = assertThrows(NullPointerException.class, () -> {Rectangle.validate(rectangle);});
		assertTrue(exp.getMessage().contains("Invalid coordinate."));
		rectangle = new Rectangle(new Coordinate(new BigDecimal("10.0"), new BigDecimal("10.0")), new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		Exception exp2 = assertThrows(NullPointerException.class, () -> {Rectangle.validate(rectangle);});
		assertTrue(exp2.getMessage().contains("Invalid rectangle: invalid bottomLeft and topRight coordinate combination. "));
	}
	
	@Test
	public void testLeft() {
		rectangle = new Rectangle(new Coordinate(new BigDecimal("1.0"), new BigDecimal("2.0")), new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		assertEquals("The left coordinate should be 1: ", new BigDecimal("1.0"), rectangle.left());
	}
	
	@Test
	public void testRight() {
		rectangle = new Rectangle(new Coordinate(new BigDecimal("1.0"), new BigDecimal("2.0")), new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		assertEquals("The right coordinate should be 4: ", new BigDecimal("4.0"), rectangle.right());
	}
	
	@Test
	public void testTop() {
		rectangle = new Rectangle(new Coordinate(new BigDecimal("1.0"), new BigDecimal("2.0")), new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		assertEquals("The top coordinate should be 8: ", new BigDecimal("8.0"), rectangle.top());
	}
	
	@Test
	public void testBottom() {
		rectangle = new Rectangle(new Coordinate(new BigDecimal("1.0"), new BigDecimal("2.0")), new Coordinate(new BigDecimal("4.0"), new BigDecimal("8.0")));
		assertEquals("The bottom coordinate should be 2: ", new BigDecimal("2.0"), rectangle.bottom());
	}
	
	
	
	
}
