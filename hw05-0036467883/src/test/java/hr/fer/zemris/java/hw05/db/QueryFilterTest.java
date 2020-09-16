package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Testira se razred QueryFilter.
 * 
 * @author Ivan Jukić
 *
 */

class QueryFilterTest {

	ConditionalExpression expr1True = new ConditionalExpression(
			 FieldValueGetters.LAST_NAME,
			 "Bos*",
			 ComparisonOperators.LIKE
			);
	
	ConditionalExpression expr2True = new ConditionalExpression(
			 FieldValueGetters.JMBAG,
			 "0000000001",
			 ComparisonOperators.EQUALS
			);
	
	ConditionalExpression expr3False = new ConditionalExpression(
			 FieldValueGetters.FIRST_NAME,
			 "Nije Ivan",
			 ComparisonOperators.EQUALS
			);
	
	List<ConditionalExpression> listOfConditionalExpressions = new ArrayList<>();
	
	
	StudentRecord record = new StudentRecord("0000000001", "Bosković", "Ivan", "3");
	
	/**
	 * Testiraju se 2 dobra uvjeta.
	 */
	
	@Test
	void testTwoTrueConditions() {
		listOfConditionalExpressions.add(expr1True);
		listOfConditionalExpressions.add(expr2True);
		QueryFilter filter = new QueryFilter(listOfConditionalExpressions);
		
		assertTrue(filter.accepts(record));
	}
	
	/**
	 * Testiraju se 2 dobra i jedan krivi uvjet.
	 */
	
	@Test
	void testOneFalseCondition() {
		listOfConditionalExpressions.add(expr1True);
		listOfConditionalExpressions.add(expr2True);
		listOfConditionalExpressions.add(expr3False);
		QueryFilter filter = new QueryFilter(listOfConditionalExpressions);
		
		assertFalse(filter.accepts(record));
	}

}
