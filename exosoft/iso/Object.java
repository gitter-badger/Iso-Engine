package exosoft.iso;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Object extends Polygon {
	public BufferedImage texture;
	private boolean antialiasStatus;

	public BufferedImage getTexturedObject() {
		Rectangle r = this.getBounds();
		BufferedImage tmp = new BufferedImage(r.width + 2, r.height + 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		if (antialiasStatus) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		AffineTransform centerTransform = AffineTransform.getTranslateInstance(-r.x + 1, -r.y + 1);
		g.setTransform(centerTransform);
		g.setClip(this);
		g.setColor(Color.lightGray);
		g.fillPolygon(this);
		g.drawImage(texture, this.getBounds().x, this.getBounds().y, null);
		g.setClip(null);
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(1));
		g.draw(this);
		g.dispose();
		return tmp;
	}

	public Object(Point... vertices) {
		for (Point vertex : vertices) {
			this.addPoint(vertex.x, vertex.y);
		}
	}

	public Object(BufferedImage texture, Point... vertices) {
		this(vertices);
		this.setTexture(texture);
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public void setTexture(String path) {
		try {
			this.texture = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Error when setting texture of object " + this.toString());
			e.printStackTrace();
		}
	}

	public enum objectType {
		INTERACTIVE, DYNAMIC, STATIC
	}

	public enum objectDistance {
		FOREGROUND, FOCUS, BACKGROUND
	}
}
