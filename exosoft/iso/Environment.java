package exosoft.iso;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	List<Rectangle> objects = new ArrayList<Rectangle>();
	public void addShape(Rectangle r) {
		objects.add(r);
	}
	public boolean checkCollision(Rectangle bounds) {
		for (Rectangle object : objects) {
			if (bounds.intersects(object)) {
				return true;
			}
		}
		return false;
		
	}
	public Rectangle[] getObjects() {
		Rectangle[] objectArray = new Rectangle[this.objects.size()];
		int index = 0;
		for (Rectangle object : objects) {
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