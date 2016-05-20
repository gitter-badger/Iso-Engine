package exosoft.iso;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import exosoft.iso.Entity;
import exosoft.iso.Object;

/**
 * The game world consists of objects (such as floors, platforms and walls) and
 * entities (such as the player).
 */
public class Environment {
	List<Object> objects = new ArrayList<Object>();
	List<Entity> entities = new ArrayList<Entity>();
	public double gravity = 0.2;
	private boolean devmode;

	public void spawnEntity(Entity e, double x, double y) {
		entities.add(e);
		e.gravity = this.gravity;
		e.setLocation(x, y);
	}

	public void addObject(Object o) {
		objects.add(o);
	}

	public void createObject(BufferedImage texture, Point... vertices) {
		objects.add(new Object(texture, vertices));
	}

	public void execute() {
		for (Entity entity : entities) {
			entity.movement();
			entity.physics();
			for (Object object : objects) {
				if (entity.getBounds().intersects(object.getBounds())) {
					entity.collide(object.getBounds());
				}
			}
		}
	}
	
	public void visual() {
		for (Object object : objects) {
		}
		for (Entity entity : entities) {
			entity.visual();
		}	
	}

	public Object[] getObjects() {
		Object[] objectArray = new Object[this.objects.size()];
		int index = 0;
		for (Object object : objects) {
			objectArray[index] = object;
			index++;
		}
		return objectArray;
	}

	public Graphics2D drawWorld(Graphics2D g) {
		for (Object object : objects) {
			g.drawImage(object.getTexturedObject(), object.getBounds().x, object.getBounds().y, null);
			if (devmode) {
				g.setColor(Color.blue);
				g.draw(object.getBounds());
			}
		}
		for (Entity entity : entities) {
			g.drawImage(entity.getSprite(entity.getSpriteNum()), entity.getIntX(), entity.getIntY(), null);
			if (devmode) {
				g.setColor(Color.red);
				g.draw(entity.getBounds());
			}
		}
		return g;
	}

	public Entity[] getEntities() {
		Entity[] entityArray = new Entity[this.entities.size()];
		int index = 0;
		for (Entity entity : entities) {
			entityArray[index] = entity;
			index++;
		}
		return entityArray;
	}

	public boolean isDevmode() {
		return devmode;
	}

	public void setDevmode(boolean devmode) {
		this.devmode = devmode;
	}
}
