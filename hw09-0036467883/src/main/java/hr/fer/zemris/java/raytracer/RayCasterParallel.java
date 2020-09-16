package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class does ray tracing for a scene with some objects in it. Ray tracing
 * creates a model for determining a 2D picture for what the observer sees while
 * observing a scene of 3D objects and some number of light sources. Algorithm
 * first finds the 2D picture of the scene observer sees, and then colors every
 * pixel with linear combination of diffuse, ambient and reflective intensities
 * of light sources whose rays intersect those objects. This class uses the
 * ForkJoinPool framework to make the coloring of every pixel parallel.
 * 
 * @author Ivan Jukić
 *
 */

public class RayCasterParallel {

	/**
	 * Runs the ray caster.
	 * 
	 * @param args command line arguments (not used here)
	 */

	public static void main(String[] args) {

		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);

	}

	/**
	 * Creates a worker for ForkJoinPool.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public static class PosaoIzracuna extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		int yMin;
		int yMax;
		double horizontal;
		double vertical;
		Point3D yAxis;
		Point3D xAxis;
		int height;
		int width;
		Point3D eye;
		short[] red;
		short[] green;
		short[] blue;
		Point3D screenCorner;
		Scene scene;
		AtomicBoolean cancel;
		static final int treshold = 16;

		/**
		 * Constructor initializes the values.
		 * 
		 * @param yMin         value of y where the work starts for one worker
		 * @param yMax         value of y where the work ends for one worker
		 * @param horizontal   horizontal width of observed space
		 * @param vertical     vertical height of observed space
		 * @param yAxis        calculated vector of the y axis
		 * @param xAxis        calculated vector of the x axis
		 * @param height       number of pixel per screen column
		 * @param width        number of pixels per screen row
		 * @param eye          position of human observer
		 * @param red          array that holds the color intensities for the red color
		 * @param green        array that holds the color intensities for the green
		 *                     color
		 * @param blue         array that holds the color intensities for the blue color
		 * @param screenCorner top left corner of the screen
		 * @param scene        scene of objects
		 * @param cancel       object used to cancel rendering of image; is set to
		 *                     <code>true</code>, rendering should be canceled
		 */

		public PosaoIzracuna(int yMin, int yMax, double horizontal, double vertical, Point3D yAxis, Point3D xAxis,
				int height, int width, Point3D eye, short[] red, short[] green, short[] blue, Point3D screenCorner,
				Scene scene, AtomicBoolean cancel) {
			super();
			this.yMin = yMin;
			this.yMax = yMax;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.height = height;
			this.width = width;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.cancel = cancel;
		}

		/**
		 * Recursively calls the splitting of the work (fork).
		 */

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}

			invokeAll(
					new PosaoIzracuna(yMin, yMin + (yMax - yMin) / 2, horizontal, vertical, yAxis, xAxis, height, width,
							eye, red, green, blue, screenCorner, scene, cancel),
					new PosaoIzracuna(yMin + (yMax - yMin) / 2, yMax, horizontal, vertical, yAxis, xAxis, height, width,
							eye, red, green, blue, screenCorner, scene, cancel));

		}

		/**
		 * The smallest unit of work that is computed directly. It is determined by the
		 * treshold variable.
		 */

		public void computeDirect() {
			short[] rgb = new short[3];
			for (int y = yMin; y < yMax; y++) {
				if (cancel.get()) {
					break;
				}
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((x * horizontal) / (width - 1)))
							.modifySub(yAxis.scalarMultiply((vertical * y) / (height - 1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(scene, ray, rgb);
					red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
					green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
					blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];

				}
			}
		}

	}

	private static IRayTracerProducer getIRayTracerProducer() {

		/**
		 * Method which is called by GUI when a scene snapshot is required. Creates the
		 * scene, calculates the needed vectors for the screen, and runs the coloring
		 * algorithm for every pixel in the screen. ForkJoinPool framework is used to
		 * split the work.
		 * 
		 * @param eye        position of human observer
		 * @param view       position that is observed
		 * @param viewUp     specification of view-up vector which is used to determine
		 *                   y-axis for screen
		 * @param horizontal horizontal width of observed space
		 * @param vertical   vertical height of observed space
		 * @param width      number of pixels per screen row
		 * @param height     number of pixel per screen column
		 * @param requestNo  used internally and must be passed on to GUI observer with
		 *                   rendered image
		 * @param observer   GUI observer that will accept and display image this
		 *                   producer creates
		 * @param cancel     object used to cancel rendering of image; is set to
		 *                   <code>true</code>, rendering should be canceled
		 */

		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				// računa vrijeme operacije
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).modifyNormalize();
				viewUp.modifyNormalize();
				Point3D yAxis = viewUp.modifySub(OG.scalarMultiply(OG.scalarProduct(viewUp)).modifyNormalize());
				Point3D xAxis = OG.vectorProduct(yAxis).modifyNormalize();
				Point3D screenCorner = view.modifySub(xAxis.scalarMultiply(horizontal * 0.5))
						.add(yAxis.scalarMultiply(vertical * 0.5));
				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new PosaoIzracuna(0, height, horizontal, vertical, yAxis, xAxis, height, width, eye, red,
						green, blue, screenCorner, scene, cancel));
				pool.shutdown();

				observer.acceptResult(red, green, blue, requestNo);
			}
		};
	}

	/**
	 * Checks the closest object that the ray from the observer to the given pixel
	 * intersects. After that it calls the function that colors the given pixel. If
	 * there is no such object, the pixel is colored with black color.
	 * 
	 * @param scene scene with objects in it
	 * @param ray   ray from observer to the given pixel
	 * @param rgb   array that stores the colors
	 */

	public static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection minIntersection = null;
		RayIntersection intersection;

		for (GraphicalObject objectInScene : scene.getObjects()) {
			intersection = objectInScene.findClosestRayIntersection(ray);

			if (intersection != null && minIntersection != null) {
				if (intersection.getDistance() < minIntersection.getDistance()) {
					minIntersection = intersection;
				}
			} else if (minIntersection == null && intersection != null) {
				minIntersection = intersection;
			}

		}
		if (minIntersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(minIntersection, scene, rgb, ray.start);
		}
	}

	/**
	 * Algorithm that determines the color for a given pixel. Looks at every light
	 * source in the scene and determines if the ray from the light source
	 * intersects with the object. If there is a closer object (ray is blocked by
	 * another object) that light source is skipped. If there is no closer object,
	 * the diffuse and reflective intensity of the light in that intersection is
	 * calculated. The ambient component for red, green and blue is taken as 15.
	 * 
	 * @param intersection intersection of ray from the observer to the object
	 * @param scene        scene with objects in it
	 * @param rgb          array that stores the colors
	 * @param eye          vector of the position of the observer
	 */

	public static void determineColorFor(RayIntersection intersection, Scene scene, short[] rgb, Point3D eye) {
		// ambient component r,g,b = 15
		for (int i = 0; i < 3; i++) {
			rgb[i] = 15;
		}

		for (LightSource light : scene.getLights()) {
			// ray from source to intersection of our object
			Ray rayLight = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			double distanceIntersectionSource = light.getPoint().sub(intersection.getPoint()).norm();

			boolean isLightSkipped = false;
			RayIntersection rayObjectInSceneIntersection;
			for (GraphicalObject objectInScene : scene.getObjects()) {
				// if the ray intersects a closer object, we ignore that light source
				// tolerance 1E-9
				rayObjectInSceneIntersection = objectInScene.findClosestRayIntersection(rayLight);
				if (rayObjectInSceneIntersection != null
						&& rayObjectInSceneIntersection.getDistance() + 1E-9 < distanceIntersectionSource) {
					isLightSkipped = true;
					break;
				}
			}
			if (!isLightSkipped) {
				// diffuse component
				Point3D l = light.getPoint().sub(intersection.getPoint()).modifyNormalize();
				Point3D n = intersection.getNormal();
				double scalarProductLN = Double.max(n.scalarProduct(l), 0);

				double Idr = 0;
				double Idg = 0;
				double Idb = 0;

				if (scalarProductLN != 0) {
					Idr = intersection.getKdr() * light.getR() * scalarProductLN;
					Idg = intersection.getKdg() * light.getG() * scalarProductLN;
					Idb = intersection.getKdb() * light.getB() * scalarProductLN;
				}

				// reflective component
				Point3D r = n.scalarMultiply(2 * l.scalarProduct(n)).modifySub(l);
				Point3D v = eye.sub(intersection.getPoint()).modifyNormalize();
				double scalarProductRV = r.scalarProduct(v);
				double Irr = 0;
				double Irg = 0;
				double Irb = 0;
				if (scalarProductRV > 0) {
					scalarProductRV = Math.pow(scalarProductRV, intersection.getKrn());
					Irr = intersection.getKrr() * light.getR() * scalarProductRV;
					Irg = intersection.getKrg() * light.getG() * scalarProductRV;
					Irb = intersection.getKrb() * light.getB() * scalarProductRV;
				}

				rgb[0] += Idr + Irr;
				rgb[1] += Idg + Irg;
				rgb[2] += Idb + Irb;
			}

		}
	}
}
