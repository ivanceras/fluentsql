package com.ivanceras.fluent.sql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Dumbed down SQL class, acts as close to StringBuilder, to write the SQL statement
 * smart mode, takes care of commas, open and close parenthesis depending whether it follows such field,function, table etc.
 * @author lee
 *
 */
public class SQL {

	public static SQL ALTER(){
		return instance().keyword("ALTER");
	}
	public static SQL ALTER_TABLE(String table){
		return ALTER().TABLE(table);
	}
	public static SQL AVG(SQL sql){
		return instance().FUNCTION("AVG", sql);
	}
	public static SQL AVG(String column){
		return instance().FUNCTION("AVG", column);
	}
	public static SQL COUNT(SQL sql){
		return instance().FUNCTION("COUNT", sql);
	}

	public static SQL COUNT(String column){
		return instance().FUNCTION("COUNT", column);
	}
	public static SQL CREATE(){
		return instance().keyword("CREATE");
	}
	public static SQL CREATE_TABLE(String table){
		return CREATE().TABLE(table);
	}
	public static SQL DELETE(){
		return instance().keyword("DELETE");
	}
	public static SQL DELETE_FROM(String table){
		return DELETE().FROM(table);
	}

	public static SQL DROP(){
		return instance().keyword("DROP");
	}

	public static SQL DROP_TABLE() {
		return DROP().keyword("TABLE");
	}
	
	public static SQL DROP_TABLE(String table) {
		return DROP().TABLE(table);
	}

	public static SQL INSERT(){
		return instance().keyword("INSERT");
	}

	public static SQL instance(){
		return new SQL();
	}
	public static SQL LOWER(SQL sql){
		return instance().FUNCTION("LOWER", sql);
	}
	public static SQL LOWER(String column){
		return instance().FUNCTION("LOWER", column);
	}

	public static SQL MAX(SQL sql){
		return instance().FUNCTION("MAX", sql);
	}

	public static SQL MAX(String column){
		return instance().FUNCTION("MAX", column);
	}

	public static SQL MIN(SQL sql){
		return instance().FUNCTION("MIN", sql);
	}

	public static SQL MIN(String column){
		return instance().FUNCTION("MIN", column);
	}

	public static SQL SELECT(){
		return instance().keyword("SELECT");
	}

	public static SQL SELECT(SQL arg){
		return SELECT().field(arg);
	}

	public static SQL SELECT(String... columns){
		return SELECT().FIELD(columns);
	}

	public static SQL SUM(SQL sql){
		return instance().FUNCTION("SUM", sql);
	}


	public static SQL SUM(String column){
		return instance().FUNCTION("SUM", column);
	}

	public static SQL TRUNCATE_TABLE(String table){
		return instance().keyword("TRUNCATE").TABLE(table);
	}

	public static SQL UPDATE(String table){
		return instance().keyword("UPDATE").FIELD(table);
	}

	public static SQL UPPER(SQL sql){
		return instance().FUNCTION("UPPER", sql);
	}

	public static SQL UPPER(String column){
		return instance().FUNCTION("UPPER", column);
	}

	public static SQL WITH(String queryName){
		return instance().keyword("WITH").keyword(queryName);
	}

	public static SQL WITH(String queryName, SQL sql){
		return WITH(queryName).AS().FIELD(sql);
	}

	public static SQL WITH_RECURSIVE(String queryName){
		return instance().keyword("WITH RECURSIVE").FIELD(queryName);
	}
	public static SQL WITH_RECURSIVE(String queryName, SQL sql){
		return WITH_RECURSIVE(queryName).AS().FIELD(sql);
	}
	private List<Object> keywords = new LinkedList<Object>();// can be string and SQL
	private List<Object> values = new LinkedList<Object>();
	int tabs = 0;

	boolean smartMode = true;//if on smart mode, adds commas, and parenthesis automatically if possible.

	String lastCall = null;

	private final String KEYWORD = "KEYWORD";

	private final String FUNCTION = "FUNCTION";

	private final String FIELD = "FIELD";

	private final String TABLE = "TABLE";

	private final String VALUE = "VALUE";

	public SQL ADD(){
		return keyword("ADD");
	}

	public SQL AND(){
		return keyword("AND");
	}

	public SQL AND(SQL sql){
		return keyword("AND").FN(sql);
	}


	public SQL AND(String column){
		return AND().FIELD(column);
	}

	public SQL AND_ON(String column1, String column2) {
		return AND().FIELD(column1).EQUAL_TO_FIELD(column2);
	}

	public SQL append(SQL sql){
		keywords.add(sql);
		lastCall = KEYWORD;
		return this;
	}

	public SQL AS(){
		return keyword("AS");
	}

