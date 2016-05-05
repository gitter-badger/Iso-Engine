package exosoft.iso;

import java.awt.geom.Rectangle2D;

public abstract class Character extends Sprite implements ObjectPhysics {
    protected double x;
    protected double y;
    protected double velocity;
    protected Rectangle2D bounds;
    protected boolean atRest = false;

	public Avatar(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle2D.Double(getX(), getY(), spriteWidth, spriteHeight);
	}

	public synchronized void collision() {
		double nextyPosition = Math.round(getY() + getVelocity());
		Rectangle2D newBounds = getBounds2D();
		newBounds.setRect(getX(), nextyPosition, spriteWidth, spriteHeight);
		if (location.checkCollision(newBounds)) {
			Rectangle2D intersect = location.intersection(newBounds);
			try {
				while (!intersect.isEmpty()) {
					if (newBounds.getMinX() < intersect.getMinX()) {
						setX(getX() - 1);
					}
					if (newBounds.getMaxY() > intersect.getMinY()) {
						nextyPosition -= gravity;
						newBounds.setRect(getX(), nextyPosition, spriteWidth, spriteHeight);
						atRest = true;
					}
					intersect = location.intersection(newBounds);
				}
				setY(nextyPosition);
				if (atRest && getVelocity() > 2) {
					setVelocity(-getVelocity() / 2);
				} else {
					setVelocity(0);
				}
			} catch (NullPointerException e) {
				System.err.println("Collision had some sort of nullpointerexception but it's fine i swear");
			}
		} else {
			atRest = false;
		}
		bounds.setRect(getX(), getY(), spriteWidth, spriteHeight);
	}

	public synchronized void physics() {
		setY(getY() + getVelocity());
		setVelocity(getVelocity() + gravity);
	}

	public abstract void visual();

	public Rectangle2D getBounds2D() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
}