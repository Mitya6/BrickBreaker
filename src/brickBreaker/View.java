package brickBreaker;

import java.io.Serializable;
import java.util.ArrayList;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;

public abstract class View implements Serializable {

	protected static final long serialVersionUID = 1L;
	protected ArrayList<GameObject> gameObjects;
	protected transient BrickBreaker bb;
	protected transient PaintListener paintListener;

	public View(BrickBreaker bb, boolean deserialized) {
		this.bb = bb;

		if (!deserialized) {
			gameObjects = new ArrayList<GameObject>();
			initGameObjects();
		}
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
