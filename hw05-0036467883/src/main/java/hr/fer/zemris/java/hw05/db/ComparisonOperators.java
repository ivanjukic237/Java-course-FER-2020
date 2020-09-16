package hr.fer.zemris.java.hw05.db;

/**
 * Predstavlja popis operatora za filtriranje. Uspoređuje riječ s nekim uzorkom
 * ovisno o odabiru operatora. Operatori mogu biti < manje, > veće, <= manje ili
 * jednako, >= veće ili jednako, = jednako, != nejednako i LIKE operator koji
 * provjerava je li riječ počinje i završava na određen način. Za sve operatore
 * osim LIKE se koristi metoda String:compareTo.
 * 
 * @author Ivan Jukić
 *
 */

public class ComparisonOperators {

	public static final IComparisonOperator LESS = new lessComparisonOperator();
	public static final IComparisonOperator LESS_OR_EQUALS = new lessOrEqualComparisonOperator();
	public static final IComparisonOperator GREATER = new greaterComparisonOperator();
	public static final IComparisonOperator GREATER_OR_EQUALS = new greaterOrEqualsComparisonOperator();
	public static final IComparisonOperator EQUALS = new equalsComparisonOperator();
	public static final IComparisonOperator NOT_EQUALS = new notEqualsComparisonOperator();
	public static final IComparisonOperator LIKE = new likelComparisonOperator();

	/**
	 * Predstavlja operator < manje.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class lessComparisonOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value, String pattern) {
			return value.compareTo(pattern) < 0;
		}

	}

	/**
	 * Predstavlja operator <= manje ili jednako.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class lessOrEqualComparisonOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value, String pattern) {
			return value.compareTo(pattern) <= 0;
		}

	}

	/**
	 * Predstavlja operator > veće
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class greaterComparisonOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value, String pattern) {
			return value.compareTo(pattern) > 0;

		}

	}

	/**
	 * Predstavlja operator >= veće ili jednako.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class greaterOrEqualsComparisonOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value, String pattern) {
			return value.compareTo(pattern) >= 0;

		}

	}

	/**
	 * Predstavlja operator = jednako.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class equalsComparisonOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value, String pattern) {
			return value.compareTo(pattern) == 0;

		}

	}

	/**
	 * Predstavlja operator nejednako za riječ i uzorak.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class notEqualsComparisonOperator implements IComparisonOperator {

		@Override
		public boolean satisfied(String value, String pattern) {
			return value.compareTo(pattern) != 0;

		}

	}

	/**
	 * Razred predstavlja like operator za riječ i uzorak. Uzorak može sadržati samo
	 * jedan wildcard operator *. Ako je operator na početku uzorka, onda se
	 * provjerava je li početak riječi jednaka uzorku. Ako je operator na kraju
	 * uzorka, onda se provjerava je li kraj riječi jednak uzorku. Ako je operator
	 * usred uzorka, onda se provjerava je li početak riječi jednak uzorku prije
	 * operatora i je li kraj riječi jednak uzorku poslije operatora. Ako nema
	 * operatora, provjerava se je li cijeli uzorak jednak riječi.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class likelComparisonOperator implements IComparisonOperator {

		/**
		 * Vraća true ako uzorak odgovara riječi.
		 * 
		 * @throws IllegalArgumentException ako imamo više od jednog wildcard operatora
		 * *
		 * @param value   riječ za koju promatramo uzorak
		 * @param pattern uzorak po kojem uspoređujemo
		 */

		@Override
		public boolean satisfied(String value, String pattern) {
			// ako nema wildcard operatora, onda samo usporedimo stringove
			if (!pattern.contains("*")) {
				return value.compareTo(pattern) == 0;
			}

			int indexOfWildcard = pattern.indexOf('*');
			if (indexOfWildcard != pattern.lastIndexOf('*')) {
				throw new IllegalArgumentException("Dozvoljen je samo jedan * operator.");
			}
			int patternLength = pattern.length();
			int valueLength = value.length();

			// ako je duljina riječi manja od duljine uzorka, onda su različiti
			if (indexOfWildcard == patternLength - 1 && valueLength > patternLength - 1) {

				if (pattern.substring(0, patternLength - 1).equals(value.substring(0, patternLength - 1))) {
					return true;
				} else {
					return false;
				}
			} else if (indexOfWildcard == 0 && valueLength > patternLength - 1) {
				if (pattern.substring(1, patternLength)
						.equals(value.substring(valueLength - patternLength + 1, valueLength))) {
					return true;
				} else {
					return false;
				}
			} else {
				String patternBeforeWildcard = pattern.substring(0, indexOfWildcard);
				String patternAfterWildcard = pattern.substring(indexOfWildcard + 1, patternLength);
				if (patternAfterWildcard.length() > valueLength || patternBeforeWildcard.length() > valueLength) {
					return false;
				}
				if (value.substring(0, patternBeforeWildcard.length()).equals(patternBeforeWildcard)
						&& value.substring(valueLength - patternAfterWildcard.length(), valueLength)
								.equals(patternAfterWildcard)) {
					return true;

				} else {
					return false;
				}
			}
		}

	}
}
