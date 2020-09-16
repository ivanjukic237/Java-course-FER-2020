package hr.fer.zemris.java.custom.collections.hw02;

/**
 * Razred ComplexNumber predstavlja model skupa kompleksnih brojeva i operacija
 * računanja s kompleksnim brojevima. Preciznost u računanju je double što se
 * treba uzeti u obzir kod korištenja operacija.
 * 
 * @author Ivan Jukić
 *
 */

public class ComplexNumber {
	private double real;
	private double imaginary;

	/**
	 * Konstruktor postavlja realni i kompleksni dio kompleksnog broja.
	 * 
	 * @param real      postavlja realni dio kompleksnog broja
	 * @param imaginary postavlja imaginarni dio kompleksnog broja
	 */

	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Metoda stvara polje Stringova ulaznog kompleksnog broja gdje su članovi polja
	 * realni i kompleksni dio kompleksnog broja.
	 * 
	 * @throws NumberFormatException ako ulaz nije u formatu a+bi
	 * @param complexNumber
	 * @return polje gdje su članovi polja realni i kompleksni dio kompleksnog broja
	 */

	private static String[] toComplexNumberArray(String complexNumber) {

		String[] complexNumberStringArray = complexNumber.split("(?=[+-])");
		// Baca iznimku ako ima više od 2 elementa, ako su oba člana kompleksni i ako
		// nijedan član (od 2) nije kompleksni broj
		if (complexNumberStringArray.length > 2
				|| complexNumberStringArray.length != 1 && (!complexNumberStringArray[1].contains("i")
						|| (complexNumberStringArray[0].contains("i") && complexNumberStringArray[1].contains("i")))

		) {
			throw new NumberFormatException("Ulaz mora biti u formatu a+bi.");
		}
		return complexNumberStringArray;
	}

	/**
	 * Metoda interpretira kompleksni broj koji je unesen kao String. Metoda
	 * provjerava je li upisani broj u ispravnom formatu.
	 * 
	 * @throws NullPointerException  ako je ulaz prazan string ili null
	 * @throws NumberFormatException ako ulaz nije legitiman kompleksni broj
	 * @param input izraz koji se interpretira kao kompleksni broj
	 * @return kompleksni broj
	 */

