package com.ivanceras.fluent;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ivanceras.fluent.sql.Breakdown;
import com.ivanceras.fluent.sql.SQL;

import static com.ivanceras.fluent.sql.SQL.Statics.*;

public class TestSQLBuilderUpdate {

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
		String expected = "UPDATE films SET kind = ? WHERE kind = ?";
		Breakdown actual = UPDATE("films").SET("kind").EQUAL("Dramatic").WHERE("kind").EQUAL_TO("Drama").build();
		Object[] expectedParam = new Object[]{"Dramatic", "Drama"};
		CTest.cassertEquals(expected, actual.getSql());
		assertArrayEquals(expectedParam, actual.getParameters());
	}

}
