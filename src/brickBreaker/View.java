package brickBreaker;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;

public abstract class View {

	protected ArrayList<GameObject> gameObjects;
	protected final BrickBreaker bb;
	protected PaintListener paintListener;

	public View(BrickBreaker bb) {
		gameObjects = new ArrayList<GameObject>();
		this.bb = bb;	
		
		initGameObjects();
	}

	public abstract void initGameObjects();

	public abstract void render(GC gcImage);

	public abstract void keyPressed(int keyCode);

	public abstract void keyReleased(int keyCode);

	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void release() {
		bb.canvas.removePaintListener(paintListener);
	}
}
