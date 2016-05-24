package princeton.calc;

public class Projection {
	protected double min;
	protected double max;

	public Projection(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public boolean overlap(Projection p2) {
		boolean temp = true;

		if (min > p2.max || max < p2.min)
			temp = false;

		return temp;
	}
}