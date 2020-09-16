package hr.fer.zemris.java.hw05.db;

/**
 * Predstavlja model operatora te sadrži jednu metodu koja provjerava da dva
 * izraza (riječ i uzorak) zadovoljavaju uvjet koji taj operator predstavlja.
 * 
 * @author Ivan Jukić
 *
 */

public interface IComparisonOperator {

	/**
	 * Vraća {@true} ako dva izraza zadovoljavaju određeni uvjet za zadani operator.
	 * 
	 * @param value1 prvi izraz kojeg se uspoređuje
	 * @param value2 drugi izraz s kojim se uspoređuje
	 * @return {@true} ako dva izraza zadovoljavaju određeni uvjet za zadani
	 *         operator
	 */

	public boolean satisfied(String value, String pattern);

}
