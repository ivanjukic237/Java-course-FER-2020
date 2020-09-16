package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja kolekciju koja sadrži uređene parove vrijednosti <K> key i <V> value. Sastoji se od polja koje prima jednostruko vezane
 * liste čiji su čvorovi uređeni parovi. U koju listu će se koji uređeni par staviti ovisi o hash vrijednosti ključa (key) uređenog para.
 * Razred implementira sučelje Iterable što omogućava korištenje for-each petlje po polju koje sadrži elemente kolekcije.
 * 
 * @author Ivan Jukić
 *
 * @param <K> tip parametra koji se koristi za ključ uređenog para
 * @param <V> tip parametra koji se koristi za vrijednost uređenog para
 */

public class SimpleHashtable <K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/**
	 * Polje predstavlja polje jednostruko vezanih lista čiji su čvorovi uređeni parovi dvaju vrijednosti tipa <K> i <V>.
	 */
	
	public TableEntry<K, V>[] slots;
	
	private int size = 0;
	
	private final double MAX_PERCENTAGE_FILLED = 0.75;
	
	private int modificationCount = 0;
	
	private int numberOfFilled = 0;
	
	/**
	 * Konstuktor inicijalizira polje jednostuko vezanih lista TableEntry<K, V> s unesenim kapacitetom. Ako kapacitet
	 * nije potencija broja 2, onda pronalazi najbližu potenciju veću od tog broja i postavi kapacitet s tim brojem.
	 * 
	 * @throws IllegalArgumentException ako je uneseni kapacitet manji od 1
	 * @param capacity željeni kapacitet polja
	 */
	
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Kapacitet mora biti veći od nula.");
		}
		while(!isPowerOfTwo(capacity)) {
			capacity++;
		}
		slots =  new TableEntry[capacity];

	}
	
	/**
	 * Konstruktor inicijalizira polje s kapacitetom 16.
	 */
	
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * Metoda vraća broj članova ove kolekcije.
	 * 
	 * @return size broj članova kolekcije
	 */
	
	public int size() {
		return size;
	}
	
	/**
	 * Metoda ispituje je li broj potencija broja 2. Koristi se bitnovna operacija AND. Ova implementacija pretpostavlja
	 * da će broj x biti veći od 1 pa ne uzima u obzir to da metoda vraća {@code true} za nulu. 
	 * 
	 * @param x broj za koji se provjerava je li potencija broja 2
	 * @return {@code true} ako je broj potencija broja 2
	 */
	
	private boolean isPowerOfTwo(int x) {
		return (x & (x - 1)) == 0;
	}
	
	/**
	 * Razred predstavlja model jednostruko povezane liste čiji su čvorovi uređeni parovi vrijednosti <K> i <V>. Svaki čvor liste osim
	 * uređenog para sadrži i referencu na sljedeći čvor u listi.
	 * 
	 * @author Ivan Jukić
	 *
	 * @param <K> prvi član uređenog para koji predstavlja ključ
	 * @param <V> drugi član uređenog para koji predstavlja vrijednost
	 */
	
	public static class TableEntry <K, V> {
		private K key;
		private V value;
		TableEntry<K, V> next = null;
		
		public TableEntry(K key, V value){
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Metoda računa mjesto u polju gdje će se uređeni par umetnuti. Računa se na način da se uzme apsolutna vrijednost hash vrijednosti
	 * ključa i onda se od njega uzme ostatak pri dijeljenju s kapacitetom polja.
	 * 
	 * @param key ključ od kojeg se traži mjesto u polju gdje će se umetnuti
	 * @return mjesto u polju gdje će se ključ umetnuti
	 */
		
	private int getSlot(Object key) {
		return Math.abs(key.hashCode()) % slots.length;
	}
	
	/**
	 * Stavlja uređeni par u kolekciju. Svaki par se stavlja u određenu vezanu listu TableEntry koja je na jednoj od pozicija polja slots. 
	 * U koju listu TableEntry će par ući ovisi o hash vrijednosti ključa. Ako je vezana lista na poziciji polja null, u nju se kao glava liste 
	 * stavlja par. Ako je polje na toj poziciji popunjeno, onda metoda putuje po vezanoj listi tražeći isti ključ kao par te ako ga nađe zamijeni 
	 * mu vrijednost.  Ako u vezanoj listi ne postoji ista vrijednost ključa, to znači da u kolekciji ne postoji taj ključ, pa se par dodaje na kraj 
	 * vezane liste. Metoda provjerava je li popunjenost tablice veća ili jednaka od {@code MAX_PERCENTAGE_FILLED} te ako je, povećava kapacitet polja
	 * slots koje sadrži elemente kolekcije.
	 * 
	 * @throws NullPointerException ako je vrijednost ključa null referenca
	 * @param key vrijednost ključa uređenog para
	 * @param value vrijednost uređenog para
	 */
	
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Ključ ne smije biti null.");
		}
		int slot = getSlot(key);
		// ako u tom slotu još nema objekata onda ga stavi kao glavu tog slota
		if(slots[slot] == null) {
			slots[slot] = new TableEntry<K, V>(key, value);
			modificationCount++;
			size++;
			numberOfFilled++;
			
			// ako je popunjenje veće ili jednako od MAX_PERCENTAGE_FILLED povećavamo kapacitet
			if(numberOfFilled / slots.length >= MAX_PERCENTAGE_FILLED) {
				increaseCapacity();
			}
			
		} else {
			TableEntry<K, V> head = slots[slot];
			
			while(head != null) {
				if(head.key.equals(key)) {
					head.setValue(value);
					break;
				} else if(head.next == null) {
					head.next = new TableEntry<K, V>(key, value);
					modificationCount++;
					size++;
					break;
				}
				
				head = head.next;
				
			}
		}
	}
	
	/**
	 * Povećava kapacitet polja slots dva puta. 
	 */
	
	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		// privremeno polje gdje ćemo spremati elemente kolekcije
		TableEntry<K, V>[] temporaryField = new TableEntry[size];
		int counter = 0;
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				TableEntry<K, V> head = slots[i];
				
				while(head != null) {
					temporaryField[counter] = head;
					counter++;
					head = head.next;
				}
			}
		}
		// brišemo reference na glave polja slots
		clear();
		
		slots = new TableEntry[slots.length * 2];
		
		for(int i = 0; i < temporaryField.length; i++) {
			// ne želimo da liste iz različitih slotova budu povezane
			temporaryField[i].next = null;
			this.put(temporaryField[i].getKey(), temporaryField[i].getValue());
			modificationCount--;
		}
	}
	
	/**
	 * Vraća vrijednost uređenog para za određeni ključ. Ako je input null ili ako nije nađen objekt metoda vraća null.
	 * 
	 * @param key ključ koji se traži
	 * @return <V> vrijednost uređenog para
	 */
	
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		int slot = getSlot(key);
		TableEntry<K, V> head = slots[slot];
		
			while(head != null) {
				if(head.key.equals(key)) {
					return head.value;
				}
				head = head.next;
			}
		return null;
	}
	
	/**
	 * Ispituje je li vrijednost ključa u kolekciji.
	 * 
	 * @param key vrijednost ključa uređenog para
	 * @return {@code true} ako je vrijednost ključa nađena
	 */
	
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		
		int slot = getSlot(key);
		if(slots[slot] == null) {
			return false;
		} else {
			TableEntry<K, V> head = slots[slot];
			
			while(head != null) {
				if(head.key.equals(key)) {
					return true;
				}
				head = head.next;
			}
		}
		return false;
	}

	/**
	 * Ispituje je li vrijednost uređenog para u kolekciji.
	 * 
	 * @param value vrijednost uređenog para
	 * @return {@code true} ako je vrijednost nađena
	 */
	
	public boolean containsValue(Object value) {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				TableEntry<K, V> head = slots[i];
				
				while(head != null) {
					//ako je head.value == null onda moramo ovaj uvjet jer ne možemo koristiti metodu equals nad null referencom
					if(head.value == null) {
						if(value == null) {
							return true;
						}
					} else if(head.value.equals(value)) {
						return true;
					}
					head = head.next;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metoda briše uređeni par određenog ključa iz kolekcije.
	 * 
	 * @param key ključ uređenog para koji se želi izbrisati iz kolekcija
	 */
	
	public void remove(Object key) {
		
		if(key != null) {
			int slot = getSlot(key);
			if(slots[slot] != null) {
				TableEntry<K, V> head = slots[slot];
				
				// ako je samo jedan element u slotu
				if(head.next == null) {
					if(head.getKey().equals(key)) {
						slots[slot] = head = null;
						modificationCount++;
						size--;
						numberOfFilled--;
					}
				} else {
					// ako imamo više elemenata u slotu, a prvi ima isti key
					if(head.getKey().equals(key)) {
						slots[slot] = head.next;
						head = null;
						modificationCount++;
						size--;
					} else {
						TableEntry<K, V> previous = head;
						head = head.next;
						
						while(head != null) {
							if(head.key.equals(key)) {
								previous.next = head.next;
								head = null;
								modificationCount++;
								size--;
								break;
							}
							previous = head;
							head = head.next;
						}
					
					}
				}
			} 
		}
	}

	/**
	 * Ispituje je li kolekcija prazna.
	 * 
	 * @return {@code true} ako je kolekcija prazna
	 */
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Vraća String reprezentaciju kolekcije.
	 * 
	 * @return String reprezentacija kolekcija
	 */
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		int counter = 0;
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				TableEntry<K, V> head = slots[i];
				
				while(head != null) {
					if(counter == size - 1) {
						stringBuilder.append(head.key + "=" + head.value);
					} else {
						stringBuilder.append(head.key + "=" + head.value + ", ");
					}
					counter++;
					head = head.next;
				}
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
	
	/**
	 * Metoda briše sve uređene parove iz kolekcije.
	 */
	
	@SuppressWarnings("unchecked")
	public void clear() {
		slots = new TableEntry[slots.length];
		size = 0;
		numberOfFilled = 0;
	}
	
	/**
	 * Vraća kapacitet polja slots.
	 */
	
	public int getCapacity() {
		return slots.length;
	}
	
	/**
	 * Razred predstavlja iterator za kolekciju TableEntry<K,V>. Omogućuje iteriranje pomoću for-each petlje. 
	 * Iterator omogućuje brisanje elemenata za vrijeme iteracije.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		int countOfIteratedElements = 0;
		TableEntry<K, V> currentElement = null;
		int currentSlot = 0;
		int modificationCountSaved = modificationCount;
		boolean firstModificationOnElement = true;
		
		/**
		 * Metoda provjerava ima li iteracija još elemenata.
		 * 
		 * @throws ConcurrentModificationException ako su se događale izmjene u kolekciji prije poziva metode
		 * @return {@code true} ako je iteracija ima još elemenata
		 */
		
		public boolean hasNext() {
			if(modificationCountSaved != modificationCount) {
				throw new ConcurrentModificationException("Događale su se izmjene u kolekciji za vrijeme iteriranja.");
			}
			return countOfIteratedElements < size;
		}
		
		/**
		 * Vraća sljedeći element u iteraciji. Koristi pomoćnu metodu getNextHead() koja baca NoSuchElementException
		 * ako više nema elemenata za iteraciju te se time završava iteracija. Ako su se događale izmjene u kolekciji
		 * tijekom iteracije baca se ConcurrentModificationException.
		 * 
		 * @throws ConcurrentModificationException ako su se događale izmjene u kolekciji prije poziva metode
		 * @throws NoSuchElementException ako nema više elemenata za iteraciju
		 * @return TableEntry<K, V> sljedeći element u iteraciji
		 */
		
		public SimpleHashtable.TableEntry<K, V> next() {
			if(modificationCountSaved != modificationCount) {
				throw new ConcurrentModificationException("Događale su se izmjene u kolekciji za vrijeme iteriranja.");
			}
			firstModificationOnElement = true;
			// ako je prvi element kolekcije 
			if(currentElement == null) {
				currentElement = getNextHead();
				countOfIteratedElements++;
				return currentElement;
			}
			// ako nije kraj vezane liste
			if(currentElement.next != null) {
				currentElement = currentElement.next;
				countOfIteratedElements++;
				return currentElement;
			} else {
				currentElement = getNextHead();
				countOfIteratedElements++;
					return currentElement;
			}
		}
		
		/**
		 * Metoda vraća sljedeću glavu vezane liste za iteraciju.
		 * 
		 * @throws NoSuchElementException ako u kolekciji nema više elemenata za iteriranje
		 * @return sljedeću glavu vezane liste za iteraciju
		 */
		
		private TableEntry<K, V> getNextHead() {
			// i kreće od idućeg slota
			for(int i = currentSlot + 1; i < slots.length; i++) {
				if(slots[i] != null) {
					currentSlot = i;
					return slots[i];
				}
			}
			throw new NoSuchElementException("U kolekciji nema više elemenata.");
		}
		
		/**
		 * Metoda briše trenutni element u iteraciji iz kolekcije.
		 * 
		 * @throws ConcurrentModificationException ako su se događale izmjene u kolekciji za vrijeme iteriranja ili ako
		 * je metoda pozvana više puta po istom elementu
		 */
		
		@Override
		public void remove() { 
			if(modificationCountSaved != modificationCount) {
				throw new ConcurrentModificationException("Događale su se izmjene u kolekciji za vrijeme iteriranja.");
			}
			if(firstModificationOnElement) {
				modificationCountSaved++;
				firstModificationOnElement = false;
			} else {
				throw new IllegalStateException("Remove se već iskoristio za ovaj element.");
			}
			// referenca na vanjski razred
			SimpleHashtable.this.remove(currentElement.getKey());
			// zbog brisanja elementa moramo smanjiti i counter iteriranih elemenata
			countOfIteratedElements--;
		}
	}
	
	/**
	 * Predstavlja iterator za razred. Vraća novi IteratorImpl.
	 * 
	 * @return iterator za razred
	 */

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
