package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
* Razred LinkedListIndexedCollection predstavlja strukturu podataka vezane liste koji pohranjuje niz objekata 
 * koristeći vezane objekte tipa Node. Neki objekt se može pojaviti više puta, ali spremanje null reference nije 
 * dozvoljeno. LinkedListIndexedCollection nasljeđuje razred Collection. 
 * 
 * @author Ivan Jukić
 *
 */
public class LinkedListIndexedCollection implements List {
	private int size;
	private Node first;
	private Node last;
	private long modificationCount = 0;
	
	/**
	 * Razred Node predstavlja članove vezane liste koji sprema vrijednost čvora, referencu na prijašnji čvor i referencu
	 * na sljedeći čvor. Ako je čvor prvi član liste onda mu je prijašnji čvor null referenca, a ako je zadnji član liste
	 * onda mu je sljedeći član null.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private static class Node{
		Node previous;
		Node next;
		Object value;
	}
	
	/**
	 * Konstruktor razreda koji inicijalizira vezanu listu. Stavlja da su prvi 
	 * i zadnji član liste jednaki null referenci.
	 */
	
	public LinkedListIndexedCollection() {
		first = last = null;
	}
	
	/**
	 * Konstruktor koji prima kao argument drugu kolekciju te je kopira u vezanu listu.
	 * 
	 * @param collection druga kolekcija koja se kopira u vezanu listu.
	 */
	
	public LinkedListIndexedCollection(Collection collection) {
		this.addAll(collection);
	}
	
