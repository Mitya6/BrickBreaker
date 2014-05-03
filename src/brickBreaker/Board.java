package brickBreaker;

import org.eclipse.swt.graphics.GC;

public class Board extends GameObject {

	public Board(BrickBreaker bb, Position pos, double width, double height) {
		super(bb, pos, width, height);
	}
	
	@Override
	public void draw(GC gc) { }

	@Override
	public void control() {	}
}
