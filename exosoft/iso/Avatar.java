package exosoft.iso;

import java.awt.Rectangle;
import exosoft.iso.Sprite;

public class Avatar extends Sprite implements ObjectPhysics, Controllable  {
	Rectangle bounds;

	public Avatar(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
	}

	public void collision() {
		if (checkCollision(bounds)) {
			
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