	public static ComplexNumber parse(String input) {
		double real = 0;
		String realString = "0";
		double imaginary = 0;
		String imaginaryString = "0";

		if (input == null || input.equals("")) {
			throw new NullPointerException("Ulaz ne smije biti prazan ili null.");
		}
		input = input.replaceAll("\\s+", "");

		String[] complexNumberArray = toComplexNumberArray(input);

		int length = complexNumberArray.length;
		for (int i = 0; i < length; i++) {
			if (complexNumberArray[i].equals("i") || complexNumberArray[i].equals("+i")) {
				imaginaryString = "1";
			} else if (complexNumberArray[i].equals("-i")) {
				imaginaryString = "-1";
			} else if (complexNumberArray[i].contains("i")) {
				imaginaryString = complexNumberArray[i].substring(0, complexNumberArray[i].length() - 1);
			} else {
				realString = complexNumberArray[i];
			}
		}
		try {
			real = Double.parseDouble(realString);
			imaginary = Double.parseDouble(imaginaryString);
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(
					"Vrijednosti realnog i imaginarnog dijela kompleksnog broja moraju biti brojevi i format mora biti a+bi.");
		}
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Metoda stvara kompleksni broj kojem je zadan realni dio, a imaginarni dio je
	 * nula.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @return kompleksni broj
	 */

	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Metoda stvara kompleksni broj kojem je zadan imaginarni dio, a realni dio je
	 * nula.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @return kompleksni broj
	 */

	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/***
	 * Metoda stvara kompleksni broj pomoću kuta i modula kompleksnog broja
	 * koristeći formulu rcos(θ)+rsin(θ)⋅i.
	 * 
	 * @param magnitude modul kompleksnog broja
	 * @param angle     kut kompleksnog broja u radijanima
	 * @return kompleksni broj
	 */

	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Metoda vraća realni dio kompleksnog broja.
	 * 
	 * @return realni dio kompleksnog broja.
	 */

	public double getReal() {
		return real;
	}

	/**
	 * Metoda vraća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio kompleksnog broja
	 */

	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Metoda vraća modul kompleksnog broja z = a + bi pomoću formule sqrt(Re(z)^2 +
	 * Im(z)^2).
	 * 
	 * @return modul kompleksnog broja
	 */

	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Metoda vraća kut kompleksnog broja pomoću formule θ = tan^-1(Im(z) / Re(z))
	 * 
	 * @return
	 */

	public double getAngle() {
		return Math.atan(imaginary / real);
	}

	/**
	 * Metoda definira zbrajanje dvaju kompleksnih brojeva.
	 * 
	 * @param addend broj koji se zbraja
	 * @return zbroj dvaju kompleksnih brojeva
	 */

	public ComplexNumber add(ComplexNumber addend) {
		return new ComplexNumber(real + addend.real, imaginary + addend.imaginary);
	}

	/**
	 * Metoda definira razliku dvaju kompleksnih brojeva.
	 * 
	 * @param subtrahend broj kojim se oduzima
	 * @return razliku
	 */

	public ComplexNumber sub(ComplexNumber subtrahend) {
		return new ComplexNumber(real - subtrahend.real, imaginary - subtrahend.imaginary);
	}

	/**
	 * Metoda definira umnožak dvaju kompleksnih brojeva.
	 * 
	 * @param multiplier broj kojim se množi
	 * @return umnožak
	 */

	public ComplexNumber mul(ComplexNumber multiplier) {
		return new ComplexNumber(real * multiplier.real - imaginary * multiplier.imaginary,
				real * multiplier.imaginary + imaginary * multiplier.real);
	}

	/**
	 * Metoda definira dijeljenje dvaju kompleksnih brojeva
	 * 
	 * @throws IllegalArgumentException ako se pokuša dijeliti s nulom
	 * @param divisor broj kojim se dijeli
	 * @return količnik
	 */

	public ComplexNumber div(ComplexNumber divisor) {
		if (divisor.real == 0 && divisor.imaginary == 0) {
			throw new IllegalArgumentException("Ne možemo dijeliti s nulom.");
		} else {
			ComplexNumber conjugate = new ComplexNumber(divisor.real, -1 * divisor.imaginary);
			ComplexNumber divisorMultipliedByConjugate = divisor.mul(conjugate);
			ComplexNumber dividendMultipliedByConjugate = mul(conjugate);

			return new ComplexNumber(dividendMultipliedByConjugate.real / divisorMultipliedByConjugate.real,
					dividendMultipliedByConjugate.imaginary / divisorMultipliedByConjugate.real);
		}
	}

	/**
	 * Metoda definira potenciranje kompleksnog broja.
	 * 
	 * @throws IllegalArgumentException ako se pokuša dati potencija koja je manja
	 *                                  od nula
	 * @param n vrijednost potencije
	 * @return potenciju kompleksnog broja
	 */

	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija mora biti veća ili jednaka nuli.");
		} else {
			double magnitudeSquared = Math.pow(getMagnitude(), 2);
			double nTimesAngle = n * getAngle();

			return new ComplexNumber(magnitudeSquared * Math.cos(nTimesAngle),
					magnitudeSquared * Math.sin(nTimesAngle));
		}
	}

	/**
	 * Metoda definira korjenovanje kompleksnog broja z pomoću formule
	 * z=ρ^1/n((cos(ϕ+2πkn)+isin(ϕ+2kπn)) gdje je k∈{0,1,...,n−1}.
	 * 
	 * @param n vrijednost korijena
	 * @return polje rješenja korjenovanja
	 */

	public ComplexNumber[] root(int n) {
		ComplexNumber[] listOfRoots = new ComplexNumber[n];

		if (n <= 0) {
			throw new IllegalArgumentException("Korijen mora biti veći od nule.");
		} else {
			double rootMagnitude = Math.pow(getMagnitude(), 1 / n);
			double angle = getAngle();
			for (int i = 0; i < n; i++) {
				listOfRoots[i] = new ComplexNumber(rootMagnitude * Math.cos((angle + 2 * Math.PI * i) / n),
						rootMagnitude * Math.sin((angle + 2 * Math.PI * i) / n));
			}
		}
		return listOfRoots;
	}

	/**
	 * Metoda vraća reprezentaciju kompleksnog broja u obliku a+bi gdje je a realni
	 * dio kompleksnog broja, a b imaginarni.
	 * 
	 * @returns String oblika a+bi
	 */

	@Override
	public String toString() {
		String imaginaryString = "";
		String realString = "";
		if (real != 0) {
			realString += real;
		} else {
			if (imaginary == 1) {
				return "i";
			} else if (imaginary == -1) {
				return "-i";
			} else {
				return imaginary + "i";
			}
		}

		if (imaginary != 0) {
			if (imaginary == 1 && real != 0) {
				return realString + "+i";
			} else if (imaginary == 1 && real == 0) {
				return "i";
			} else if (imaginary == -1) {
				return realString + "-i";
			} else if (imaginary > 0) {
				return realString + "+" + imaginary + "i";
			} else {
				return realString + imaginary + "i";
			}
		}
		return realString + imaginaryString;
	}
}
