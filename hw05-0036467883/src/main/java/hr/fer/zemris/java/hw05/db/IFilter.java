package hr.fer.zemris.java.hw05.db;

/**
 * Predstavlja funkcionalno sučelje te je model filtera za pojedinog studenta.
 * 
 * @author Ivan Jukić
 *
 */

public interface IFilter {

	/**
	 * Ako uvjet vrijedi za pojedinog studenta vraća se {@true}.
	 * 
	 * @param record student za kojeg se provjerava uvjet.
	 * @return {@true} ako za studenta vrijedi uvjet
	 */

	public boolean accepts(StudentRecord record);
}
