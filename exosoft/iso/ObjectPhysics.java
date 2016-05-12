package exosoft.iso;

import java.awt.geom.Rectangle2D;

public interface ObjectPhysics {
	public void collide(Rectangle2D intersect);
	public void physics();
}
