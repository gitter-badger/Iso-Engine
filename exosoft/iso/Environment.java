package exosoft.iso;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The game world consists of objects (such as floors, platforms and walls) and
 * entities (such as the player).
 */
public class Environment {
	List<Object> objects = new ArrayList<Object>();
	List<Entity> entities = new ArrayList<Entity>();
	private double gravity = 0.3;
	private boolean devmode;
	private boolean antialiasmode;

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
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Object object : objects) {
			g.drawImage(object.getTexturedObject(), object.getBounds().x, object.getBounds().y, null);
			if (devmode) {
				g.setColor(Color.blue);
				g.draw(object.getBounds());
			}
		}
		for (Entity entity : entities) {
			g.drawImage(entity.activeSprite, entity.getIntX(), entity.getIntY(), null);
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

	public boolean isAntialiasmode() {
		return antialiasmode;
	}

	public void setAntialiasmode(boolean antialiasmode) {
		this.antialiasmode = antialiasmode;
		for (Object object : objects) {
			object.setAntialiasmode(antialiasmode);
		}
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
		for (Entity e : entities) {
			e.gravity = gravity;
		}
	}
}
