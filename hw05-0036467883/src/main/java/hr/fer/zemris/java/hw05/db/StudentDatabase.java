package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Razred predstavlja bazu podataka studenata.
 * 
 * @author Ivan Jukić
 *
 */

public class StudentDatabase {

	/**
	 * Mapa koja sadrži ključ-hash vrijednost JMBAG-a studenta i vrijednost objekt razreda
	 * StudentRecord. 
	 */
	
	LinkedHashMap<String, StudentRecord> indexedStudentRecords = new LinkedHashMap<>();

	/**
	 * Konstruktor postavlja u bazu objekte tipa StudentRecord. Prihvaća samo jedinstvene vrijednosti
	 * JMBAG-a i vrijednosti konačne ocjene od 1 do 5.
	 * 
	 * @throws NumberFormatException ako vrijednost konačne ocjene nije broj od 1 do 5.
	 * @param studentRecords polje objekata razreda StudentRecord.
	 */
	
	public StudentDatabase(String[] studentRecords) {
		for (String studentRecord : studentRecords) {
			String[] studentRecortArray = studentRecord.strip().split("\\t");

			String jmbag = studentRecortArray[0];
			String lastName = studentRecortArray[1];
			String firstName = studentRecortArray[2];
			String finalGrade = studentRecortArray[3];
			try {
				int finalGradeInt = Integer.parseInt(finalGrade);
				if (finalGradeInt > 5 || finalGradeInt < 1) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ex) {
				System.out.println("Ocjena za jmbag: " + jmbag + " nije broj od 1 do 5.");
			}
			if (indexedStudentRecords.containsKey(jmbag)) {
				throw new IllegalArgumentException("Jmbag " + jmbag + " već postoji u bazi.");
			}
			indexedStudentRecords.put(jmbag, new StudentRecord(jmbag, lastName, firstName, finalGrade));
		}
	}

	/**
	 * Metoda vraća studenta iz baze za određenu vrijednost JMBAG-a.
	 * 
	 * @param jmbag JMBAG studenta koji se traži.
	 * @return student za čiji se JMBAG traži
	 */
	
	public StudentRecord forJMBAG(String jmbag) {
		return indexedStudentRecords.get(jmbag);
	}

	/**
	 * Vraća listu filtriranih studenata po nekom određenom filteru.
	 * 
	 * @param filter filter po kojem se filtriraju studenti u bazi
	 * @return listu filtriranih studenata
	 */
	
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tempList = new ArrayList<>();
		for (StudentRecord studentRecord : indexedStudentRecords.values()) {
			if (filter.accepts(studentRecord)) {
				tempList.add(studentRecord);
			}
		}
		return tempList;
	}

}
