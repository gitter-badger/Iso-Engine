package exosoft.iso;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import exosoft.iso.Environment;

import javax.imageio.ImageIO;

public abstract class Sprite extends Environment {
	BufferedImage spriteSheet = null;
	private BufferedImage[] sprites;
	public int spriteWidth = 0;
	public int spriteHeight = 0;
	protected double x;
	protected double y;
	protected double velocity;
	private int spriteNum;
	protected Rectangle2D bounds;
	Environment location;

	public enum SheetType {
		SINGLE, HORIZONTAL, VERTICAL, RECTANGULAR
	}

	public Sprite(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		this.spriteHeight = spriteHeight;
		this.spriteWidth = spriteWidth;
		try {
			spriteSheet = ImageIO.read(new File(sheetPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch (type) {
		case HORIZONTAL:
			sprites = new BufferedImage[spriteSheet.getWidth() / spriteWidth];
			for (int i = 0; i < spriteSheet.getWidth() / spriteWidth; i++) {
				sprites[i] = spriteSheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
			}
			break;
		case RECTANGULAR:
			break;
		case VERTICAL:
			sprites = new BufferedImage[spriteSheet.getHeight() / spriteHeight];
			for (int i = 0; i < spriteSheet.getHeight() / spriteHeight; i++) {
				sprites[i] = spriteSheet.getSubimage(0, i * spriteHeight, spriteWidth, spriteHeight);
			}
			break;
		default:
			break;
		}
	}

	public Sprite() {

	}

	public void spawn(Environment location) {
		this.location = location;
	}
	
	public abstract void visual();

	public Rectangle2D getBounds2D() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public BufferedImage getSprite(int index) {
		return sprites[index];
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

	public void setVelocity(double yVelocity) {
		this.velocity = yVelocity;
	}

	public int getSpriteNum() {
		return spriteNum;
	}

	public void setSpriteNum(int spriteNum) {
		this.spriteNum = spriteNum;
	}
}