	public SQL AS(SQL sql){
		return AS().FIELD(sql);
	}
	public SQL AS(String columnAs){
		AS().FIELD(columnAs);
		lastCall = FIELD;
		return this;
	}
	public SQL ASC(){
		keyword("ASC");
		lastCall = FIELD;
		return this;
	}
	public Breakdown build(){
		Breakdown bk = new Breakdown(); 
		build(bk, this);
		return bk;
	}
	public Breakdown build(Breakdown bk, SQL passed){
		List<Object> passedKeywords = passed.keywords;
		List<Object> passedValues = passed.values;

		for(Object keyword : passedKeywords){
			Class<?> keyClass = keyword.getClass();
			if(keyClass.equals(String.class)){
				bk.appendSp((String)keyword);
			}
			else if(keyClass.equals(SQL.class)){
				build(bk, (SQL)keyword);
			}
		}
		for(Object value : passedValues){
			bk.addParameter(value);
		}
		return bk;
	}
	public SQL CASCADE(){
		return keyword("CASCADE");
	}
	public SQL CASE(){
		return keyword("CASE");
	}
	public SQL CAST(){
		return keyword("CAST");
	}

	public SQL chars(String keyword){
		keywords.add(keyword);
		return this;
	}

	public SQL CHECK(){
		return keyword("CHECK").ln();
	}

	public SQL closeParen(){
		lastCall = "_CLOSE_PAREN_";
		return chars(")");
	}

	public SQL COALESCE(){
		return keyword("COALESCE");
	}
	public SQL COLUMN(){
		return keyword("COLUMN");
	}
	public SQL comma(){
		return chars(",");
	}
	public SQL CONNECT_BY(){
		return keyword("CONNECT BY");
	}
	public SQL CONSTRAINT(String constraintName){
		return keyword("CONSTRAINT").keyword(constraintName).ln();
	}
	public SQL CROSS_JOIN(String table){
		return keyword("CROSS JOIN").FIELD(table).ln();
	}
	public SQL DECODE(){
		return keyword("DECODE");
	}
	public SQL DESC(){
		keyword("DESC");
		lastCall = FIELD;
		return this;
	}
	public SQL DISTINCT(String... columns){
		return keyword("DISTINCT").FIELD(columns);
	}
	public SQL DISTINCT_ON(String... columns){
		keyword("DISTINCT ON");
		openParen();
		FIELD(columns);
		closeParen();
		lastCall = "DISTINCT_ON";
		return this;
	}
	public SQL EQUAL() {
		return keyword("=");
	}
	public SQL EQUAL(Object value){
		return EQUAL().VALUE(value);
	}
	public SQL EQUAL_TO(Object value){
		return EQUAL().VALUE(value);
	}
	public SQL EQUAL_TO_FIELD(String column) {
		return EQUAL().FIELD(column);
	}
	public SQL EXCEPT(SQL sql){
		return keyword("EXCEPT").FIELD(sql);
	}
	public SQL EXCEPT_ALL(SQL sql){
		return keyword("EXCEPT ALL").FIELD(sql);
	}
	public SQL EXIST(SQL sql){
		keyword("EXIST");
		openParen();
		FIELD(sql);
		closeParen();
		return this;
	}

	private SQL field(SQL sql){
		keywords.add(sql);
		return this;
	}

	/**
	 * Wraps with open and close parenthesis the SQL
	 * @param sql
	 * @return
	 */
	public SQL FIELD(SQL sql){
		smartCommaFnField();
		openParen();
		field(sql);
		closeParen();
		lastCall = FIELD;
		return this;
	}

	private SQL smartCommaFnField(){
		if(smartMode && lastCall != null && (lastCall.equals(FIELD) || lastCall.equals(FUNCTION))){
			comma();
		}
		return this;
	}

	/**
	 * Does not wraps the SQL statements with parenthesis, to avoid unnecessary characters on the query
	 * @param sql
	 * @return
	 */
	public SQL FN(SQL sql){
		smartCommaFnField();
		field(sql);
		lastCall = FUNCTION;
		return this;
	}

	public SQL FIELD(String keyword){
		smartCommaFnField();
		keyword(keyword);
		lastCall = FIELD;
		return this;
	}
	public SQL FIELD(String... columns){
		for(String col : columns){
			FIELD(col);
		}
		return this;
	}

	public SQL FOR(){
		return keyword("FOR");
	}
	public SQL FOREIGN_KEY(String...columns){
		return keyword("FOREIGN KEY").FIELD(columns).ln();
	}

	public SQL FROM(){
		return keyword("FROM");
	}

	public SQL FROM(SQL sql){
		return FROM().FIELD(sql);
	}

	public SQL FROM(String table){
		FROM().keyword(table);
		lastCall = TABLE;
		return this;
	}

