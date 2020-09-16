package hr.fer.zemris.java.hw05.db;

/**
 * Razred predstavlja jednog studenta u bazi. Student ima atribute JMBAG, prezime, ime i
 * konačnu ocjenu.
 * 
 * @author Ivan Jukić
 *
 */

public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private String finalGrade;
	
	/**
	 * Vraća String reprezentaciju studenta koristeći sve njegove atribute.
	 * 
	 * @return String reprezentaciju studenta
	 */
	
	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", finalGrade="
				+ finalGrade + "]";
	}

	/**
	 * Konstruktor postavlja atribute pojedinog studenta.
	 * 
	 * @param jmbag JMBAG studenta
	 * @param lastName prezime studenta
	 * @param firstName ime studenta
	 * @param finalGrade konačna ocjena studenta
	 */
	
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Vraća JMBAG studenta
	 *  
	 * @return JMBAG studenta
	 */
	
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Postavlja JMBAG studenta
	 */
	
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Vraća prezime studenta
	 *  
	 * @return prezime studenta
	 */
	
	public String getLastName() {
		return lastName;
	}

	/**
	 * Postavlja prezime studenta 
	 */
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Vraća ime studenta
	 *  
	 * @return ime studenta
	 */
	
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Postavlja ime studenta 
	 */
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Vraća konačnu ocjenu studenta
	 *  
	 * @return konačnu ocjenu studenta
	 */
	
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Postavlja konačnu ocjenu studenta 
	 */
	
	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Vraća hash vrijednost studenta pomoću JMBAG-a.
	 * 
	 * @return hash vrijednost studenta
	 */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	
	/**
	 * Vraća {@true} ako dva studenta imaju jednak JMBAG.
	 * 
	 * @return {@true} ako su studenti jednaki
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
}
