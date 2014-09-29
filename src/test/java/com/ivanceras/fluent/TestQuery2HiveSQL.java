package com.ivanceras.fluent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.ivanceras.fluent.sql.SQL.Statics.*;

public class TestQuery2HiveSQL {

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
	
		String expected = "SELECT * FROM products WHERE price IS NOT NULL";
		String actual = SELECT("*").FROM("products").WHERE("price").IS_NOT_NULL().build().getSql();
		CTest.cassertEquals(expected, actual);
	}
	
	
	@Test
	public void test2() {
	
		String expected = "SELECT  SUM ( price )  FROM products WHERE price IS NOT NULL";
		String actual = SELECT(SUM("price")).FROM("products").WHERE("price").IS_NOT_NULL().build().getSql();
		CTest.cassertEquals(expected, actual);
	}


}
