package exosoft.iso;

import java.awt.geom.Rectangle2D;

/** Abstract class based off of the Sprite class.
* For in-game entities that have movement capability 
* and interact with physics. The main class must have
* its own implementation of this subclass.
*/
public abstract class Entity extends Sprite implements ObjectPhysics {
    protected double x;
    protected double y;
    public double velocity;
    protected double gravity;
    protected Rectangle2D bounds;
    protected boolean atRest = false;
    public Environment location;

    // Declares a new Entity
	public Entity(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle2D.Double(getX(), getY(), spriteWidth, spriteHeight);
	}

	public void spawn(Environment location) {
		this.location = location;
		this.gravity = location.gravity;
	}
	
    //Detects and compensates for collision
	public synchronized void collision() {
	    double x = getX();
		double y = getY() + velocity;
		Rectangle2D newBounds = getBounds();
		newBounds.setRect(getX(), y, spriteWidth, spriteHeight);
		if (location.checkCollision(newBounds)) {
			Rectangle2D intersect = location.intersection(newBounds);
			try {
				while (!intersect.isEmpty()) {
					if (newBounds.getMinX() < intersect.getMinX()) {
						x -= 1;
					}
					if (newBounds.getMaxY() > intersect.getMinY()) {
						y -= location.gravity;
						newBounds.setRect(x, y, spriteWidth, spriteHeight);
						atRest = true;
					}
					intersect = location.intersection(newBounds);
				}
				setLocation(x, y);
				if (atRest && velocity > 2) {
					setVelocity(-velocity / 2);
				} else {
					setVelocity(0);
				}
			} catch (NullPointerException e) {
				System.err.println("Collision had some sort of nullpointerexception but it's fine");
			}
		} else {
			atRest = false;
		}
		bounds.setRect(x, y, spriteWidth, spriteHeight);
	}

    // Inherited function that runs the physics of the environment
	public synchronized void physics() {
		setY(getY() + velocity);
		setVelocity(velocity + gravity);
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
}