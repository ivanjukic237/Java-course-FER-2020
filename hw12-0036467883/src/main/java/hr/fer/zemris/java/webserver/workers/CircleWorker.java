package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that paints a 200x200 circle and sends it to the client via context.
 * 
 * @author Ivan Jukić
 *
 */

public class CircleWorker implements IWebWorker {

	/**
	 * Creates a circle and sends it to the client.
	 * 
	 * @param context context of the client
	 */

	@Override
	public void processRequest(RequestContext context) throws Exception {
		circle(context);

	}
	private void circle(RequestContext context) throws IOException {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.PINK);
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		g2d.setColor(Color.magenta);
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
		g2d.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bim, "png", bos);
		context.setMimeType("image/png");
		context.write(bos.toByteArray());
	}
	
}
