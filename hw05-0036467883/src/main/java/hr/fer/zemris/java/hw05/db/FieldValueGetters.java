package hr.fer.zemris.java.hw05.db;

/**
 * Predstavlja model popisa razreda koji implementiraju funkcionalna sučelja za
 * vraćanje određenog atributa studenta.
 * 
 * @author Ivan Jukić
 *
 */

public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = new firstNameGetter();
	public static final IFieldValueGetter LAST_NAME = new lastNameGetter();
	public static final IFieldValueGetter JMBAG = new jmbagGetter();
	public static final IFieldValueGetter FINAL_GRADE = new finalGradeGetter();

	/**
	 * Vraća ime studenta.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class firstNameGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getFirstName();
		}

	}

	/**
	 * Vraća prezime studenta.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class lastNameGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getLastName();
		}

	}

	/**
	 * Vraća JMBAG studenta.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class jmbagGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getJmbag();
		}

	}

	/**
	 * Vraća konačnu ocjenu studenta.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class finalGradeGetter implements IFieldValueGetter {

		@Override
		public String get(StudentRecord record) {
			return record.getFinalGrade();
		}

	}

}
