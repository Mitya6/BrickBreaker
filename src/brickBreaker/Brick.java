package brickBreaker;

import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class Brick extends GameObject {
	
	private int red;
	private int green;
	private int blue;
	
	public static final int defWidth = 60;
	public static final int defHeight = 25;

	public Brick(BrickBreaker bb, Position pos, double width, double height) {
		super(bb, pos, width, height);

		this.red = getRandomInt();
		this.green = getRandomInt();
		this.blue = getRandomInt();
	}
	
	public Brick(BrickBreaker bb, Position pos) {
		super(bb, pos, defWidth, defHeight);

		this.red = getRandomInt();
		this.green = getRandomInt();
		this.blue = getRandomInt();
	}

	public int getRandomInt() {
		Random r = new Random();
		int i = Math.abs(r.nextInt()) % 255;
		return i;
	}

	@Override
	public void draw(GC gc) {
		Color c1 = new Color(this.bb.getShell().getDisplay(), this.red,
				this.green, this.blue);

		gc.setBackground(c1);
		gc.fillRectangle((int) position.x, (int) position.y, (int) width,
				(int) height);

		c1.dispose();
	}

	@Override
	public void control() {
		// TODO Auto-generated method stub

	}
}
