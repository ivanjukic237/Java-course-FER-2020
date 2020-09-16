package hr.fer.zemris.java.gui.calc.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Creates the list model.
 * 
 * @author Ivan Jukić
 *
 * @param <T> object that the model works with
 */

public class PrimListModel<T> implements ListModel<T> {
	/**
	 * Current prime number.
	 */
	int currentPrime = 1;
	/**
	 * List of elements.
	 */
	private List<T> elementi = new ArrayList<>();
	/**
	 * List of listeners.
	 */
	private List<ListDataListener> promatraci = new ArrayList<>();

	/*
	 * {@inheritDoc}
	 */
	
	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}

	/*
	 * {@inheritDoc}
	 */
	
	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}

	/*
	 * {@inheritDoc}
	 */
	
	@Override
	public int getSize() {
		return elementi.size();
	}

	/*
	 * {@inheritDoc}
	 */
	
	@Override
	public T getElementAt(int index) {
		return elementi.get(index);
	}

	/**
	 * Ads an element to the end of the list.
	 * 
	 * @param element element to be added
	 */
	
	public void add(T element) {
		int pos = elementi.size();
		elementi.add(element);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : promatraci) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Gives the next prime number.
	 * 
	 * @return next prime number
	 */
	
	public int next() {
		if (currentPrime <= 1) {
			currentPrime = 2;
			return 2;
		}
			

		int prime = currentPrime;
		boolean found = false;

		while (!found) {
			prime++;
			if (isPrime(prime))
				found = true;
		}
		currentPrime = prime;
		return prime;
	}

	/**
	 * Checks if a number is prime.
	 * 
	 * @param number number to check if it is prime
	 * @return true if the number is prime
	 */
	
	private boolean isPrime(int number) {
		if (number <= 1) {
			return false;
		}
		if (number <= 3) {
			return true;
		}

		if (number % 2 == 0 || number % 3 == 0) {
			return false;
		}

		for (int i = 5; i * i <= number; i = i + 6) {
			if (number % i == 0 || number % (i + 2) == 0) {
				return false;
			}
		}

		return true;
	}
}
