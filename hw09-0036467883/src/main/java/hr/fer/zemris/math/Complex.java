package hr.fer.zemris.math;

/**
 * 
 * The Complex class is a model of a set of complex numbers and operations
 * calculations with complex numbers. The precision in the calculation is double
 * what it is should be considered when using operations.
 * 
 * @author Ivan Jukić
 *
 */

public class Complex {
	private double real;
	private double imaginary;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * The constructor sets the real and complex part of a complex number.
	 * 
	 * @param real      sets the real part of a complex number
	 * @param imaginary sets the imaginary part of a complex number
	 */

	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * The method creates a String field of an input complex number where the
	 * members of the field are real and complex part of a complex number.
	 *
	 * @throws NumberFormatException if the input is not in a + bi format
	 * @param Complex
	 * @return field where the members of the field are a real and complex part of a
	 *         complex number
	 */

	private static String[] toComplexArray(String Complex) {

		String[] ComplexStringArray = Complex.split("(?=[+-])");
		// Baca iznimku ako ima više od 2 elementa, ako su oba člana kompleksni i ako
		// nijedan član (od 2) nije kompleksni broj
		if (ComplexStringArray.length > 2 || ComplexStringArray.length != 1 && (!ComplexStringArray[1].contains("i")
				|| (ComplexStringArray[0].contains("i") && ComplexStringArray[1].contains("i")))

		) {
			throw new NumberFormatException("Ulaz mora biti u formatu a+bi.");
		}
		return ComplexStringArray;
	}

	/**
	 * The method interprets a complex number entered as a String. method checks if
	 * the entered number is in the correct format.
	 *
	 * @throws NullPointerException  if the input is an empty string or null
	 * @throws NumberFormatException if the input is not a legitimate complex number
	 * @param input expression that is interpreted as a complex number
	 * @return complex number
	 */

	public static Complex parse(String input) {
		double real = 0;
		String realString = "0";
		double imaginary = 0;
		String imaginaryString = "0";

		if (input == null || input.equals("")) {
			throw new NullPointerException("Ulaz ne smije biti prazan ili null.");
		}
		input = input.replaceAll("\\s+", "");

		String[] ComplexArray = toComplexArray(input);

		int length = ComplexArray.length;
		for (int i = 0; i < length; i++) {
			if (ComplexArray[i].equals("i") || ComplexArray[i].equals("+i")) {
				imaginaryString = "1";
			} else if (ComplexArray[i].equals("-i")) {
				imaginaryString = "-1";
			} else if (ComplexArray[i].contains("i")) {
				imaginaryString = ComplexArray[i].replace("i", "");
			} else {
				realString = ComplexArray[i];
			}
		}
		try {
			real = Double.parseDouble(realString);
			imaginary = Double.parseDouble(imaginaryString);
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(
					"Vrijednosti realnog i imaginarnog dijela kompleksnog broja moraju biti brojevi i format mora biti a+bi.");
		}
		return new Complex(real, imaginary);
	}

	/**
	 * The method creates a complex number given a real part and an imaginary part
	 * zero.
	 *
	 * @param real real part of a complex number
	 * @return complex number
	 */

	public static Complex fromReal(double real) {
		return new Complex(real, 0);
	}

	/**
	 * The method creates a complex number given an imaginary part and a real part
	 * zero.
	 *
	 * @param real real part of a complex number
	 * @return complex number
	 */

	public static Complex fromImaginary(double imaginary) {
		return new Complex(0, imaginary);
	}

	/**
	 * 
	 * The method creates a complex number using an angle and a complex number
	 * module using the formula rcos (θ) + rsin (θ) ⋅i.
	 *
	 * @param magnitude module of complex number
	 * @param angle     angle of a complex number in radians
	 * @return complex number
	 */

	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * The method returns the real part of a complex number.
	 *
	 * @return the real part of a complex number.
	 */

	public double getReal() {
		return real;
	}

	/**
	 * 
	 * The method returns the imaginary part of a complex number.
	 *
	 * @return the imaginary part of a complex number
	 */

	public double getImaginary() {
		return imaginary;
	}

