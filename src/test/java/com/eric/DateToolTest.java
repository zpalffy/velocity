package com.eric;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateToolTest {

	@Test
	public void testYear() {
		DateTool tool = new DateTool().noon(10, 11, 1979);
		assertEquals(1979, tool.getYear());

		tool = new DateTool().noon(11, 28, 2018);
		assertEquals(2018, tool.getYear());
		
		tool = new DateTool().noon(11, 28, 1898);
		assertEquals(1898, tool.getYear());
	}
}
