package exosoft.iso;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	List<Shape> objects = new ArrayList<Shape>();
	List<Character> entities = new ArrayList<Character>();
	public double gravity = 0.2;
	
	public void spawnEntity(Character c) {
	    entities.add(c);
	}
	
	public void addObject(Shape s) {
		objects.add(s);
	}
	
	public void createObject(double x, double y, double w, double h) {
	    objects.add(new Rectangle2D.Double(x, y, w, h)));
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
	
	public Character[] getEntities() {
	    Character[] entityArray = new Character[this.entities.size()];
	    int index = 0;
	    for (Character entity : entities) {
	        entityArray[index] = object;
	        index++;
	    }
	    return entityArray;
	}
	
	enum objectType {
		INTERACTIVE, DYNAMIC, STATIC
	}
	
	class Object extends Shape {
		
	}
}