package exosoft.iso;

import java.awt.geom.Rectangle2D;

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
	protected Rectangle2D bounds;
	protected boolean hitCeiling;

	// Declares a new Entity
	public Entity(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle2D.Double(getX(), getY(), spriteWidth, spriteHeight);
	}

	public synchronized void collide2D(Object object) {
		Vector[] axes = new Vector[object.npoints];
		// loop over the vertices
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
		Rectangle2D newBounds = getBounds();
		newBounds.setRect(x, y, spriteWidth, spriteHeight);
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
			newBounds.setRect(x, y, spriteWidth, spriteHeight);
			intersect = intersect.createIntersection(newBounds);
		}
		setLocation(x, y);
		if (atRest || hitCeiling) {
			if (velocity > 8) {
				setVelocity(-velocity / 2);
			} else {
				setVelocity(0);
			}
		}
		bounds.setRect(x, y, spriteWidth, spriteHeight);
	}

	// Inherited function that runs the physics of the environment
	public synchronized void physics() {
		setY(getY() + velocity);
		setVelocity(velocity + gravity);
		bounds.setRect(x, y, spriteWidth, spriteHeight);
	}

	// Visual logic to be handled by subclass
	public abstract void visual();

	public Rectangle2D getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle2D bounds) {
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