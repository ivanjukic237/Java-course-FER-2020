package hr.fer.zemris.math;

/**
 * Class models the 3D vector in Cartesian coordinate system and operations on
 * the vector like addition, subtraction, dot product etc.
 * 
 * @author Ivan Jukić
 *
 */

public class Vector3 {

	private final double x;
	private final double y;
	private final double z;

	/**
	 * Creates the vector.
	 * 
	 * @param x x component
	 * @param y y component
	 * @param z z component
	 */

	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the norm of the vector.
	 * 
	 * @return norm of the vector
	 */
	
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalizes the vector.
	 * 
	 * @return normalized vector
	 */
	
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Adds a given vector to this vector..
	 * @param other vector to add
	 * @return sum of two vectors
	 */
	
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}

	/**
	 * Subtracts a given vector with this vector.
	 * 
	 * @param other other vector to subtract
	 * @return difference
	 */
	
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	public double dot(Vector3 other) {
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}

	/**
	 * Calculates the cross product.
	 * 
	 * @param other vector to calculate cross product
	 * @return cross product
	 */
	
	public Vector3 cross(Vector3 other) {
		double x1 = other.getX();
		double y1 = other.getY();
		double z1 = other.getZ();
		return new Vector3(y * z1 - z * y1, z * x1 - x * z1, x * y1 - y * x1);
	}

	/**
	 * Scales the vector by parameter s.
	 * 
	 * @param s scale parameter
	 * @return scaled vector
	 */
	
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Returns the cos angle with another vector.
	 * 
	 * @param other other vector to calculate the angle
	 * @return cos angle
	 */
	
	public double cosAngle(Vector3 other) {
		double x1 = other.getX();
		double y1 = other.getY();
		double z1 = other.getZ();
		double normOther = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
		return dot(other) / (normOther * norm());
	}

	/**
	 * Returns the array with x, y and z coordinates of the vector.
	 * 
	 * @return array with x, y and z coordinates of the vector
	 */
	
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	/**
	 * String representation of the vector in the form (x, y, z).
	 */
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

	/*
	 * Returns x component of the vector.
	 */
	
	public double getX() {
		return x;
	}

	/*
	 * Returns y component of the vector.
	 */
	
	public double getY() {
		return y;
	}

	/*
	 * Returns z component of the vector.
	 */
	
	public double getZ() {
		return z;
	}

}
