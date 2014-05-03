package brickBreaker;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class Pad extends GameObject {

	private Speed speed;
	private int padDir;

	public Pad(BrickBreaker bb, Position pos, double width, double height,
			Speed speed) {
		super(bb, pos, width, height);
		this.speed = speed;
		this.padDir = 0;
	}

	@Override
	public void draw(GC gc) {
		Color c1 = new Color(this.bb.getShell().getDisplay(), 100, 255, 255);

		gc.setBackground(c1);
		gc.fillRectangle((int) position.x, (int) position.y, (int) width,
				(int) height);

		c1.dispose();
	}

	@Override
	public void control() {
		if ((padDir > 0 && this.right() < bb.getGameObjects().get(1).right()) || 
				(padDir < 0 && this.left() > bb.getGameObjects().get(1).left())) {
		this.position.x += padDir * this.speed.vx;
		}
	}

	public void setPadDir(int dir) {
		if (dir == 1)
			padDir = 1;
		if (dir == -1)
			padDir = -1;
		if (dir == 0)
			padDir = 0;
	}
	
	public int getPadDir() {
		return this.padDir;
	}

}
