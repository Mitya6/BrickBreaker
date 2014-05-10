package brickBreaker;

import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;

public class Board extends GameObject {	

	private ArrayList<GameObject> gameObjects;
	

	public Board(BrickBreaker bb, View screen, Point pos, double width, double height) {
		super(bb, screen, pos, width, height);
		
		this.gameObjects = new ArrayList<GameObject>();
	}
	
	@Override
	public void draw(GC gc) { }

	@Override
	public void control() {	}
	
	public GameObject get(int idx) {
		return this.gameObjects.get(idx);
	}
	
	public void add(GameObject go) {
		this.gameObjects.add(go);
	}
}
