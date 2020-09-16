package hr.fer.zemris.java.custom.collections;

/**
 * Razred ArrayIndexedCollection predstavlja strukturu podataka listu koji pohranjuje niz objekata 
 * koristeći polje elements. Neki objekt se može pojaviti više puta, ali spremanje null reference nije dozvoljeno.
 * ArrayIndexedCollection nasljeđuje razred Collection. 
 * 
 * @author Ivan Jukić
 *
 */

public class ArrayIndexedCollection extends Collection{
	private int capacity;
	private int size;
	
	/**
	 * Polje elements sadrži elemente liste.
	 */
	
	private Object[] elements;
	
	/**
	 * Konstruktor razreda koji prima argumente collection razreda Collection i initialCapacity,
	 * broj koji zadaje početni kapacitet liste. Konstruktor stvara listu gdje objekti koji su sadržani
	 * u kolekciji collection se kopiraju u primjerak razreda.
	 * 
	 * @throws NullPointerException ako je argument collection jednak null
	 * @throws IllegalArgumentException ako je ulazni kapacitet manji od jedan
	 * @param collection primjerak razreda Collection čiji se članovi kopiraju u listu
	 * @param initialCapacity početni kapacitet razreda
	 */
	
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		if(collection == null) {
			throw new NullPointerException("Kolekcija ne smije biti null.");

		}
		
		 if(initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet ne smije biti manji od 1.");
			
		}else if(initialCapacity < size){
			capacity = collection.size();
		}else {
			capacity = initialCapacity;
		}
			elements = new Object[capacity];
			this.addAll(collection);
	}
	
	/**
	 * Konstruktor razreda koji prima samo jedan argument, ulazni kapacitet te stvara listu s tim
	 * kapacitetom. 
	 * 
	 * @param initialCapacity početni kapacitet razreda
	 */
	
	public ArrayIndexedCollection(int initialCapacity) {
		this(new Collection(), initialCapacity);
	}
	
	/**
	 * Konstruktor razreda koji prima samo jedan argument collection, primjerak razreda Collection.
	 * Konstruktor stvara listu s kopiranim članovima od collection i početnim kapacitetom 16.
	 * 
	 * @param collection primjerak razreda Collection čiji se članovi kopiraju u listu
	 */
	
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}
	
	/**
	 * Konstruktor razreda koji ne prima niti jedan argument. Stvara listu s početnim kapacitetom 16.
	 */
	
	public ArrayIndexedCollection() {
		this(new Collection(), 16);
	}
	
	/**
	 * Metoda add dodaje na vrh liste objekt.
	 * 
	 * @throws NullPointerException ako se u listu dodaje null referenca
	 * @param value objekt koji se dodaje na vrh liste
	 */
	
	@Override
	//Ako je veličina liste veća od kapaciteta, onda povećavamo kapacitet dva puta.
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Element koji se dodaje ne smije biti null.");
		}else if(size >= capacity) {
			increaseCapacity();
		}
			elements[size] = value;
			size++;
	}
	
	/**
	 * Metoda increaseCapacity povećava kapacitet liste dva puta.
	 */
	
	public void increaseCapacity() {
		capacity *= 2;
		Object[] temp = new Object[capacity];
		
		for(int i = 0; i < size; i++) {
			temp[i] = elements[i];
		}
		elements = temp;
	}
	
	/**
	 * Metoda get vraća dodani element liste na određenom položaju.
	 * 
	 * @param index položaj elementa u listi
	 * @return element na položaju index
	 */
	
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index " + index + " nije validan za duljinu " + size() + ".");
		}else {
			return elements[index];
		}
	}
	
	/**
	 * Metoda forEach poziva metodu process za argument processor za svaki član liste. 
	 * 
	 * @param processor za koji se poziva metoda process za svaki član liste
	 */
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(get(i));
		}
	}
	
	/**
	 * Metoda clear svaki član liste postavlja na referencu null.
	 */
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Metoda insert ubacuje objekt u listu na određen položaj.
	 * 
	 * @throws NullPointerException ako se pokuša u listu dodati null referenca
	 * @throws IndexOutOfBoundsException ako se objekt pokuša ubaciti na položaj koji je manji od nula
	 * ili veći od broja članova liste
	 * @param value objekt koji se ubacuje u listu
	 * @param position mjesto na koje se objekt ubacuje
	 */
	
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException("Element koji se ubacuje u listu ne smije biti null.");
		}else if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Index " + position + " nije validan za duljinu " + size() + ".");
		}else if(size >= capacity) {
			increaseCapacity();
		}
		for(int i = size + 1; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Metoda indexOf vraća položaj traženog objekta ili -1 ako se objekt ne nalazi u listi.
	 * 
	 * @param value objekt za kojeg se traži položaj
	 * @return položaj objekta ili -1 ako se objekt ne nalazi u listi
	 */
	
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;	
	}
	
	/**
	 * Metoda printCollection vraća String kao članove liste u nizu od prvog dodanog do zadnjeg dodanog.
	 * 
	 * @return String koji predstavlja listu kao niz
	 */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < size; i++) {
			if(i == size - 1) {
				sb.append(elements[i]);
			}else {
				sb.append(elements[i] + ", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Metoda remove briše referencu na objekt u listi na određenom položaju.
	 * 
	 * @throws IndexOutOfBoundsException ako se objekt pokuša izbrisati sa položaja koji je manji od nula
	 * ili veći od broja članova liste
	 * @param index položaj objekta u listi koji se briše
	 */
	
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index " + index + " nije validan za duljinu " + size() + ".");
		}else {
			elements[index] = null;
			for(int i = index; i < size - 1; i++) {
				elements[i] = elements[i + 1];
			}
			elements[size - 1] = null;
			size--;
		}
	}
	
	/**
	 * Metoda briše prvi primjer određenog objekta iz liste.
	 * 
	 * @return true ako je objekt izbrisan
	 */
	
	@Override
	public boolean remove(Object value) {
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metoda contains provjerava je li dani objekt sadržan u listi.
	 * 
	 * @return je li objekt sadržan u listi
	 */
	
	public boolean contains(Object value) {
		for(int i = 0; i < size; i++) {
			if(get(i).equals(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Metoda size vraća vrijednost veličine ove liste.
	 * 
	 * @return veličinu liste
	 */
	
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Metoda toArray vraća elemente liste kao polje.
	 * 
	 * @return elemente liste kao polje
	 */
	
	@Override
	public Object[] toArray() {
		Object[] listArray = new Object[size];
		for(int i = 0; i < size; i++) {
			listArray[i] = elements[i];
		}
		return listArray;
	}
	
	/**
	 * Metoda capacity vraća kapacitet liste.
	 * 
	 * @return kapacitet liste
	 */
	
	public int capacity() {
		return this.capacity;
	}
}
