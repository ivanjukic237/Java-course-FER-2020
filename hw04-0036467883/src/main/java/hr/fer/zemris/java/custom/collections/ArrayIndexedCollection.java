package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred ArrayIndexedCollection predstavlja strukturu podataka listu koji pohranjuje niz objekata 
 * koristeći polje elements. Neki objekt se može pojaviti više puta, ali spremanje null reference nije dozvoljeno.
 * ArrayIndexedCollection nasljeđuje razred Collection. 
 * 
 * @author Ivan Jukić
 *
 *@param <E> tip elemenata kolekcije
 */

public class ArrayIndexedCollection<E> implements List<E> {
	private int capacity;
	private int size;
	private long modificationCount = 0;

	
	/**
	 * Polje elements sadrži elemente liste.
	 */
	
	private E[] elements;
	
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
	
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<E> collection, int initialCapacity) {
		
		 if(initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet ne smije biti manji od 1.");
			
		}else if(initialCapacity < size){
			capacity = collection.size();
		}else {
			capacity = initialCapacity;
		}
			this.elements = (E[]) new Object[capacity];
			this.addAll(collection);
	}
	
	/**
	 * Konstruktor razreda koji prima samo jedan argument, ulazni kapacitet te stvara listu s tim
	 * kapacitetom. 
	 * 
	 * @param initialCapacity početni kapacitet razreda
	 */
	
	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}
	
	/**
	 * Konstruktor razreda koji prima samo jedan argument collection, primjerak razreda Collection.
	 * Konstruktor stvara listu s kopiranim članovima od collection i početnim kapacitetom 16.
	 * 
	 * @param collection primjerak razreda Collection čiji se članovi kopiraju u listu
	 */
	
	public ArrayIndexedCollection(Collection<E> collection) {
		this(collection, 16);
	}
	
	/**
	 * Konstruktor razreda koji ne prima niti jedan argument. Stvara listu s početnim kapacitetom 16.
	 */
	
	public ArrayIndexedCollection() {
		this(null, 16);
	}
	
	/**
	 * Metoda add dodaje na vrh liste objekt.
	 * 
	 * @throws NullPointerException ako se u listu dodaje null referenca
	 * @param value objekt koji se dodaje na vrh liste
	 */
	
	@Override
	//Ako je veličina liste veća od kapaciteta, onda povećavamo kapacitet dva puta.
	public void add(E value) {
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
	
	@SuppressWarnings("unchecked")
	public void increaseCapacity() {
		capacity *= 2;
		E[] temp = (E[])new Object[capacity];
		
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
	
	public E get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index " + index + " nije validan za duljinu " + size() + ".");
		}else {
			return elements[index];
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
		modificationCount++;
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
	
	public void insert(E value, int position) {
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
		modificationCount++;
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
			modificationCount++;
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
	public E[] toArray() {
		@SuppressWarnings("unchecked")
		E[] listArray = (E[])new Object[size];
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
	
	/**
	 * Metoda stvara primjerak razreda ArrayListElementsGetter kojem je argument ova lista.
	 */
	
	public ElementsGetter<E> createElementsGetter() {
		return new ArrayListElementsGetter<E>(this);
	}
	
	/**
	 * Razred implementira sučelje ElementsGetter te predstavlja iterator po elementima liste.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private static class ArrayListElementsGetter<E> implements ElementsGetter<E> {
		int iteratorPointer = 0;
		private final long savedModificationCount;
		ArrayIndexedCollection<E> list;
		
		/**
		 * Konstruktor stvara primjerak razreda te sprema broj modifikacija napravljenih nad
		 * listom u trenutku stvaranja liste. 
		 * 
		 * @param list lista ArrayIndexedCollection nad kojom se radi iteracija po elementima
		 */
		
		private ArrayListElementsGetter(ArrayIndexedCollection<E> list) {
			this.savedModificationCount = list.modificationCount;
			this.list = list;
		}
		
		/**
		 * Metoda provjerava postoji li sljedeći element u listi za vratiti.
		 * 
		 * @return true ako postoji sljedeći element.
		 */
		
		public boolean hasNextElement() {
			
			if(iteratorPointer >= list.size) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * Metoda vraća sljedeći element u listi.
		 * 
		 * @throws ConcurrentModificationException ako se lista mijenjala nakon stvaranja primjerka razreda
		 * @throws NoSuchElementException ako se metoda pozove nakon što je iterator vratio već zadnji element liste
		 * @return sljedeći element u listi
		 */
		
		public E getNextElement() {
			
			if(this.savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException("Lista je mijenjana za vrijeme iteracije.");
			} else if(hasNextElement()) {
				E elementToReturn = list.elements[iteratorPointer];
				iteratorPointer++;
				return elementToReturn;
			} else {
				throw new NoSuchElementException("Element ne postoji.");
			}

		}	
	}
}
