package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Razred predstavlja način za filtriranje studenata u bazi po dobivenoj listi
 * uvjeta.
 * 
 * @author Ivan Jukić
 *
 */

public class QueryFilter implements IFilter {

	/**
	 * Lista uvjeta po kojima se filtrira.
	 */

	List<ConditionalExpression> listOfConditionalExpressions;

	public QueryFilter(List<ConditionalExpression> listOfConditionalExpressions) {
		this.listOfConditionalExpressions = listOfConditionalExpressions;
	}

	/**
	 * Provjerava za koje sve studente vrijede određeni uvjeti.
	 * 
	 * @return {@true} ako za pojedinog studenta vrijede svi uvjeti.
	 */

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression conditionalExpression : listOfConditionalExpressions) {
			if (!conditionalExpression.getComparisonOperator().satisfied(
					conditionalExpression.getFieldGetter().get(record), conditionalExpression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
