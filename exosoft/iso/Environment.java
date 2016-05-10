package exosoft.iso;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import exosoft.iso.Entity;

/**
 * The game world consists of objects (such as floors, platforms and walls) and
 * entities (such as the player).
 */
public class Environment {
	List<Shape> objects = new ArrayList<Shape>();
	List<Entity> entities = new ArrayList<Entity>();
	public double gravity = 0.2;

	public void spawnEntity(Entity e) {
		e.spawn(this);
		entities.add(e);
	}

	public void addObject(Shape s) {
		objects.add(s);
	}

	public void createObject(double x, double y, double w, double h) {
		objects.add(new Rectangle2D.Double(x, y, w, h));
	}

	public boolean checkCollision(Rectangle2D bounds) {
		for (Shape object : objects) {
			if (object.intersects(bounds)) {
				return true;
			}
		}
		return false;
	}

	public Rectangle2D intersection(Rectangle2D bounds) {
		for (Shape object : objects) {
			if (object.getBounds().intersects(bounds)) {
				return bounds.createIntersection(object.getBounds());
			}
		}
		return new Rectangle2D.Double();
	}

	public Shape[] getObjects() {
		Shape[] objectArray = new Shape[this.objects.size()];
		int index = 0;
		for (Shape object : objects) {
			objectArray[index] = object;
			index++;
		}
		return objectArray;
	}

	public Entity[] getEntities() {
		Entity[] entityArray = new Entity[this.entities.size()];
		int index = 0;
		for (Entity entity : entities) {
			entityArray[index] = entity;
			index++;
		}
		return entityArray;
	}

	enum objectType {
		INTERACTIVE, DYNAMIC, STATIC
	}

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
	        // create a transform to center the shape in the image
	        AffineTransform centerTransform = AffineTransform.
	                getTranslateInstance(-r.x+1, -r.y+1);
	        // set the transform to the graphics object
	        g.setTransform(centerTransform);
	        // set the shape as the clip
	        g.setClip(this);
	        // draw the image
	        g.drawImage(texture, this.getBounds().x, this.getBounds().y, null);
	        // clear the clip
	        g.setClip(null);
	        // draw the shape as an outline
	        g.setColor(Color.RED);
	        g.setStroke(new BasicStroke(1f));
	        g.draw(this);
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
}