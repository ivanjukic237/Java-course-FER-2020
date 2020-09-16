package hr.fer.zemris.java.raytracer;

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
 * of light sources whose rays intersect those objects.
 * 
 * @author Ivan Jukić
 *
 */

public class RayCaster {

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
	 * {@inheritDoc}
	 */

	private static IRayTracerProducer getIRayTracerProducer() {

		return new IRayTracerProducer() {

			/**
			 * Method which is called by GUI when a scene snapshot is required. Creates the
			 * scene, calculates the needed vectors for the screen, and runs the coloring
			 * algorithm for every pixel in the screen.
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

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).normalize();
				viewUp.modifyNormalize();
				Point3D yAxis = viewUp.sub(OG.scalarMultiply(OG.scalarProduct(viewUp)).normalize());
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal * 0.5))
						.add(yAxis.scalarMultiply(vertical * 0.5));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((x * horizontal) / (width - 1)))
								.sub(yAxis.scalarMultiply((vertical * y) / (height - 1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
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

		for (GraphicalObject objectInScene : scene.getObjects()) {
			RayIntersection intersection = objectInScene.findClosestRayIntersection(ray);

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
		// ambient component
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
				;

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
