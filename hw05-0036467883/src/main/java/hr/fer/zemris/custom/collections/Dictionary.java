package hr.fer.zemris.custom.collections;

/**
 * Razred dictionary predstavlja listu uređenih parova ključeva i vrijednosti (ključ, vrijednost).
 * 
 * @author Ivan Jukić
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */

public class Dictionary<K, V> {

	/**
	 * Lista se koristi za spremanje uređenih parova (ključ, vrijednost).
	 */
	
	private ArrayIndexedCollection<OrderedPair> collection = new ArrayIndexedCollection<>();
	
	/**
	 * Razred predstavlja model uređenog para (ključ, vrijednost). Ključ ne smije biti null.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private class OrderedPair {
		private K key;
		private V value;
		
		/**
		 * Konstruktor postavlja vrijednosti uređenog para (ključ, vrijednost) = (key, value).
		 * 
		 * @throws NullPointerException ako je ključ jednak null referenci
		 * @param key vrijednost ključa
		 * @param value vrijednost
		 */
		
		private OrderedPair(K key, V value) {
			if(key == null) {
				throw new NullPointerException("Ključ ne smije biti null.");
			}
			this.key = key;
			this.value = value;
		}

		/**
		 * Vraća vrijednost ključa uređenog para.
		 * 
		 * @return vrijednost ključa uređenog para
		 */
		
		public K getKey() {
			return key;
		}
		
		/**
		 * Vraća vrijednost uređenog para.
		 * 
		 * @return vrijednost uređenog para
		 */
		
		public V getValue() {
			return value;
		}
		
		/**
		 * Postavlja vrijednost uređenog para.
		 * 
		 * @param value vrijednost uređenog para
		 */
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Provjerava je li Dictionary prazan.
	 * 
	 * @return {@code true} ako je Dictionary prazan
	 */
	
	public boolean isEmpty() {
		return this.collection.isEmpty();
	}
	
	/**
	 * Vraća broj uređenih parova kolekcije.
	 * 
	 * @return broj uređenih parova razreda.
	 */
	
	public int size() {
		return this.collection.size();
	}
	
	/**
	 * Briše sve elemente kolekcije.
	 */
	
	public void clear() {
		this.collection.clear();
	}
	
	/**
	 * Stavlja određeni uređeni par u kolekciju. Ako ključ postoji u kolekciji, njegova vrijednost
	 * se prebriše s novom, inače se dodaje na kraj kolekcije.
	 * 
	 * @param key vrijednost ključa uređenog para
	 * @param value vrijednost uređenog para
	 */
	
	// "gazi" eventualni postojeći zapis
	public void put(K key, V value) {
		boolean isAlreadyInCollection = false;
		ElementsGetter<OrderedPair> elementsGetter = this.collection.createElementsGetter();
		
		while(elementsGetter.hasNextElement()) {
			OrderedPair element = elementsGetter.getNextElement();
			
			if(element.getKey().equals(key)) {
				element.setValue(value);
				isAlreadyInCollection = true;
				break;
			}
		}
		
		if(!isAlreadyInCollection) {
			this.collection.add(new OrderedPair(key, value));
		}
	
	}
	
	/**
	 * Vraća vrijednost uređenog para određenog ključa. Ako u kolekciji ne postoji ključ vraća null.
	 * 
	 * @param key vrijednost traženog ključa
	 * @return vrijednost traženog uređenog para
	 */
	
	// ako ne postoji pripadni value, vraća null
	public V get(K key) {
		ElementsGetter<OrderedPair> elementsGetter = this.collection.createElementsGetter();

		while(elementsGetter.hasNextElement()) {
			OrderedPair element = elementsGetter.getNextElement();
			if(element.getKey().equals(key)) {
				return element.getValue();
			}
		}
		return null;
	}
}
