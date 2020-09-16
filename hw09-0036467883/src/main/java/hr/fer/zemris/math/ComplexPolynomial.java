package hr.fer.zemris.math;

/**
 * Class models the complex polynomial and operations on polynomials, like
 * derivative, multiplication etc. This is a polynomial in the form of p(z) =
 * a0+a1*z+a2*z^2+*a3*z^3...
 * 
 * @author Ivan Jukić
 *
 */

public class ComplexPolynomial {

	private Complex[] factors;

	/**
	 * Creates the complex polynomials from given factors.
	 * 
	 * @param factors factors of the polynomial
	 */

	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns the array of complex factors of the polynomial.
	 * 
	 * @return array of complex factors of the polynomial.
	 */

	public Complex[] getFactors() {
		return factors;
	}

	/**
	 * Returns the order of the polynomial.
	 * 
	 * @return order of the polynomial
	 */

	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Method multiplies a complex polynomial by a complex number.
	 * 
	 * @param root complex number
	 * @return multiplied complex polynomial
	 */

	public ComplexPolynomial multiplyByOrderOne(Complex root) {

		Complex[] temp = new Complex[factors.length + 1];

		for (int i = 0; i < factors.length; i++) {
			if (temp[i + 1] == null) {
				temp[i + 1] = Complex.ZERO;
			}
			if (temp[i] == null) {
				temp[i] = Complex.ZERO;
			}
			temp[i + 1] = temp[i + 1].add(factors[i]);
			temp[i] = temp[i].add(factors[i].mul(root.negate()));

		}
		return new ComplexPolynomial(temp);
	}

	/**
	 * Computes the first derivative of the polynomial.
	 * 
	 * @return first derivative of the polynomial
	 */

	public ComplexPolynomial derive() {
		Complex[] temp = new Complex[factors.length - 1];

		for (int i = 0; i < factors.length - 1; i++) {
			temp[i] = factors[i + 1].scale(i + 1);
		}
		return new ComplexPolynomial(temp);
	}

	/**
	 * Applies a given complex number to the polynomial.
	 * 
	 * @param z complex number
	 * @return p(z) wher p is the polynomial
	 */

	public Complex apply(Complex z) {
		Complex temp = new Complex(0, 0);
		for (int i = 0; i < factors.length; i++) {
			temp = temp.add(z.power(i).mul(factors[i]));
		}
		return temp;
	}

	/**
	 * Returns a string represenation of the complex polynomial in the form p(z) =
	 * a0+a1*z+a2*z^2+*a3*z^3...
	 */

	public String toString() {
		StringBuilder sb = new StringBuilder(factors[0].toString());

		for (int i = 1; i < factors.length; i++) {
			sb.append("+");
			sb.append(factors[i].toString());
			sb.append(String.format("*z^%d", i));
		}
		return sb.toString();
	}
}
