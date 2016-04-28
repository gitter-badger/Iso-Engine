package exosoft.iso;

import java.awt.Rectangle;

public class Avatar extends Sprite implements ObjectPhysics, Controllable  {
	
	public Avatar(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
		bounds = new Rectangle((int) getxPosition(), (int) getyPosition(), spriteWidth, spriteHeight);
	}

	public void collision() {
		getBounds().setLocation(getIntxPosition(), getIntyPosition());
		if (location.checkCollision(getBounds())) {
			setyVelocity(0);
			Rectangle intersect = location.intersection(getBounds());
			if (bounds.getX() < intersect.getX() && intersect.intersects(bounds)) {
				setxPosition(getxPosition() - 1);
			}
			if (bounds.getX() > intersect.getX() && bounds.getMaxX() < intersect.getMaxX()) {
				
			}
		}
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