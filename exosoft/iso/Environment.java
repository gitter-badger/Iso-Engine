package exosoft.iso;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Environment {
	List<Rectangle> objects = new ArrayList<Rectangle>();
	public void addShape(Rectangle r) {
		objects.add(r);
	}
	public Rectangle[] getObjects() {
		Rectangle[] objects = new Rectangle[this.objects.size()];
		for (int index = 0; index < objects.length; index++) {
			objects[index] = this.objects.get(index);
		}
		return objects;
	}
	enum objectType {
		
	}
	class Object {
		
	}
}