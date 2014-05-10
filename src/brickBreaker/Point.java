package brickBreaker;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;
	public double x;
	public double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double distance(Point other) {
		return Math.sqrt((this.x-other.x)*(this.x-other.x) + (this.y-other.y)*(this.y-other.y));
	}
}
