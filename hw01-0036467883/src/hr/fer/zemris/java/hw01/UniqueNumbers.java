package hr.fer.zemris.java.hw01;

import java.util.Scanner;
/**
 * Razred UniqueNumbers omogućava korisniku da ispiše unesene cijele brojeve poredane u sortirani uzlazni i silazni niz.
 * Koristi se binarno stablo za sortiranje.
 * 
 * @author Ivan Jukić
 */
public class UniqueNumbers {
	/**
	 * Razred TreeNode prikazuje čvor binarnog stabla koji sadrži svoju vrijednost kao cijeli broj
	 * te njemu lijevi i desni čvor.
	 */
	public static class TreeNode{
		TreeNode left;
		TreeNode right;
		int value;
	}
	/**
	 * Metoda dodaje čvorove s određenim vrijednostima u binarno stablo. Ako je stablo prazan skup 
	 * dodaje se korijen, a ako nije dodaje se čvor na prvo slobodno mjesto na način da s lijeve 
	 * strane idu čvorovi s manjim vrijednostima, a s desne strane čvorovi s većim vijednostima.
	 * Ako vrijednost već postoji u nekom čvoru, onda se taj čvor ne dodaje u stablo.
	 * 
	 * @param node korijen binarnog stabla
	 * @param value vrijednost za čvor koji se dodaje
	 * @return korijen binarnog stabla
	 */
	public static TreeNode addNode(TreeNode node, int value) {
		if(node == null) {
			node = new TreeNode();
			node.value = value;
			node.left = null;
			node.right = null;
		}
		else {
			if(node.value > value) {
				node.left = addNode(node.left, value);
			}
			if(node.value < value){
				node.right = addNode(node.right, value);
			}
		}
		return node;
	}
	/**
	 * Metoda vraća broj čvorova binarnog stabla.
	 * 
	 * @param node korijen binarnog stabla
	 * @return broj čvorova binarnog stabla
	 */
	public static int treeSize(TreeNode node) {
		if(node == null) {
			return 0;
		}
		else {
			return 1 + treeSize(node.left) + treeSize(node.right);
		}
	}
	/**
	 * Metoda provjerava postoji li u binarnom stablu čvor s unesenom vrijednosti.
	 * 
	 * @param node korijen binarnog stabla
	 * @param value vrijednost za koju se provjerava je li u stablu
	 * @return true ako je vrijednost u stablu, a false ako nije
	 */
	public static boolean containsValue(TreeNode node, int value) {
		if(node == null) {
			return false;
		}
		if(node.value == value) {
			return true;
		}
		else {
			return false || containsValue(node.left, value) || containsValue(node.right, value);
		}
	}
	/**
	 * Metoda ispisuje vrijednosti čvorova binarnog stabla kao uzlazni niz.
	 * 
	 * @param node korijen binarnog stabla
	 */
	public static void printAscending(TreeNode node) {
		if(node != null) {
			printAscending(node.left);
			System.out.print(node.value + " ");
			printAscending(node.right);
		}
	}
	/**
	 * Metoda ispisuje vrijednosti čvorova binarnog stabla kao silazni niz.
	 * 
	 * @param node korijen binarnog stabla
	 */
	public static void printDescending(TreeNode node) {
		if(node != null) {
			printDescending(node.right);
			System.out.print(node.value + " ");
			printDescending(node.left);
		}
	}
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		TreeNode head = null;
		
		while(true) {
			
			System.out.print("Unesite broj > ");
			String next = input.nextLine();
			
			if(next.equals("kraj")) {
				System.out.print("Ispis od najmanjeg: ");
				printAscending(head);
				System.out.println();
				
				System.out.print("Ispis od najvećeg: ");
				printDescending(head);
				System.out.println();
				
				break;
			}
			try {
				int number = Integer.parseInt(next.strip());
				
				if(containsValue(head, number)) {
					System.out.println("Broj već postoji. Preskačem.");
				}
				else {
					head = addNode(head, number);
					System.out.println("Dodano.");
				}
			}
			catch(IllegalArgumentException ex) {
				System.out.format("%s nije cijeli broj.%n", next);
			}
		}
		input.close();
	}
}
