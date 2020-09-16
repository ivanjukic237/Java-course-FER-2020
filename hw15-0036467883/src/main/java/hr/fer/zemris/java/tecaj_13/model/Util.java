package hr.fer.zemris.java.tecaj_13.model;

import java.util.LinkedHashMap;

/**
 * Class which contains method to take hex-encoded String and returns appropriate
 * byte array and vice versa.
 * 
 * @author Ivan Jukić
 *
 */

public class Util {

	/**
	 * Array of all hex digits. Used to make a map of (decimal, hex) pairs.
	 */

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * Sorted map which contains pairs (key, value) = (decimal value, hex value).
	 */

	private static final LinkedHashMap<Character, Integer> mapOfHexValues = createMapOfHexValues();

	/**
	 * Creates a sorted map contains pairs (key, value) = (decimal value, hex
	 * value).
	 * 
	 * @return a sorted map with decimal, hex pairs.
	 */

	private static LinkedHashMap<Character, Integer> createMapOfHexValues() {
		LinkedHashMap<Character, Integer> mapOfHexValues = new LinkedHashMap<>();

		for (int i = 0; i < hexDigits.length; i++) {
			mapOfHexValues.put(hexDigits[i], i);
		}
		return mapOfHexValues;

	}

	/**
	 * Method takes hex-encoded String and returns an appropriate byte array.
	 * 
	 * @param keyText hex-encoded String
	 * @return byte[] array of the given String
	 * @throws IllegalArgumentException if the given String length is not an even
	 *                                  number
	 */

	public static byte[] hextobyte(String keyText) {

		if (keyText.equals("")) {
			return new byte[0];
		}
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}

		String[] hexArray = keyText.split("(?<=\\G.{2})");
		byte[] decimalArray = new byte[hexArray.length];
		for (int i = 0; i < hexArray.length; i++) {
			decimalArray[i] = (byte) hexToDecimal(hexArray[i]);
		}

		return decimalArray;
	}

	/**
	 * Helper method that converts a given hexadecimal String to an integer.
	 * 
	 * @param hex hexadecimal String
	 * @return integer representation of the hexadecimal String
	 */

	private static int hexToDecimal(String hex) {
		hex = hex.toLowerCase();

		int number = 0;
		int counter = 0;

		for (int i = hex.length() - 1; i >= 0; i--) {

			number += mapOfHexValues.get(hex.charAt(counter)) * (int) Math.pow(16, i);
			counter++;
		}
		return number;
	}

	/**
	 * Method takes a byte and returns appropriate hex-encoded String.
	 * 
	 * @param decimal one byte
	 * @return hex-encoded String
	 */
	
	public static String decimalToHex(byte decimal) {
		// unsigned
		int decimalInteger = decimal & 0xFF;
		int remainder = decimalInteger % 16;
		String hex = "" + hexDigits[remainder];

		decimalInteger /= 16;

		while (decimalInteger != 0) {
			remainder = decimalInteger % 16;
			decimalInteger /= 16;
			hex = hexDigits[remainder] + hex;
		}

		if (hex.length() % 2 == 0) {
			return hex;
		} else {
			return "0" + hex;
		}
	}

	/**
	 * * Method takes a byte array and returns appropriate hex-encoded String.
	 * 
	 * @param bytearray byte array which is converted to hex-encoded String
	 * @return hex-encoded String of the byte array
	 */
	
	public static String bytetohex(byte[] bytearray) {
		StringBuilder hex = new StringBuilder();
		for (int i = 0; i < bytearray.length; i++) {
			hex.append(decimalToHex(bytearray[i]));
		}
		return hex.toString();
	}

	public static void main(String[] args) {
		hextobyte("abcdef");

		byte[] a = hextobyte("01aE22");

		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}

		System.out.println(bytetohex(a));

	}
}