	/**
	 * The method returns the modulus of the complex number z = a + bi using the
	 * formula sqrt (Re (z) ^ 2 + Im (z) ^ 2).
	 *
	 * @return module of complex number
	 */

	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * 
	 * The method returns the angle of a complex number using the formula θ = tan ^
	 * -1 (Im (z) / Re (z))
	 *
	 * @return angle of a complex number
	 */

	public double getAngle() {
		return Math.atan(imaginary / real);
	}

	/**
	 * 
	 * The method defines the sum of two complex numbers.
	 *
	 * @param addend number to be added
	 * @return the sum of two complex numbers
	 */

	public Complex add(Complex addend) {
		return new Complex(real + addend.real, imaginary + addend.imaginary);
	}

	/**
	 * 
	 * The method defines the difference of two complex numbers.
	 *
	 * @param subtrahend number to subtract
	 * @return difference
	 */

	public Complex sub(Complex subtrahend) {
		return new Complex(real - subtrahend.real, imaginary - subtrahend.imaginary);
	}

	/**
	 * 
	 * The method defines the product of two complex numbers.
	 *
	 * @param multiplier number by which to multiply
	 * @return multiplication
	 */

	public Complex mul(Complex multiplier) {
		return new Complex(real * multiplier.real - imaginary * multiplier.imaginary,
				real * multiplier.imaginary + imaginary * multiplier.real);
	}

	/**
	 * 
	 * The method defines the division of two complex numbers
	 *
	 * @throws IllegalArgumentException if trying to divide by zero
	 * @param divisor the number to be divided
	 * @return quotient
	 */

	public Complex div(Complex divisor) {
		double k = Math.pow(divisor.getImaginary(), 2) + Math.pow(divisor.getReal(), 2);
		return new Complex((this.getReal() * divisor.getReal() + this.getImaginary() * divisor.getImaginary()) / k,
				(this.getImaginary() * divisor.getReal() - this.getReal() * divisor.getImaginary()) / k);
	}

	/**
	 * 
	 * The method defines the exponentiation of a complex number.
	 *
	 * @throws IllegalArgumentException if an attempt is made to give a potency that
	 *                                  is less from zero
	 * @param n potency value
	 * @return the power of a complex number
	 * 
	 */

	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija mora biti veća ili jednaka nuli.");
		} else {
			Complex temp = this;
			if (n == 0) {
				return Complex.ONE;
			}
			for (int i = 1; i < n; i++) {
				temp = temp.mul(this);
			}
			return temp;
		}

	}

	/**
	 * The method defines the rooting of a complex number z using a formula z = ρ ^
	 * 1 / n (cos (ϕ + 2πkn) + isin (ϕ + 2kπn)) where k∈ {0,1, ..., n − 1}.
	 *
	 * @param n root value
	 * @return rooting solution field
	 */

	public Complex[] root(int n) {
		Complex[] listOfRoots = new Complex[n];

		if (n <= 0) {
			throw new IllegalArgumentException("Korijen mora biti veći od nule.");
		} else {
			double rootMagnitude = Math.pow(getMagnitude(), 1 / n);
			double angle = getAngle();
			for (int i = 0; i < n; i++) {
				listOfRoots[i] = new Complex(rootMagnitude * Math.cos((angle + 2 * Math.PI * i) / n),
						rootMagnitude * Math.sin((angle + 2 * Math.PI * i) / n));
			}
		}
		return listOfRoots;
	}

	/**
	 * The method returns a representation of a complex number in the form a + bi
	 * where a is real part of a complex number, a b imaginary.
	 *
	 * @returns String of form a + bi
	 */

	@Override
	public String toString() {
		if (imaginary >= 0) {
			return String.format("(%.1f+%.1fi)", real, Math.abs(imaginary));
		} else {
			return String.format("(%.1f%.1fi)", real, imaginary);
		}

	}

	/**
	 * Negates the complex number.
	 * 
	 * @return negated complex number
	 */
	
	public Complex negate() {
		return new Complex(-1 * real, -1 * imaginary);
	}

	/**
	 * Scales the complex number by some constant n.
	 * 
	 * @param n constant of scaling
	 * @return scaled complex number
	 */
	
	public Complex scale(int n) {
		return new Complex(n * real, n * imaginary);
	}
}
