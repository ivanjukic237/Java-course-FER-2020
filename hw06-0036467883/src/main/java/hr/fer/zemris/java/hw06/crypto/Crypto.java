package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.zemris.java.hw06.Util;

/**
 * Class Crypto allow the user to encrypt/decrypt given file using the AES
 * cryptoalgorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest.
 * 
 * @author Ivan Jukić
 *
 */

public class Crypto {

	/**
	 * Method returns the SHA-256 file digest.
	 * 
	 * @param file file to be checked
	 * @return SHA-256 file digest.
	 * @throws NoSuchAlgorithmException if the algorithm for calculating the digest
	 *                                  doesn't exits
	 */

	public static String checkSha(String file) throws NoSuchAlgorithmException {

		Path p = Paths.get(file);
		// byte[] data = Files.readAllBytes(p);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		try (InputStream input = Files.newInputStream(p)) {
			byte[] buff = new byte[4000];
			int len;
			while ((len = input.read(buff)) > 0) {
				digest.update(buff, 0, len);
			}
			input.close();
		} catch (IOException ex) {
			System.out.println("File does not exist.");
		}

		byte[] hash = digest.digest();

		return Util.bytetohex(hash);
	}

	/**
	 * Method encrypts/decrypts the file using the AES cryptoalgorithm.
	 * 
	 * @param encrypt     if the user asks for encryption or decryption in main
	 *                    program
	 * @param keyText     password as hex-encoded text (16 bytes, i.e. 32
	 *                    hex-digits)
	 * @param ivText      initialization vector as hex-encoded text (32 hex-digits)
	 * @param file        file to encrypt/decrypt
	 * @param fileCrypted encrypted/decrypted file
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */

	public static void encrypt(boolean encrypt, String keyText, String ivText, String file, String fileCrypted)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		Path pathFile = Paths.get(file);
		Path pathCrypted = Paths.get(fileCrypted);

		try (InputStream input = Files.newInputStream(pathFile)) {
			OutputStream output = Files.newOutputStream(pathCrypted);
			byte[] buff = new byte[4000];
			int len;
			while ((len = input.read(buff)) > 0) {
				output.write(cipher.update(buff, 0, len));
			}
			output.write(cipher.doFinal());
			output.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Command line program which takes 2 or 3 arguments. First argument chooses an
	 * operation to be made - "checksha" for checking the file digest, "encrypt" for
	 * encrypting and "decrypt" for decrypting. If the user chooses "checksha" as
	 * the first argument, only one argument is expected after - path to the file.
	 * If the user chooses "encrypt"/"decrypt", second argument is file to
	 * encrypt/decrypt and the third arguments it the path for the
	 * encrypted/decrypted file.
	 * 
	 * @param args field of arguments
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */

	public static void main(String[] args)
			throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException {
		Scanner in = new Scanner(System.in);
		if (args[0].equals("checksha")) {
			System.out.printf("Please provide expected sha-256 digest for %s\n> ", args[1]);
			String digest = checkSha(args[1]);
			if (in.next().equals(digest)) {
				System.out.printf("Digesting completed. Digest of %s matches expected digest.\r\n", args[1]);
			} else {
				System.out.printf(
						"Digesting completed. Digest of %s does not match the expected digest. Digest was: %s\n",
						args[1], digest);
			}
		} else {
			boolean encrypt = args[0].equals("encrypt");

			if (!encrypt && !args[0].equals("decrypt")) {
				in.close();
				throw new IllegalArgumentException(args[0] + " je nepoznata akcija.");
			}

			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			String keyText = in.next();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			String ivText = in.next();

			try {
				encrypt(encrypt, keyText, ivText, args[1], args[2]);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			System.out.printf("Encryption completed. Generated file %s based on file %s.", args[2], args[1]);

		}

		in.close();
	}
}
