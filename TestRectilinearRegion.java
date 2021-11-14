package gis;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

class TestRectilinearRegion {

	Rectangle Rectangle1 = new Rectangle(new Coordinate(new BigDecimal("1"), new BigDecimal("0.5")), new Coordinate(new BigDecimal("3"), new BigDecimal("2")));
	Rectangle Rectangle2 = new Rectangle(new Coordinate(new BigDecimal("2"), new BigDecimal("3")), new Coordinate(new BigDecimal("4.5"), new BigDecimal("5")));
	RectilinearRegion region;
	
	@Test
	void testRectilinearRegion() {
		Rectangle1 = new Rectangle(new Coordinate(new BigDecimal("1"), new BigDecimal("0.5")), new Coordinate(new BigDecimal("3"), new BigDecimal("4")));
		Set<Rectangle> set = new HashSet<Rectangle>();
		set.add(Rectangle1);
		set.add(Rectangle2);
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {region = RectilinearRegion.of(set);});
		assertTrue(exp.getMessage().contains("Invalid rectangles: overlapped. "));
	}
	
	@Test
	void testRectangleMap() {
		Set<Rectangle> rectangles = new HashSet<Rectangle>();
		rectangles.add(Rectangle1);
		rectangles.add(Rectangle2);
		RectilinearRegion region = RectilinearRegion.of(rectangles);
	}

}
