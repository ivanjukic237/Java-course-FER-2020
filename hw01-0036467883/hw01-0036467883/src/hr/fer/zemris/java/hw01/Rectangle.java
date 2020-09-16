package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred Rectangle omogućava korisniku da izračuna i ispiše površinu i opseg pravokutnika.
 * 
 * @author Ivan Jukić
 **/

public class Rectangle {
/**
 * Metoda računa površinu pravokutnika pomoću formule A = širina * visina.
 * 
 * @param width širina pravokutnika
 * @param height visina pravokutnika
 * @return površina pravokutnika
 */
	public static double area(double width, double height) {
		return width * height;
	}
	/**
	 * Metoda računa opseg pravokutnika pomoću formule P = 2 * visina + 2 * širina.
	 * 
	 * @param width širina pravokutnika
	 * @param height visina pravokutnika
	 * @return opseg pravokutnika
	 */
	public static double perimiter(double width, double height) {
		return 2 * width + 2 * height;
	}
	/**
	 * Metoda provjerava je li broj pozitivan i ispisuje odgovarajuću poruku ako je negativan.
	 * 
	 * @param number broj za koji se provjerava je li pozitivan
	 * @return true ako je broj pozitivan, a false ako je broj negativan
	 */
	public static boolean isArgumentPositive(double number) {
		if(number >= 0) {
			return true;
		}
		else {
			System.out.println("Unijeli ste negativnu vrijednost.");
			return false;
		}
	}
	/**
	 * Metoda provjerava može li se string protumačiti kao broj i ispisuje odgovarajuću poruku ako 
	 * se ne može.
	 * 
	 * @param input string za kojeg se provjerava je li broj
	 * @return true ako se string može protumačiti kao broj, a false ako se ne može
	 */
	public static boolean isDouble(String input) {
		
		try {
			Double.parseDouble(input);
		}
		catch(IllegalArgumentException ex) {
			System.out.format("'%s' se ne može protumačiti kao broj.%n", input);
			return false;
		}
		
		return true;
	}
	/**
	 * Metoda ispisuje površinu i opseg pravokutnika.
	 * 
	 * @param width širina pravokutnika
	 * @param height visina pravokutnika
	 * @param area površina pravokutnika
	 * @param perimiter opseg pravokutnika
	 */
	public static void printSolution(
		double width, double height, double area, double perimiter
	){
		System.out.format(
			"Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.%n",
			width, height, area, perimiter
		);
	}
	
	public static void main(String[] args) {

		if(args.length != 0 && args.length != 2) {
			System.out.println("Niste unijeli dva argumenta.");
			System.exit(-1);
		}
		
		if(args.length == 2) {
			if(isDouble(args[0]) && isDouble(args[1])){
				
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);
				
				if(isArgumentPositive(width) && isArgumentPositive(height)) {
					printSolution(
						width, height, area(width, height), perimiter(width, height)
					);
				}
			}
		}
		
		if(args.length == 0) {
			Scanner input = new Scanner(System.in);
			
			double width;
			while(true) {
				System.out.print("Unesite širinu > ");
				String inWidth = input.nextLine().strip();
				
				if(isDouble(inWidth)) {
					 width = Double.parseDouble(inWidth);
						if(isArgumentPositive(width)) {
							break;
						}
				}
				
			}
			
			double height;
			while(true) {
				System.out.print("Unesite visinu > ");
				String inHeight = input.nextLine().strip();
				
				if(isDouble(inHeight)) {
					height = Double.parseDouble(inHeight);
						if(isArgumentPositive(height)) {
							break;
						}
				}
			}
			
			input.close();
			
			printSolution(
				width, height, area(width, height), perimiter(width, height)
			);
		}
	}
}
		


