package hr.fer.zemris.java.hw05.db;

/**
 * Razred kreira izraz koji predstavlja popis atributa koji se koriste za filtriranje studenata u bazi.
 * 
 * @author Ivan Jukić
 *
 */

public class ConditionalExpression {
	
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Postavlja određi atribut studenta, operator za usporedbu i tekst s kojim se 
	 * određeni atribut filtrira.
	 * 
	 * @param fieldGetter određeni atribut studenta koji se filtrira
	 * @param stringLiteral tekst s kojim se filtrira
	 * @param comparisonOperator operator uspoređivanja (kako će se usporediti)
	 */
	
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	
	
	/**
	 * Vraća IFieldValueGetter tj. određeni atribut koji se filtrira.
	 * 
	 * @return fieldGetter koji se koristi za vađenje atributa
	 */
	
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Postavlja fieldGetter tj. određeni atribut koji se filtrira.
	 * 
	 * @param fieldGetter koji se koristi za vađenje atributa
	 */
	
	public void setFieldGetter(IFieldValueGetter fieldGetter) {
		this.fieldGetter = fieldGetter;
	}

	/**
	 * Vraća tekst s kojim se filtrira.
	 * 
	 * @return tekst s kojim se filtrira.
	 */
	
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Postavlja tekst s kojim se filtrira.
	 * 
	 * @param stringLiteral tekst s kojim se filtrira.
	 */

	public void setStringLiteral(String stringLiteral) {
		this.stringLiteral = stringLiteral;
	}

	/**
	 * Vraća operator koji se koristi za filtriranje.
	 * 
	 * @return operator za filtriranje.
	 */

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Postavlja operator koji se koristi za filtriranje.
	 * 
	 * @param comparisonOperator operator koji se koristi za filtriranje.
	 */

	public void setComparisonOperator(IComparisonOperator comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}
	
}
