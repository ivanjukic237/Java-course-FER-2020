package hr.fer.zemris.java.hw05.db;

/**
 * Predstavlja strategiju za vraćanje pojedinog atributa studenta.
 * 
 * @author Ivan Jukić
 *
 */

public interface IFieldValueGetter {

	/**
	 * Vraća određeni atribut studenta.
	 * 
	 * @param record student
	 * @return atribut studenta
	 */

	public String get(StudentRecord record);

}