	public SQL FROM(String table, String... otherTables){
		List<String> tableList = new ArrayList<String>();
		tableList.add(table);
		for(String tbl : otherTables){
			tableList.add(tbl);
		}
		return FROM(tableList.toArray(new String[tableList.size()]));
	}
	public SQL FROM(String[] tables){
		FROM();
		for(String tbl : tables){
			table(tbl);
		}
		return this;
	}


	public SQL FULL_OUTER_JOIN(String table){
		return keyword("FULL OUTER JOIN").FIELD(table).ln();
	}

	public SQL function(String function, SQL sql){
		keyword(function);
		lastCall = FUNCTION;
		openParen();
		field(sql);
		closeParen();
		lastCall = FUNCTION;
		return this;
	}

	public SQL function(String function, String column){
		keyword(function);
		lastCall = FUNCTION;
		openParen();
		FIELD(column);
		closeParen();
		lastCall = FUNCTION;
		return this;
	}

	public SQL FUNCTION(String function, SQL sql){
		return function(function, sql);
	}

	public SQL FUNCTION(String function, String column){
		return function(function, column);
	}

	public SQL GREATER_THAN(Object value){
		return keyword(">").VALUE(value);
	}

	public SQL GREATER_THAN(SQL sql){
		return keyword(">").FIELD(sql);
	}
	public SQL GREATER_THAN_OR_EQUAL(Object value){
		return keyword(">=").VALUE(value);
	}

	public SQL GROUP_BY(SQL sql){
		return keyword("GROUP BY").FN(sql);
	}
	public SQL GROUP_BY(String... column){
		return keyword("GROUP BY").FIELD(column);
	}
	/**
	 * As much as possible, don't use this
	 * @param expression
	 * @return
	 */
	public SQL HAVING(String column1){
		return keyword("HAVING").FIELD(column1);
	}
	public SQL IF(){
		return keyword("IF");
	}
	public SQL IF_EXISTS(){
		return keyword("IF EXISTS").ln();
	}
	public SQL IF_NOT_EXIST(){
		return keyword("IF NOT EXIST");
	}

	public SQL IN(Object... value){
		keyword("IN");
		openParen();
		boolean doComma = false;
		for(Object v : value){
			if(doComma){comma();}else{doComma=true;}
			VALUE(v);
		}
		closeParen();
		return this;
	}

	public SQL IN(SQL sql){
		return keyword("IN").FIELD(sql);
	}
	public SQL INDEX(String indexName, String columns){
		return keyword("INDEX").keyword(indexName).FIELD(columns);
	}

	public SQL INHERITS(String table){
		return keyword("INHERITS").openParen().FIELD(table).closeParen();
	}

	public SQL INNER_JOIN(String table){
		return keyword("INNER JOIN").FIELD(table).ln();
	}

	public SQL INTERSECT(SQL sql){
		return keyword("INTERSECT").field(sql);
	}

	public SQL INTERSECT_ALL(SQL sql){
		return keyword("INTERSECT ALL").FIELD(sql);
	}

	public SQL INTO(String table){
		keyword("INTO").FIELD(table);
		lastCall = TABLE;
		return this;
	}
	public SQL IS_NOT_NULL(){
		return keyword("IS NOT NULL");
	}

	public SQL IS_NULL(){
		return keyword("IS NULL");
	}

	public SQL keyword(String keyword){
		keywords.add(keyword);
		lastCall = KEYWORD;
		return this;
	}

	public SQL LEFT_JOIN(String table){
		return keyword("LEFT JOIN").FIELD(table).ln();
	}

	public SQL LEFT_OUTER_JOIN(String table){
		return keyword("LEFT OUTER JOIN").FIELD(table).ln();
	}

	public SQL LESS_THAN(Object value){
		return keyword("<").VALUE(value);
	}
	public SQL LESS_THAN(SQL sql){
		return keyword("<").FIELD(sql);
	}
	public SQL LESS_THAN_OR_EQUAL(Object value){
		return keyword("<").EQUAL().VALUE(value);
	}

	public SQL LIMIT(int limit){
		return keyword("LIMIT").keyword(limit+"");
	}

	public SQL ln(){
		return keyword("\n");
	}

	public SQL MATCH_FULL(){
		return keyword("MATCH FULL").ln();
	}


	public SQL MATCH_SIMPLE(){
		return keyword("MATCH SIMPLE").ln();
	}

	public SQL NOT_EQUAL_TO(Object value){
		return keyword("!=").VALUE(value);
	}

	public SQL NOT_EQUAL_TO_FIELD(String column){
		return keyword("!=").FIELD(column);
	}

	public SQL NOT_EXIST(SQL sql){
		keyword("NOT EXIST");
		FIELD(sql);
		return this;
	}
	public SQL NOT_IN(Object... value){
		keyword("NOT IN");
		openParen();
		boolean doComma = false;
		for(Object v : value){
			if(doComma){comma();}else{doComma=true;}
			VALUE(v);
		}
		closeParen();
		return this;
	}

