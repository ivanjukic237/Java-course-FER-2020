package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Razred testira funkcionalnosti razreda ObjectStack.
 * 
 * @author Ivan Jukić
 *
 */

class ObjectStackTest {

	/**
	 * Testira peek metodu za stog. Metoda treba vratiti zadnji element pushan na stog.
	 */
	
	@Test
	void testPush() {
		ObjectStack<Object> stack = new ObjectStack<Object>();
		
		stack.push(2);
		stack.push(12);
		stack.push("Vrijednost");
		
		assertEquals("Vrijednost", stack.peek());
		
	}
	
	/**
	 * Testira da se baca iznimka ako se pokuša koristiti metoda peek na prazan stog.
	 */
	
	@Test
	void testPeekEmptyStack() {
		ObjectStack<Object> stack = new ObjectStack<Object>();

		assertThrows(EmptyStackException.class, () -> stack.peek());
	}

	/**
	 * Testira metodu pop za stog. Metoda treba vratiti zadnji element stoga i onda ga maknuti s stoga.
	 */
	
	@Test
	void testPop() {
		ObjectStack<Object> stack = new ObjectStack<Object>();
		
		stack.push(2);
		stack.push(12);
		stack.push("Vrijednost");
		assertEquals("Vrijednost", stack.pop());
		assertEquals(12, stack.peek());
	}
	
	/**
	 * Testira da se baca iznimka ako se pokuša koristiti metoda pop na prazan stog.
	 */
	
	@Test
	void testPopEmptyStack() {
		ObjectStack<Object> stack = new ObjectStack<Object>();

		assertThrows(EmptyStackException.class, () -> stack.pop());
	}
}
