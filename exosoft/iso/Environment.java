package exosoft.iso;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	List<Shape> objects = new ArrayList<Shape>();
	public void addShape(Shape s) {
		objects.add(s);
	}
	public boolean checkCollision(Rectangle bounds) {
		for (Shape object : objects) {
			if (object.intersects(bounds)) {
				return true;
			}
		}
		return false;
	}
	
	public Rectangle intersection(Rectangle bounds) {
		for (Shape object : objects) {
			if (object.getBounds().intersects(bounds)) {
				return bounds.intersection(object.getBounds());
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