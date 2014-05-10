package brickBreaker;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class Pad extends GameObject {

	private Speed speed;
	private int vertDir;
	private int horDir;

	public Pad(BrickBreaker bb, View screen, Point pos, double width, double height,
			Speed speed) {
		super(bb, screen, pos, width, height);
		this.speed = speed;
		this.vertDir = 0;
		this.horDir = 0;
	}

	@Override
	public void draw(GC gc) {
		Color c1 = new Color(this.bb.getShell().getDisplay(), 255, 0, 0);

		gc.setBackground(c1);
		gc.fillRectangle((int) position.x, (int) position.y, (int) width,
				(int) height);

		c1.dispose();
	}

	@Override
	public void control() {
		if ((horDir > 0 && this.right() < screen.getGameObjects().get(1).right()) || 
				(horDir < 0 && this.left() > screen.getGameObjects().get(1).left())) {
		this.position.x += horDir * this.speed.vX();
		}
		
		if ((vertDir > 0 && this.bottom() < 510) || 
				(vertDir < 0 && this.top() > 350)) {
		this.position.y += vertDir * this.speed.vY();
		}
	}

	public void setVertDir(int dir) {
		if (dir == 1)
			vertDir = 1;
		if (dir == -1)
			vertDir = -1;
		if (dir == 0)
			vertDir = 0;
	}
	
	public void setHorDir(int dir) {
		if (dir == 1)
			horDir = 1;
		if (dir == -1)
			horDir = -1;
		if (dir == 0)
			horDir = 0;
	}

}
