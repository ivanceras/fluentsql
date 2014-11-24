fluentsql
==========

[![Build Status](https://api.travis-ci.org/ivanceras/fluentsql.svg)](https://travis-ci.org/ivanceras/fluentsql)

A minimalistic Fluent SQL API for Java aimed to resemble the code to your original SQL code.

     
##Example
 * Writing SQL code using string concatentation in Java

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

* In fluent SQL:

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
## What it is:
 * A clever usage of Java Static methods to write your SQL statement just as what you would expect.
 * Easily write your SQL in java.
 * It has almost no logic other than appending the SQL statements just like StringBuilder, and appending commas when it is obvious.
 

## What it is NOT
 * It is not an automatic SQL builder that will automagically transform SQL strings to work accross multi-database platform.
 
## What it is used for
 * It is used by programmers who would rather write SQL statements than use ORM that gets in their way.
  
 


### Features


A SQL breakdown result:
 * breakdown.getSql() (String) - the SQL string built
 * breakdown.getParameters() (Object[]) - the resulted array of the parameters that is gathered by the SQL builder, with the correct order as they are mentioned in the query
	This will be used in as parameters in your preparedStatment 
 * stmt.setObject(i,parameter[i]), in turn SQL injection is already mitigated



## The keyword `SUPER AWESOME JOIN` that I want to use is not there.

 * You can use the keyword API
 	
 ```java
 SQL = SELECT("name", "description").FROM("mytable").
 			keyword(" SUPER AWESOME JOIN ").append("table3")
 			.ON("column1", "column2");
 ```
 
 * You can incorporate it into the [SQL.java](https://github.com/ivanceras/fluentsql/blob/master/src/main/java/com/ivanceras/fluent/sql/SQL.java) class

### Usage

* Simplest Usage:
>Copy these 2 files [here](https://github.com/ivanceras/fluentsql/tree/master/src/main/java/com/ivanceras/fluent/sql).
  Make it as part of your source code, so you can easily modify and add keywords intended for your Database platform.
 

* Maven project, If you are just using keywords that are already in there!

```xml

<dependency>
	<groupId>com.ivanceras</groupId>
	<artifactId>fluentsql</artifactId>
	<version>0.0.6</version>
</dependency>

```

    
### Projects using it

* [Ivanceras ORM](https://github.com/ivanceras/orm)

> Take a look at the code in action [here](https://github.com/ivanceras/orm/blob/master/src/main/java/com/ivanceras/db/api/DB_Rdbms.java)




### link to previous HN comments submission

https://news.ycombinator.com/item?id=5956867



#### More examples

https://github.com/ivanceras/fluentsql/tree/master/src/test/java/com/ivanceras/fluent


#### Similar Projects

http://www.jooq.org/  ( This is polished but I wanted CAPSLOCK on the SQL keywords, also it automatically transform SQL statements for each platform )

http://code.google.com/p/squiggle-sql/  ( ..uhmm.. poor choice of words.. )

### License
Apache2

### How can I help?
* Bitcoin Tips : 1CYj1jEjV4eWm5TLPRDD34hQbVuUHcGg9X
* [Fork me](https://github.com/ivanceras/fluentsql)
* [Submit Issues, Suggestions](https://github.com/ivanceras/fluentsql/issues)