	public SQL NOT_IN(SQL sql){
		keyword("NOT IN");
		FIELD(sql);
		return this;
	}

	public SQL NOT_NULL() {
		return keyword("NOT NULL");
	}

	public SQL OFFSET(int offset){
		return keyword("OFFSET").keyword(offset+"");
	}

	public SQL ON(String column1){
		return keyword("ON").FIELD(column1);
	}
	public SQL ON(String column1, String column2){
		return keyword("ON").FIELD(column1).EQUAL().FIELD(column2).ln();
	}
	public SQL ON_DELETE(){
		return keyword("ON DELETE").ln();
	}
	public SQL ON_UPDATE(){
		return keyword("ON UPDATE").ln();
	}

	public SQL ONLY(){
		return keyword("ONLY");
	}
	public SQL openParen(){
		lastCall = "_OPEN_PAREN_";
		return chars("(");
	}
	public SQL OR(String column){
		return keyword("OR").FIELD(column);

	}
	public SQL ORDER_BY(String... field){
		keyword("ORDER BY");
		for(String f : field){
			FIELD(f);
		}
		return this;
	}
	public SQL PARTITION_BY(String... columns){
		return keyword("PARTITION BY").FIELD(columns);
	}
	public SQL PRIMARY_KEY(String...columns){
		return keyword("PRIMARY KEY").openParen().FIELD(columns).closeParen().ln();
	}

	public SQL REFERENCES(String table, String column){
		return keyword("REFERENCES").ln()
				.FIELD(table)
				.openParen().FIELD(column).ln().closeParen();
	}
	public SQL RENAME(){
		return keyword("RENAME");
	}

	public SQL RENAME_TO(String table){
		return RENAME().TO(table);
	}

	public SQL RETURNING(String column){
		return keyword("RETURNING").FIELD(column);
	}

	public SQL RIGHT_JOIN(String table){
		return keyword("RIGHT JOIN").FIELD(table).ln();
	}

	public SQL RIGHT_OUTER_JOIN(String table){
		return keyword("RIGHT OUTER JOIN").FIELD(table).ln();
	}

	public SQL SCHEMA(){
		return keyword("SCHEMA").ln();
	}
	public SQL SCHEMA(String schema){
		return SCHEMA().FIELD(schema);
	}
	public SQL SET() {
		return keyword("SET");
	}
	public SQL SET(String field) {
		return SET().FIELD(field);
	}
	public SQL SET(String field, Object value) {
		return SET().FIELD(field).EQUAL(value);
	}
	public SQL tab(){
		tabs++;
		for(int i = 0; i < tabs; i++){
			keyword("\t");
		}
		return this;
	}
	private SQL table(String tbl) {
		if(smartMode && lastCall != null && lastCall.equals(TABLE)){
			comma();
		}
		this.keywords.add(tbl);
		lastCall = TABLE;
		return this;
	}


	public SQL TABLE(String table){
		return keyword("TABLE").FIELD(table);
	}
	public SQL TABLE(String table, String...otherTables){
		keyword("TABLE");
		FIELD(table);
		lastCall = TABLE;
		for(String o : otherTables){
			FIELD(o);
		}
		return this;
	}
	public SQL TEMPORARY(){
		return keyword("TEMPORARY");
	}
	public SQL THEN(){
		return keyword("THEN");
	}

	public SQL TO(String table){
		return keyword("TO").TABLE(table);
	}

	public SQL UNION(SQL sql){
		return keyword("UNION").field(sql);
	}

	public SQL UNION_ALL(SQL sql){
		return keyword("UNION ALL").FIELD(sql);
	}

	public SQL UNIQUE(){
		return keyword("UNIQUE");
	}

	public SQL UNIQUE(String... uniqueColumns){
		return keyword("UNIQUE").openParen().FIELD(uniqueColumns).closeParen();
	}

	public SQL USING(String... column){
		return keyword("USING").FIELD(column);
	}

	public SQL VALUE(Object value){
		FIELD("?");
		values.add(value);
		lastCall = VALUE;
		return this;
	}
	public SQL VALUE(SQL sql){
		return FIELD(sql);
	}
	public SQL VALUES(){
		return keyword("VALUES");
	}

	public SQL VALUES(Object... objValue){
		VALUES().openParen();
		for(Object val : objValue){
			VALUE(val);
		}
		return closeParen();
	}

	public SQL WHEN(){
		return keyword("WHEN");
	}


	public SQL WHERE(){
		return keyword("WHERE");
	}

	public SQL WHERE(SQL sql){
		return WHERE().FIELD(sql);
	}

	public SQL WHERE(String column){
		return WHERE().FIELD(column);
	}
}
