package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testira se razred ConditionalExpression.
 * 
 * @author Ivan Jukić
 *
 */

class ConditionalExpressionTest {

	/**
	 * Testira se ConditionalExpression za istiniti filter.
	 */
	
	@Test
	void testTrue() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
				StudentRecord record = new StudentRecord("0000000001", "Bosković", "Ivan", "3");
				boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), // returns lastName from given record
				 expr.getStringLiteral()); // returns "Bos*"
				assertTrue(recordSatisfies);
	}
	
	/**
	 * Testira se ConditionalExpression za neistiniti filter.
	 */
	
	@Test
	void testFalse() {
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*A",
				 ComparisonOperators.LIKE
				);
				StudentRecord record = new StudentRecord("0000000001", "Bosković", "Ivan", "3");
				boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), // returns lastName from given record
				 expr.getStringLiteral()); // returns "Bos*"
				assertFalse(recordSatisfies);
	}
	
}
