package exosoft.iso;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import princeton.calc.Projection;
import princeton.calc.Vector;

/**
 * Abstract class based off of the Sprite class. For in-game entities that have
 * movement capability and interact with physics. The main class must have its
 * own implementation of this subclass.
 */
public abstract class Entity extends Sprite implements ObjectPhysics {
	protected boolean atRest;
	protected double x;
	protected double y;
	public double velocity;
	protected double gravity;
	protected Rectangle bounds;
	protected Polygon bounds2D;
	protected boolean hitCeiling;

	// Declares a new Entity
	public Entity(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle();
		bounds.setBounds((int) getX(), (int) getY(), spriteWidth, spriteHeight);
		bounds2D = new Polygon();
		bounds2D.addPoint((int) bounds.getMinX(), (int) bounds.getMinY());
		bounds2D.addPoint((int) bounds.getMaxX(), (int) bounds.getMaxY());
		bounds2D.addPoint((int) bounds.getMaxX(), (int) bounds.getMaxY());
		bounds2D.addPoint((int) bounds.getMinX(), (int) bounds.getMaxY());
		bounds2D.addPoint((int) bounds.getMinX(), (int) bounds.getMinY());
	}

	public boolean collides(Object object) {
		boolean check = true;
		double temp = 0;
		Projection p1;
		Projection p2;
		Point2D.Double axis;

		for (int i = 0; i < object.npoints; i++) {
			if (check) {
				double min = 0, max = 0;
				double dx = object.xpoints[i + 1 == object.npoints ? 0 : i + 1] - object.xpoints[i];
				double dy = object.ypoints[i + 1 == object.npoints ? 0 : i + 1] - object.ypoints[i];

				double x = (dx) / Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				double y = (dy) / Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				axis = new Point2D.Double(-x, y);

				min = max = (axis.x * bounds2D.xpoints[0]) + (axis.y * bounds2D.ypoints[0]);

				// project this shape
				for (int j = 0; j < bounds2D.npoints; j++) {
					temp = (axis.x * bounds2D.xpoints[j]) + (axis.y * bounds2D.ypoints[j]);
					if (temp < min)
						min = temp;
					if (temp > max)
						max = temp;
				}
				p1 = new Projection(min, max);

				min = max = (axis.x * object.xpoints[0]) + (axis.y * object.ypoints[0]);
				// project other shape
				for (int j = 0; j < object.npoints; j++) {
					temp = (axis.x * object.xpoints[j]) + (axis.y * object.ypoints[j]);
					if (temp < min)
						min = temp;
					if (temp > max)
						max = temp;
				}
				p2 = new Projection(min, max);

				if (!p1.overlap(p2))
					check = false;
			}
		}

		return check;
	}

	public synchronized void collide2D(Object object) {
		Vector[] axes = new Vector[object.npoints];
		// loop over the points
		for (int i = 0; i < object.npoints; i++) {
			// get the current vertex
			Vector p1 = new Vector(object.xpoints[i], object.ypoints[i]);
			// get the next vertex
			Vector p2 = new Vector(object.xpoints[i + 1 == object.npoints ? 0 : i + 1],
					object.ypoints[i + 1 == object.npoints ? 0 : i + 1]);
			// subtract the two to get the edge vector
			Vector edge = p1.minus(p2);
			// get either perpendicular vector
			Vector normal = edge.getNormal();
			// the perp method is just (x, y) => (-y, x) or (y, -x)
			axes[i] = normal;
		}
	}

	// Compensates for collision
	public synchronized void collide(Rectangle2D intersect) {
		atRest = false;
		hitCeiling = false;
		double x = getX();
		double y = getY() + velocity;
		Rectangle newBounds = getBounds();
		newBounds.setBounds((int) x, (int) y, spriteWidth, spriteHeight);
		while (!intersect.isEmpty() && (intersect.getHeight() >= 1 || intersect.getWidth() >= 1)) {
			if (intersect.getHeight() > intersect.getWidth()) {
				if (intersect.getCenterX() < newBounds.getCenterX()) {
					x += 0.01;
				} else if (intersect.getCenterX() > newBounds.getCenterX()) {
					x -= 0.01;
				}
			}
			if (intersect.getWidth() > intersect.getHeight()) {
				if (intersect.getCenterY() < newBounds.getCenterY()) {
					y += 0.01;
				} else if (intersect.getCenterY() > newBounds.getCenterY()) {
					y -= 0.01;
				}
			}
			if (intersect.getMaxY() == newBounds.getMaxY() && intersect.getWidth() >= 5) {
				atRest = true;
			}
			if (intersect.getMinY() == newBounds.getMinY() && intersect.getWidth() >= 5) {
				hitCeiling = true;
			}
			newBounds.setBounds((int) x, (int) y, spriteWidth, spriteHeight);
			intersect = intersect.createIntersection(newBounds);
		}
		setLocation(x, y);
		if (atRest || hitCeiling) {
			if (velocity > 5) {
				setVelocity(-velocity / 2);
			} else {
				setVelocity(0);
			}
		}
		bounds.setBounds((int) x, (int) y, spriteWidth, spriteHeight);
	}

	// Inherited function that runs the physics of the environment
	public synchronized void physics() {
		setY(getY() + velocity);
		setVelocity(velocity + gravity);
		bounds.setRect(x, y, spriteWidth, spriteHeight);
	}

	// Visual logic to be handled by subclass
	public abstract void visual();

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public int getIntX() {
		return (int) x;
	}

	public void setX(double xPosition) {
		this.x = xPosition;
	}

	public double getY() {
		return y;
	}

	public int getIntY() {
		return (int) Math.round(y);
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public abstract void movement();
}