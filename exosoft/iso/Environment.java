package exosoft.iso;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	List<Shape> objects = new ArrayList<Shape>();
	public double gravity = 0.2;
	public void addShape(Shape s) {
		objects.add(s);
	}
	public boolean checkCollision(Rectangle2D newBounds) {
		for (Shape object : objects) {
			if (object.intersects(newBounds)) {
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
		return new Rectangle();
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
	enum objectType {
		
	}
	class Object {
		
	}
}