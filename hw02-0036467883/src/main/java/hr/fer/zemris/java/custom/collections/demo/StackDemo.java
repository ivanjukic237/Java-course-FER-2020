package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Razred StackDemo računa sufiksalni izraz dan preko jednog naredbenog retka koji se sastoji od brojeva i
 * aritmetičkih operacija nad brojevima.
 * 
 * @author Ivan Jukić
 *
 */

public class StackDemo {
	
/**
 * Metoda odabire vrstu aritmetičke operacije te je provodi nad dva cijela broja.
 * Vrste mogućih operacija su zbrajanje ("+"), oduzimanje ("-"), množenje ("*"), 
 * dijeljenje ("/") i ostatak pri dijeljenju ("%").
 * 
 * @param first cijeli broj nad kojim se vrši operacija
 * @param second cijeli broj kojim se vrši operacija
 * @param operator definira vrstu operacije
 * @return cijeli broj koji je rezultat aritmetičke operacije
 */
	
	private static int operation(int first, int second, String operator) {

		if(operator.equals("+")) {
			return first + second;
		} else if(operator.equals("-")) {
			return first - second;
		} else if(operator.equals("*")) {
			return first * second;
		} else if(operator.equals("/")) {
			if(second == 0) {
				throw new IllegalArgumentException("Ne možemo dijeliti s nulom.");
			} else {
				return first / second;
			}
		} else if(operator.equals("%")) {
			return first % second;
		}
		throw new IllegalArgumentException("Operator nije valjan. Treba koristiti samo +, -, /, *, % te ih odvojiti razmakom.");
	}

	/**
	 * Metoda kao ulaz prima jedan argument s naredbenog retka oblika String koji
	 * predstavlja sufiksalni izraz koji se sastoji od aritmetičkih operacija nad 
	 * cijelim brojevima, a zatim ispisuje rješenje izraza.
	 * Aritmetička operacija se provodi nad prva dva broja koja prethode operaciji.
	 * Svaki broj i aritmetička operacija moraju biti odvojeni razmakom.
	 * 
	 * @throws IllegalArgumentException ako je ulaz više od jednog argumenta naredbenog retka
	 * @throws NumberFormatException ako je članovi izraza nisu odvojeni razmakom ili ako se 
	 * operacije ne mogu provesti
	 * @param args jedan argument s naredbenog retka 
	 * 
	 */
	
	public static void main(String[] args) {
		if(args.length != 1) {
			throw new IllegalArgumentException("Izraz mora imati samo jedan član.");
		} 
		ObjectStack stack = new ObjectStack();
		
		// dijelimo String po bilo kojem razmaku
		String[] expression = args[0].split("\\s+");
		for(String element : expression) {
			try {
				stack.push(Integer.parseInt(element));
			} catch(NumberFormatException ex) {

				try {
					//prvo uzimamo drugi član operacije, pa onda prvi član (inače će operacija ići unatrag)
					int second = (Integer)stack.pop();
					int first = (Integer)stack.pop();

					stack.push(operation(first, second, element));

				} catch(EmptyStackException ex1) {
					throw new NumberFormatException("Izraz nije u dobrom formatu.");
				}
			}
		}

		if(stack.size() != 1) {
			throw new NumberFormatException("Izraz nije u dobrom formatu.");

		} else {
			System.out.println("Expression evaluates to " + stack.pop());
		}
	}
}