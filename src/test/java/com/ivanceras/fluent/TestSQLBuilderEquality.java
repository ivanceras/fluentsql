package com.ivanceras.fluent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.ivanceras.fluent.sql.SQL.*;


public class TestSQLBuilderEquality {


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String expected = "DELETE FROM products WHERE price = ?";
		String actual = DELETE().FROM("products").WHERE("price").EQUAL_TO(10).build().getSql();
		String actual2 = DELETE().FROM("products").WHERE("price").EQUAL_TO("10").build().getSql();
		CTest.cassertEquals(expected, actual);
		CTest.cassertEquals(expected, actual2);
	}

}
