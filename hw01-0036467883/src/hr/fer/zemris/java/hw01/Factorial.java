package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 *  Razred Factorial omogućava korisniku da izračuna faktorijel prirodnog broja.
 *  
 *  @author Ivan Jukić
 **/

public class Factorial {
/**
 * Metoda računa faktorijel predanog broja pomoću formule n! = n*(n-1)*(n-2)*...*2*1.
 * 
 * @param n broj kojem se računa faktorijel
 * @return faktorijel od broja n
 * @throws IllegalArgumentException ako se kao argument preda vrijednost 
 * koja nije u intervalu [0, Long.MAX_VALUE]
 */
	public static long calculateFactorial(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		
		long factorial = 1;
		while(n > 1) {
			
			if(factorial > Long.MAX_VALUE / n) {
				throw new IllegalArgumentException();
			}
			else {
			factorial *= n;
			n--;
			}
		}
		return factorial;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		while(true) {
			System.out.print("Unesite broj > ");
			if(input.hasNextInt()) {
				int k = input.nextInt();
				
				if(k < 3 || k > 20) {
					System.out.format(
					 "'%s' nije broj u dozvoljenom rasponu.%n", k
					);
				}
				else {
					System.out.format(
					 "%s! = %s%n", k, calculateFactorial(k)
					);
				}
				
			}
			else {
				String k = input.next();
				
				if(k.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				else {
					System.out.format("'%s' nije cijeli broj.%n", k);
				}
				
			}
		}
		input.close();
	}
}
