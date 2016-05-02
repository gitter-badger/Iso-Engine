package exosoft.iso;

import java.awt.Rectangle;

public class Avatar extends Sprite implements ObjectPhysics, Controllable {

	public Avatar(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle((int) getIntxPosition(), getIntyPosition(), spriteWidth, spriteHeight);
	}

	public synchronized void collision() {
		double nextyPosition = Math.round(getyPosition() + getyVelocity());
		Rectangle newBounds = getBounds();
		newBounds.setLocation(getIntxPosition(), (int) nextyPosition);
		newBounds.grow(1, 1);
		if (location.checkCollision(newBounds)) {
			Rectangle intersect = location.intersection(newBounds);
			try {
				while (!intersect.isEmpty()) {
					if (newBounds.getMinX() < intersect.getMinX()) {
						setxPosition(getIntxPosition() - 1);
					}
					if (newBounds.getMaxY() > intersect.getMinY()) {
						nextyPosition-= 0.01;
						newBounds.setLocation(getIntxPosition(), (int) nextyPosition);
					}
					intersect = location.intersection(newBounds);
				}
				setyPosition((int) nextyPosition);
				setyVelocity(0);
			} catch (NullPointerException e) {
				System.err.println("Collision had some sort of nullpointerexception but it's fine i swear");
			}
		}
		bounds.setLocation(getIntxPosition(), getIntyPosition());
	}

	public synchronized void physics() {
		setyPosition(getyPosition() + getyVelocity());
		setyVelocity(getyVelocity() + gravity);
	}

	@Override
	public void visual() {

	}

	@Override
	public void movement() {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveLeft(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveUp(double multiplier) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveDown(double multiplier) {
		// TODO Auto-generated method stub

	}
}