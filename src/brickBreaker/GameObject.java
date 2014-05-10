package brickBreaker;

import java.io.Serializable;

import org.eclipse.swt.graphics.GC;

public abstract class GameObject implements Serializable {

	protected static final long serialVersionUID = 1L;
	protected transient BrickBreaker bb;
	protected Point position;
	protected double width;
	protected double height;
	protected View screen;
	
	public GameObject() {}
	
	public GameObject(BrickBreaker bb, View screen, Point pos, double width, double height) {
		this.bb = bb;
		this.position = pos;
		this.width = width;
		this.height = height;
		this.screen = screen;
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
	
	public Point getCenter() {
		return new Point(this.position.x + this.width / 2, this.position.y
				+ this.height / 2);
	}
	
	public void setBrickBreaker(BrickBreaker bb) {
		this.bb = bb;
	}
}
