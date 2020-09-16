package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;

/**
 * Razred radi tablicu za određenu količinu predanih filtriranih podataka iz
 * baze. Širina svakog stupca će ovisiti o duljini najdužeg atributa iz ćelija
 * stupca.
 * 
 * @author Ivan Jukić
 *
 */

public class QueryToString {

	/**
	 * Lista filtriranih studenata iz koje se radi tablica.
	 */

	private ArrayList<StudentRecord> listOfFilteredStudents;

	private int lastNameLength = 0;
	private int firstNameLength = 0;
	private int jmbagLength = 0;
	private int finalGradeLength = 0;

	/**
	 * Konstruktor prima listu filtriranih studenata i postavlja je u razredu. Osim
	 * toga traži broj znakova svih najdužih atributa iz liste i postavlja ih.
	 * 
	 * @param listOfFilteredStudents lista filtriranih studenata
	 */

	public QueryToString(ArrayList<StudentRecord> listOfFilteredStudents) {
		this.listOfFilteredStudents = listOfFilteredStudents;

		for (StudentRecord r : listOfFilteredStudents) {
			if (r.getLastName().length() > lastNameLength) {
				lastNameLength = r.getLastName().length();
			}
			if (r.getFirstName().length() > firstNameLength) {
				firstNameLength = r.getFirstName().length();
			}
			if (r.getJmbag().length() > jmbagLength) {
				jmbagLength = r.getJmbag().length();
			}
			if (r.getFirstName().length() > finalGradeLength) {
				finalGradeLength = r.getFinalGrade().length();
			}
		}
	}

	/**
	 * Vraća gornji i donji okvir tablice. Okvir će ovisiti o duljini najdužeg
	 * atributa u filtriranoj listi.
	 * 
	 * @return gornj i donji okvir tablice
	 */

	private String getFrame() {
		StringBuilder frame = new StringBuilder("+");
		for (int i = 0; i < jmbagLength + 2; i++) {
			frame.append("=");
		}
		frame.append("+");
		for (int i = 0; i < lastNameLength + 2; i++) {
			frame.append("=");
		}
		frame.append("+");
		for (int i = 0; i < firstNameLength + 2; i++) {
			frame.append("=");
		}
		frame.append("+");
		for (int i = 0; i < finalGradeLength + 2; i++) {
			frame.append("=");
		}
		frame.append("+");
		return frame.toString();
	}

	/**
	 * Vraća tablicu filtriranih studenata.
	 * 
	 * @return tablica filtriranih studenata.
	 */

	public String getQuery() {
		if (listOfFilteredStudents.size() == 0) {
			return "";
		}
		StringBuilder record = new StringBuilder();
		record.append(getFrame() + "\n");

		for (StudentRecord r : listOfFilteredStudents) {
			record.append("| ");
			record.append(r.getJmbag());
			for (int i = 0; i < jmbagLength - r.getJmbag().length() + 1; i++) {
				record.append(" ");
			}
			record.append("| ");
			record.append(r.getLastName());
			for (int i = 0; i < lastNameLength - r.getLastName().length() + 1; i++) {
				record.append(" ");
			}
			record.append("| ");
			record.append(r.getFirstName());
			for (int i = 0; i < firstNameLength - r.getFirstName().length() + 1; i++) {
				record.append(" ");
			}
			record.append("| ");
			record.append(r.getFinalGrade() + " |\n");
		}
		record.append(getFrame());
		return record.toString();
	}

}
