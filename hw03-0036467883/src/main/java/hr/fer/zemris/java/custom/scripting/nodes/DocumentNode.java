package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred predstavlja roditeljski čvor stabla kojeg SmartScriptParser vraća. Sadrži tekst dokumenta koji se parsira. 
 * 
 * @author Ivan Jukić
 *
 */

public class DocumentNode extends Node {

	String documentBody;
	
	/**
	 * Konstruktor sprema tekst dokumenta koji se parsira 
	 * 
	 * @param documentBody tekst dokumenta koji se parsira
	 */
	
	public DocumentNode(String documentBody) {
		this.documentBody = documentBody;
	}
	
	/**
	 * Provjera jesu li dva DocumentNode objekta jednaka. Provodi se tako da se uspoređuju sva djeca oba 
	 * čvora.
	 * 
	 * @param documentForCheck dokument za koji se provjerava je li jednak instanci razreda
	 * @return true ako su dva DocumentNode čvora jednaka
	 */
	
	public boolean equals(DocumentNode documentForCheck) {
		int numberOfChildren = numberOfChildren();
		for(int i = 0; i < numberOfChildren; i++) {
			if(!getChild(i).toString().equals(documentForCheck.getChild(i).toString())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metoda vraća String reprezentaciju sve djece ovog čvora.
	 * 
	 * @return String reprezentacija sve djece ovog čvora
	 */
	
	@Override
	public String toString(){
		int numberOfChildren = numberOfChildren();
		StringBuilder documentBodyBuilder = new StringBuilder();
		for(int i = 0; i < numberOfChildren; i++) {
			documentBodyBuilder.append(getChild(i));
		}
		return documentBodyBuilder.toString();
	}

}
