package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Newton-Raphson iteration-based fractal viewer application. User inputs the
 * roots of a complex polynomal and the applicaton draws the fractal.
 * 
 * @author Ivan Jukić
 *
 */

public class Newton {
	/**
	 * Roots of the given polynomal
	 */

	static Complex[] roots;
	static ComplexRootedPolynomial rootedPoly;
	static ComplexPolynomial polynomial;

	/**
	 * Pool of daemon threads. It is used so that threads die after coloring,
	 * considering the shutdown method can't be used. It can't be used because of
	 * the funcionality of zooming in on the fractal, so the pool will be reused.
	 */

	static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), t -> {
		Thread worker = new Thread(t);
		worker.setDaemon(true);
		return worker;
	});

	/**
	 * Method asks the user to input the roots of the polynomial and after that,
	 * when the user inputs "done", starts the fractal viewer.
	 * 
	 * @param args command line arguments (not used)
	 */

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner input = new Scanner(System.in);
		int counter = 0;
		String in;
		ArrayList<Complex> listOfRoots = new ArrayList<Complex>();
		while (true) {
			System.out.format("Root %d> ", counter + 1);
			try {
				in = input.nextLine();
				if (in.strip().equals("done")) {
					break;
				}
				listOfRoots.add(Complex.parse(in));
			} catch (NumberFormatException | NullPointerException ex) {
				counter--;
				System.out.println(ex.getMessage());
			}
			counter++;
		}
		input.close();
		roots = new Complex[listOfRoots.size()];
		for (int i = 0; i < listOfRoots.size(); i++) {
			roots[i] = listOfRoots.get(i);
		}

		rootedPoly = new ComplexRootedPolynomial(Complex.ONE, roots);
		polynomial = rootedPoly.toComplexPolynom();
		FractalViewer.show(new MojProducer());
	}

	/**
	 * Creates one worker that does a job of coloring a part of the complex plane
	 * where the fractal is.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public static class PosaoIzracuna implements Callable<Void> {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int maxIter;
		short[] data;
		AtomicBoolean cancel;

		/**
		 * Constructor initializes the variables.
		 * 
		 * @param reMin  minimal real value
		 * @param reMax  maximal real value
		 * @param imMin  minimal imaginary value
		 * @param imMax  maximal imaginary value
		 * @param width  width of the screen
		 * @param height height of the screen
		 * @param yMin   value of y from where the worker starts
		 * @param yMax   value of y where the worker stops
		 * @param m      how many iterations of the Newton algorighm will be performed
		 * @param data   array that colects the indexes of closest roots for a point
		 * @param cancel object used to cancel rendering of image; is set to
		 *               <code>true</code>, rendering should be canceled
		 */

		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = m;
			this.data = data;
			this.cancel = cancel;
		}

		/**
		 * Calculates the index of the root that the point is closest to.
		 */

		@Override
		public Void call() {

			double convergenceTreshold = 1E-3;
			double rootDistanceTreshold = 2E-3;
			Complex zn;
			for (int y = yMin; y < yMax; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;

					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					double module = 0;
					zn = new Complex(cre, cim);
					int iter = 0;

					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = polynomial.derive().apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.div(denominator);
						zn = zn.sub(fraction);

						module = zn.sub(znold).getMagnitude();
						iter++;
					} while (module > convergenceTreshold && iter < maxIter);

					int index = rootedPoly.indexOfClosestRootFor(zn, rootDistanceTreshold);
					data[y * width + x] = (short) (index + 1);
				}

			}
			return null;
		}
	}

	/**
	 * Creates an IFractalProducer that creates the fractal.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public static class MojProducer implements IFractalProducer {

		/**
		 * Divides the work of computing indices of closest roots for every point in the
		 * screen. Number of parallel workers will be 8 * number of available
		 * processors. The y axis will be divided by that number and each worker will
		 * work on points in that set.
		 * 
		 * @param reMin     minimal real value
		 * @param reMax     maximal real value
		 * @param imMin     minimal imaginary value
		 * @param imMax     maximal imaginary value
		 * @param width     width of the screen
		 * @param height    height of the screen
		 * @param requestNo used internally and must be passed on to GUI observer with
		 *                  rendered image
		 * @param observer  GUI observer that will accept and display image this
		 *                  producer creates
		 * @param cancel    object used to cancel rendering of image; is set to
		 *                  <code>true</code>, rendering should be canceled
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int numberOfThreads = Runtime.getRuntime().availableProcessors();
			final int brojTraka = numberOfThreads * 8;
			int brojYPoTraci = height / brojTraka;

			List<Future<Void>> rezultati = new ArrayList<>();

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci;
				if (i == brojTraka - 1) {
					yMax = height;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
						cancel);
				rezultati.add(pool.submit(posao));
			}
			for (Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}
}
