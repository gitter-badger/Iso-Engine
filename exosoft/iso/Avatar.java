package exosoft.iso;

import java.awt.Rectangle;
import exosoft.iso.Sprite;

public class Avatar extends Sprite implements ObjPhys {
	Rectangle bounds;
	private double xPos = 60;
	private double yPos = 60;
	private double yVel = 0;

	public Avatar(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		super(type, sheetPath, spriteWidth, spriteHeight);
	}

	public int getxPos() {
		return (int) Math.round(xPos);
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return (int) Math.round(yPos);
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	public int getSpriteNum() {
		return spriteNum;
	}

	public void setSpriteNum(int spriteNum) {
		this.spriteNum = spriteNum;
	}

	public synchronized void visualLogic() {
		// TODO Add visual logic you lazy fuck
	}

	@Override
	public void collisionLogic() {
		// TODO Add collision logic you lazy fuck
	}

	@Override
	public void ambientForces() {
		setyPos(getyPos() + yVel);
		setyVel(getyVel() + 1);
	}
}

