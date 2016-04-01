package exosoft.iso;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Sprite {
	BufferedImage spriteSheet = null;
	private BufferedImage[] sprites;
	public int spriteWidth = 0;
	public int spriteHeight = 0;
	private double xPosition;
	private double yPosition;
	private double yVelocity;
	public int spriteNum;
	
	public enum SheetType {
		SINGLE,
		HORIZONTAL,
		VERTICAL,
		RECTANGULAR
	}
	public Sprite(SheetType type, String sheetPath, int spriteWidth, int spriteHeight) {
		try {
			spriteSheet = ImageIO.read(new File(sheetPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch(type) {
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
	
	public BufferedImage getSprite(int index) {
		return sprites[index];
	}

	public double getxPosition() {
		return xPosition;
	}
	
	public int getIntxPosition() {
		return (int) xPosition;
	}

	public void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getyPosition() {
		return yPosition;
	}
	
	public int getIntyPosition() {
		return (int) yPosition;
	}

	public void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public void visual() {
		// TODO Literally just a required supertype
	}
	
}