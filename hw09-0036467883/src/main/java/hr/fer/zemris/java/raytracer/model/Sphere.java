package hr.fer.zemris.java.raytracer.model;

/**
 * Model of the sphere which holds the constants for diffuse and reflective
 * intensity of the material.
 * 
 * @author Ivan Jukić
 *
 */

public class Sphere extends GraphicalObject {

	Point3D center;
	double radius;
	double kdr;
	double kdg;
	double kdb;
	double krr;
	double krg;
	double krb;
	double krn;

	/**
	 * Initializes the variables of the sphere.
	 * 
	 * @param center center of the sphere
	 * @param radius radius of the sphere
	 * @param kdr    Constant for red diffuse intensity.
	 * @param kdg    Constant for green diffuse intensity.
	 * @param kdb    Constant for blue diffuse intensity.
	 * @param krr    Constant for red reflective intensity.
	 * @param krg    Constant for blue reflective intensity.
	 * @param krb    Constant for green reflective intensity.
	 * @param krn    Constant roughness of the material.
	 */

	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Finds the closest intersection of the given ray and the sphere.
	 * 
	 * @param ray ray for which the closest intersection is found
	 * @return null if there is no intersection and RayIntersection if there is an
	 *         intersection
	 */

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		// point of intersection
		Point3D p;
		Point3D l = center.sub(ray.start);
		double lenCenStart = l.scalarProduct(ray.direction);

		if (lenCenStart < 0) {
			return null;
		}

		double d = Math.sqrt(l.scalarProduct(l) - lenCenStart * lenCenStart);
		if (d > radius) {
			return null;
		}
		double lenCenP = Math.sqrt(radius * radius - d * d);
		// point closest to sphere
		double t0 = lenCenStart - lenCenP;
		// double t1 = lenCenStart + lenCenP;

		p = ray.start.add(ray.direction.scalarMultiply(t0));

		return new MyRayIntersection(p, t0, true);
	}

	/**
	 * Ray-sphere intersection.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public class MyRayIntersection extends RayIntersection {

		/**
		 * Initializes the variables of intersection.
		 * 
		 * @param point    point of intersection
		 * @param distance distance from the start of the ray to the intersection
		 * @param outer    is it an outer or inner intersection
		 */

		protected MyRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);

		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		/**
		 * Returns the normal for the sphere using the formula c-p where c is the vector
		 * for the center of the sphere, and p is the intersection.
		 */

		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).modifyNormalize();
		}

	}
}