	/**
	 * Metoda add dodaje na vrh liste objekt.
	 * 
	 * @throws NullPointerException ako se u listu dodaje null referenca
	 * @param value objekt koji se dodaje na vrh liste
	 */
	
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Vrijednost ne smije biti null.");
		}
		
		Node nextNode = new Node();
		nextNode.value = value;
		
		if(last == null && first == null) {
			
			first = nextNode;
			last = nextNode;
		}else {
			nextNode.previous = last;
			last.next = nextNode;
			last = nextNode;
		}
		size++;
	}
	
	/**
	 * Metoda get vraća dodani element liste na određenom položaju.
	 * 
	 * @param index položaj elementa u listi
	 * @return element na položaju index
	 */
	
	public Object get(int index) {
		Node currentNode = null;
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index " + index + " nije validan za duljinu " + size() + ".");
		}else if(index < size / 2){
			currentNode = first;
			for(int i = 0; i < index; i++) {
				currentNode = currentNode.next;
			}
		}else {
			currentNode = last;
			for(int i = size - 1; i > index; i--) {
				currentNode = currentNode.previous;
			}
		}
		return currentNode.value;
	}
	
	
	/** Metoda printCollection vraća String kao članove liste u nizu od prvog dodanog do zadnjeg dodanog.
	 * 
	 * @return String koji predstavlja listu kao niz
	 */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node currentNode = first;
		for(int i = 0; i < size; i++) {
			if(i == size - 1) {
				sb.append(currentNode.value);
			}else {
				sb.append(currentNode.value + ", ");
			}
			currentNode = currentNode.next;
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Metoda toArray vraća elemente liste kao polje.
	 * 
	 * @return elemente liste kao polje
	 */
	
	@Override
	public Object[] toArray() {
		Object[] elementsArray = new Object[size];
		Node currentNode = first;
		for(int i = 0; i < size; i++) {
			elementsArray[i] = currentNode.value;
			currentNode = currentNode.next;
		}
		return elementsArray;
	}
	
	/**
	 * Metoda clear čisti listu od svih članova.
	 */
	
	@Override
	public void clear() {
		first = last = null;
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
	
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException("Element koji se ubacuje u listu ne smije biti null.");
		}
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Index " + position + " nije validan za duljinu " + size() + ".");
		} 
		
		Node nodeToInsert = new Node();
		nodeToInsert.value = value;
		if(position == 0) {
			first.previous = nodeToInsert;
			nodeToInsert.next = first;
			first = nodeToInsert;
			size++;
		} else if(position == size) {
			add(value);
		} else {
			Node currentNode;	
			if(position < size / 2){
				currentNode = first;
				for(int i = 0; i < position; i++) {
					currentNode = currentNode.next;
				}
			}else {
				currentNode = last;
				for(int i = size - 1; i > position; i--) {
					currentNode = currentNode.previous;
				}
			}
			currentNode.previous.next = nodeToInsert;
			nodeToInsert.previous = currentNode.previous;
			nodeToInsert.next = currentNode;
			currentNode.previous = nodeToInsert;
			modificationCount++;
			size++;
		}
	}
	
	/**
	 * Metoda indexOf vraća položaj traženog objekta ili -1 ako se objekt ne nalazi u listi.
	 * 
	 * @param value objekt za kojeg se traži položaj
	 * @return položaj objekta ili -1 ako se objekt ne nalazi u listi
	 */
	
	public int indexOf(Object value) {
		Node currentNode = first;
		for(int position = 0; position < size; position++) {
			if(currentNode.value.equals(value)) {
				return position;
			}
			currentNode = currentNode.next;
		}
		return -1;
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
		} 
		if(index == 0) {
			first = first.next;
		} else if(index == size - 1) {
			last = last.previous;
		} else {
			Node currentNode;	
			if(index < size / 2){
				currentNode = first;
				for(int i = 0; i < index; i++) {
					currentNode = currentNode.next;
				}
			}else {
				currentNode = last;
				for(int i = size - 1; i > index; i--) {
					currentNode = currentNode.previous;
				}
			}
			currentNode.previous.next = currentNode.next;
			currentNode.next.previous = currentNode.previous;
		}
		modificationCount++;
		size--;
	}
	
	/**
	 * Metoda briše prvi primjer određenog objekta iz liste.
	 * 
	 * @return true ako je objekt izbrisan
	 */
	
	@Override
	public boolean remove(Object value) {
		Node currentNode = first;
		for(int i = 0; i < size; i++) {
			if(currentNode.equals(value)) {
				remove(i);
				return true;
			}
			currentNode = currentNode.next;
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
	
	public int size() {
		return this.size;
	}

	/**
	 * Metoda stvara primjerak razreda LinkedListElementsGetter kojem je argument ova lista.
	 */
	
	@Override
	public ElementsGetter createElementsGetter() {

		return new LinkedListElementsGetter(this);
	}
	
	/**
	 * Razred implementira sučelje ElementsGetter te predstavlja iterator po elementima liste.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private static class LinkedListElementsGetter implements ElementsGetter{
		Node currentNode;
		private final long savedModificationCount = 0;
		LinkedListIndexedCollection list;

		/**
		 * Konstruktor stvara primjerak razreda te sprema broj modifikacija napravljenih nad
		 * listom u trenutku stvaranja liste. 
		 * 
		 * @param list lista LinkedListIndexedCollection nad kojom se radi iteracija po elementima
		 */
		
		public LinkedListElementsGetter(LinkedListIndexedCollection list) {
			this.currentNode = list.first;
			this.list = list;
			
		}
		
		/**
		 * Metoda provjerava postoji li sljedeći element u listi za vratiti.
		 * 
		 * @return true ako postoji sljedeći element.
		 */
		
		@Override
		public boolean hasNextElement() {
			if(currentNode == null) {
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
		
		@Override
		public Object getNextElement() {
			
			if(this.savedModificationCount != list.modificationCount) {
				throw new ConcurrentModificationException("Lista je mijenjana za vrijeme iteracije.");
			}else if(hasNextElement()) {
				Node nodeToReturn = currentNode;
				currentNode = currentNode.next;
				return nodeToReturn.value;
			} else {
				throw new NoSuchElementException("Element ne postoji.");
			}
		}	
	}
	public static void main(String[] args) {
		 List col = new ArrayIndexedCollection();
		 col.add("Ivo");
		 col.add("Ana");
		 col.add("Jasna");
		 ElementsGetter getter = col.createElementsGetter();
		 System.out.println("Jedan element: " + getter.getNextElement());
		 System.out.println("Jedan element: " + getter.getNextElement());
		 col.insert(1, 1);
		 System.out.println("Jedan element: " + getter.getNextElement());


		 }
}
