package brickBreaker;

import org.eclipse.swt.graphics.GC;

public abstract class GameObject {

	protected BrickBreaker bb;
	protected Position position;
	protected double width;
	protected double height;
	
	public GameObject() {}
	
	public GameObject(BrickBreaker bb, Position pos, double width, double height) {
		this.bb = bb;
		this.position = pos;
		this.width = width;
		this.height = height;
	}
	
	public abstract void draw(GC gc);
	
	public abstract void control();
	
	public double top() {
		return position.y;
	}
	
	public double bottom() {
		return position.y + height;
	}
	
	public double left() {
		return position.x;
	}
	
	public double right() {
		return position.x + width;
	}
}
