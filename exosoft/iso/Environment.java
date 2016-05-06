package exosoft.iso;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import exosoft.iso.Entity;

/** The game world consists of objects (such as floors, 
* platforms and walls) and entities (such as the 
* player).
*/
public class Environment {
	List<Shape> objects = new ArrayList<Shape>();
	List<Entity> entities = new ArrayList<Entity>();
	public double gravity = 0.2;
	
	public void spawnEntity(Entity e) {
		e.spawn(this);
	    entities.add(e);
	}
	
	public void addObject(Shape s) {
		objects.add(s);
	}
	
	public void createObject(double x, double y, double w, double h) {
	    objects.add(new Rectangle2D.Double(x, y, w, h));
	}
	
	public boolean checkCollision(Rectangle2D bounds) {
		for (Shape object : objects) {
			if (object.intersects(bounds)) {
				return true;
			}
		}
		return false;
	}
	
	public Rectangle2D intersection(Rectangle2D bounds) {
		for (Shape object : objects) {
			if (object.getBounds().intersects(bounds)) {
				return bounds.createIntersection(object.getBounds());
			}
		}
		return new Rectangle2D.Double();
	}
	
	public Shape[] getObjects() {
		Shape[] objectArray = new Shape[this.objects.size()];
		int index = 0;
		for (Shape object : objects) {
			objectArray[index] = object;
			index++;
		}
		return objectArray;
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
	
	enum objectType {
		INTERACTIVE, DYNAMIC, STATIC
	}
	
	@SuppressWarnings("serial")
	class Object extends Polygon {
		
	}
}