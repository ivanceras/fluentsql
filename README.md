fluentsql
==========

[![Build Status](https://api.travis-ci.org/ivanceras/fluentsql.svg)](https://travis-ci.org/ivanceras/fluentsql)

A minimalistic Fluent SQL API for Java aimed to resemble the code to your original SQL code

 
     
Using String concatenation:

````java
  	String sql =
				" WITH LatestOrders AS (" +
				"		SELECT CustomerName , "+
				"				SUM ( COUNT ( ID ) ) ," +
				"				COUNT ( MAX ( n_items ) ) , " +
				"				Red as color" +
				"			FROM dbo.Orders" +
				"			RIGHT JOIN Customers" +
				"				on Orders.Customer_ID = Customers.ID " +
				"			LEFT JOIN Persons" +
				"				ON Persons.name = Customer.name" +
				"				AND Persons.lastName = Customer.lastName" +
				"			GROUP BY CustomerID" +
				"		) "+
				"  SELECT "+
				"    Customers.* , "+
				"    Orders.OrderTime AS LatestOrderTime , "+
				"    ( SELECT COUNT ( * ) " +
				"		FROM dbo.OrderItems " +
				"		WHERE OrderID IN "+
				"        ( SELECT ID FROM dbo.Orders WHERE CustomerID = Customers.ID )  "+
				"      )      AS TotalItemsPurchased "+
				" FROM dbo.Customers " +
				" INNER JOIN dbo.Orders "+
				"        USING ID" +
				" WHERE "+
				"   Orders.ID IN ( SELECT ID FROM LatestOrders )"+
				"	AND Orders.n_items > ? ";
  
````

In fluent SQL:

````java

    SQL sql = WITH("LatestOrders" , 
							SELECT("CustomerName")
							.FN(SUM(COUNT("ID")))
							.FN(COUNT(MAX("n_items")))
							.FIELD("Red").AS("color")
							.FROM("dbo.Orders")
							.RIGHT_JOIN("Customers")
								.ON("Orders.customer_ID" , "Customers.ID")
							.LEFT_JOIN("Persons")
								.ON("Persons.name" , "Customer.name")
								.AND_ON("Persons.lastName" , "Customer.lastName")
							.GROUP_BY("CustomerID")
			)
			.append(SELECT()
				.FIELD("Customers.*")
				.FIELD("Orders.OrderTime").AS("LatestOrderTime")
				.FIELD(SELECT(COUNT("*"))
							.FROM("dbo.OrderItems")
							.WHERE("OrderID").IN(
									SELECT("ID")
										.FROM("dbo.Orders")
										.WHERE("CustomerID").EQUAL_TO_FIELD("Customers.ID"))
							
						).AS("TotalItemsPurchased")
				.FROM("dbo.Customers")
				.INNER_JOIN("dbo.Orders")
					.USING("ID")
				.WHERE("Orders.ID").IN(SELECT("ID").FROM("LatestOrders"))
				.AND("Orders.n_items").GREATER_THAN(0)
				);
		
		Breakdown actual = sql.build();
      
````      

TODO: need ORM version here

### Features


A SQL breakdown result:
 * breakdown.getSql() (String) - the SQL string built
 * breakdown.getParameters() (Object[]) - the resulted array of the parameters that is gathered by the SQL builder, with the correct order as they are mentioned in the query
	This will be used in as parameters in your preparedStatment 
 * stmt.setObject(i,parameter[i]), in turn SQL injection is already mitigated





link to comments

https://news.ycombinator.com/item?id=5956867



#### More examples


https://github.com/ivanceras/fluentsql/tree/master/src/test/java/com/ivanceras/fluent


#### Similar Projects


http://www.jooq.org/  ( This is polished but I wanted CAPSLOCK on the SQL keywords )

http://code.google.com/p/squiggle-sql/  ( ..uhmm.. poor choice of words.. )

LICENSE: Apache2

Tips? : 1CYj1jEjV4eWm5TLPRDD34hQbVuUHcGg9X
