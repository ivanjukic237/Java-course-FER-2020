package hr.fer.zemris.math;

/**
 * Model of the factored complex polynomial and operations on it. This is a
 * polynomial in the form of p(z) = a0*(z-a1)(z-a2)(z-a3)... where an are the
 * roots of the polynomial.
 * 
 * @author Ivan Jukić
 *
 */

public class ComplexRootedPolynomial {

	Complex constant;
	public Complex[] roots;

	/**
	 * Creates the rooted polynomial with constant a0 and roots.
	 * 
	 * @param constant a0 in the polynomial
	 * @param roots    roots of the polynomial
	 */

	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Applies a given complex number to the polynomial.
	 * 
	 * @param z complex number
	 * @return p(z) wher p is the polynomial
	 */

	public Complex apply(Complex z) {
		Complex temp = constant;

		for (Complex complex : roots) {
			temp = temp.mul(z.sub(complex));
			if (temp.getImaginary() == 0 && temp.getReal() == 0) {
				return new Complex(0, 0);
			}
		}
		return temp;
	}

	/**
	 * Returns a string represenation of the complex polynomial in the form p(z) =
	 * a0*(z-a1)(z-a2)(z-a3)...
	 */

	public String toString() {
		StringBuilder sb = new StringBuilder(constant.toString());
		for (int i = 0; i < roots.length; i++) {
			sb.append("*(z-");
			sb.append(roots[i]);

		}
		return sb.toString();
	}

	/**
	 * Transforms the rooted polynomial to the regular polynomial in the form of
	 * p(z) = a0+a1*z+a2*z^2+*a3*z^3...
	 * 
	 * @return
	 */

	// every factor in ComplexRootedPolynomial is a ComplexPolynomial
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial tempPolynomial = new ComplexPolynomial(constant);
		for (int i = 0; i < roots.length; i++) {
			tempPolynomial = tempPolynomial.multiplyByOrderOne(roots[i]);
		}

		return tempPolynomial;
	}

	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double min = roots[0].sub(z).getMagnitude();
		for (int i = 0; i < roots.length; i++) {
			double temp = roots[i].sub(z).getMagnitude();
			if (temp < treshold && temp <= min) {
				index = i;
				min = temp;
			}

		}
		return index;
	}
}
