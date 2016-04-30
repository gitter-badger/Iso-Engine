package exosoft.iso;

import java.awt.Rectangle;

public class Avatar extends Sprite implements ObjectPhysics, Controllable {

	public Avatar(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle((int) getIntxPosition(), getIntyPosition(), spriteWidth, spriteHeight);
	}

	public void collision() {
		double nextyPosition = getyPosition() + gravity;
		Rectangle newBounds = getBounds();
		newBounds.setLocation(getIntxPosition(), (int) nextyPosition);
		if (location.checkCollision(newBounds)) {
			Rectangle intersect = location.intersection(newBounds);
			try {
				while (!intersect.isEmpty()) {
					if (newBounds.getMinX() < intersect.getMinX()) {
						setxPosition(getIntxPosition() - 1);
					}
					if (newBounds.getMaxY() > intersect.getMinY()) {
						nextyPosition--;
						newBounds.setLocation(getIntxPosition(), (int) nextyPosition);
					}
					intersect = location.intersection(newBounds);
				}
			} catch (NullPointerException e) {

			}
		}

		setyPosition(nextyPosition);
		bounds.setLocation(getIntxPosition(), getIntyPosition());
	}

	public void physics() {
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