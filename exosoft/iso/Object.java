package exosoft.iso;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Object extends Polygon {
	public BufferedImage texture;
	public BufferedImage getTexturedObject() {
        Rectangle r = this.getBounds();
        // create a transparent image with 1 px padding.
        BufferedImage tmp = new BufferedImage(
                r.width+2,r.height+2,BufferedImage.TYPE_INT_ARGB);
        // get the graphics object
        Graphics2D g = tmp.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // create a transform to center the shape in the image
        AffineTransform centerTransform = AffineTransform.
                getTranslateInstance(-r.x+1, -r.y+1);
        // set the transform to the graphics object
        g.setTransform(centerTransform);
        // set the shape as the clip
        g.setClip(this);
        // draw the image
        g.setColor(Color.lightGray);
        g.fillPolygon(this);
        g.drawImage(texture, this.getBounds().x, this.getBounds().y, null);
        // clear the clip
        g.setClip(null);
        // draw the shape as an outline
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(1f));
        g.draw(this);
        Line2D[] sides = new Line2D[this.npoints];
		int[] xpoints = this.xpoints;
		int[] ypoints = this.ypoints;
		for (int i = 0; i < sides.length - 1; i++) {
			g.drawLine(xpoints[i], xpoints[i + 1], xpoints[i + 1], ypoints[i]);
		}
		g.drawLine(xpoints[sides.length - 1], ypoints[0], xpoints[0], ypoints[sides.length - 1]);
        // dispose of any graphics object we explicitly create
        g.dispose();
        return tmp;
    }
	public Object(Point... vertices) {
		for (Point vertex : vertices) {
			this.addPoint(vertex.x, vertex.y);
		}
	}
	public Object(BufferedImage texture, Point... vertices) {
		for (Point vertex : vertices) {
			this.addPoint(vertex.x, vertex.y);
		}
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
}
