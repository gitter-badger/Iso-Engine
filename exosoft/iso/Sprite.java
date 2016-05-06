package exosoft.iso;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import exosoft.iso.Environment;

import javax.imageio.ImageIO;

public abstract class Sprite {
	BufferedImage spriteSheet = null;
	private BufferedImage[] sprites;
	public int spriteWidth = 0;
	public int spriteHeight = 0;
	private int spriteNum;

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
		case SINGLE:
		    sprites = spriteSheet;
		    break;
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

	
	public int getSpriteNum() {
		return spriteNum;
	}

	public void setSpriteNum(int spriteNum) {
		this.spriteNum = spriteNum;
	}
}