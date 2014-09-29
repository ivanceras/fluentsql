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

public class TestSQLOrderBy {

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
		String expected = "" +
				" SELECT Customers.CustomerName , Orders.OrderID "+
				" FROM Customers "+
				" ORDER BY name desc , description";
		
		
		SQL sql = SELECT("Customers.CustomerName", "Orders.OrderID")
					.FROM("Customers")
					.ORDER_BY("name").DESC()
					.FIELD("description");
		Breakdown bk = sql.build();
		CTest.cassertEquals(expected, bk.getSql());
	}

}
