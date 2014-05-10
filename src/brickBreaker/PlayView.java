package brickBreaker;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public class PlayView extends View {

	private ArrayList<Brick> bricksToRemove;

	public PlayView(final BrickBreaker bb) {
		super(bb);
		bricksToRemove = new ArrayList<Brick>();

		paintListener = new PlayViewPaintListener();

		bb.canvas.addPaintListener(paintListener);
	}

	@Override
	public void initGameObjects() {

		// Ball [0]
		gameObjects.add(new Ball(bb, this, new Point(200, 350), 25, 25, new Speed(3.5)));

		// Board [1]
		Rectangle clientArea = bb.getShell().getClientArea();
		gameObjects.add(new Board(bb, this, new Point(0, 0), clientArea.width, clientArea.height));
		
		// Pad [2]
		gameObjects.add(new Pad(bb, this, new Point(250, 500), 100, 10, new Speed(7)));

		// Pause [3]
		gameObjects.add(new GameString(bb, this, new Point(50, 530), "Pause - Space", 15));
		//gameObjects.add(new GameString(bb, this, new Point(350, 520), "Space", 25));

		// Load game [4]
		gameObjects.add(new GameString(bb, this, new Point(50, 530), "Resume - Space", 15));
		//gameObjects.add(new GameString(bb, this, new Point(350, 520), "Space", 25));

		// Highscore [5]
		gameObjects.add(new GameString(bb, this, new Point(270, 530), "Save - S", 15));
		//gameObjects.add(new GameString(bb, this, new Point(350, 520), "S", 25));

		// Exit [6]
		gameObjects.add(new GameString(bb, this, new Point(440, 530), "Finish - F", 15));
		//gameObjects.add(new GameString(bb, this, new Point(350, 520), "F", 25));

		// Bricks
		for (int i = 0; i < (clientArea.width - 50) / Brick.defWidth; i++) {
			for (int j = 0; j < 6; j++) {
				gameObjects.add(new Brick(bb, this, new Point(50 + i * Brick.defWidth, 50 + j * Brick.defHeight)));
			}
		}
	}

	@Override
	public void render(GC gcImage) {

		for (int i = 0; i < gameObjects.size(); i++) {
			
			if (i == 3 && bb.getState() == GameState.PAUSED) {
				continue;
			}
			if (i >= 4 && i <= 6 && bb.getState() == GameState.RUNNING) {
				continue;
			}
			
			gameObjects.get(i).draw(gcImage);
		}
	}

	public void SimulateWorld() {

		for (GameObject obj : gameObjects) {
			obj.control();
		}

		for (Brick b : bricksToRemove) {
			gameObjects.remove(b);
		}
		bricksToRemove.clear();

		bb.canvas.redraw();
	}

	@Override
	public void keyPressed(int keyCode) {

		// Control the pad
		if (bb.getState() == GameState.RUNNING) {
			if (keyCode == SWT.ARROW_RIGHT) {
				((Pad) gameObjects.get(2)).setHorDir(1);
			}
			if (keyCode == SWT.ARROW_LEFT) {
				((Pad) gameObjects.get(2)).setHorDir(-1);
			}

			if (keyCode == SWT.ARROW_UP) {
				((Pad) gameObjects.get(2)).setVertDir(-1);
			}
			if (keyCode == SWT.ARROW_DOWN) {
				((Pad) gameObjects.get(2)).setVertDir(1);
			}
		}

		// Pause and Resume game with space
		if (keyCode == SWT.SPACE) {
			if (bb.getState() == GameState.RUNNING) {
				bb.pauseGame();
			} else if (bb.getState() == GameState.PAUSED) {
				bb.runGame();
			}
		}
		
		// Finish if paused
		if (bb.getState() == GameState.PAUSED) {
			if (keyCode == 'F' || keyCode == 'f') {
				bb.endGame();
			}
		}
	}

	@Override
	public void keyReleased(int keyCode) {

		if (bb.getState() == GameState.RUNNING) {
			if (keyCode == SWT.ARROW_RIGHT || keyCode == SWT.ARROW_LEFT) {
				((Pad) gameObjects.get(2)).setHorDir(0);
			}

			if (keyCode == SWT.ARROW_UP || keyCode == SWT.ARROW_DOWN) {
				((Pad) gameObjects.get(2)).setVertDir(0);
			}
		}
	}

	public ArrayList<Brick> getBricksToRemove() {
		return bricksToRemove;
	}
	
	public int getRemainingBricks() {
		return gameObjects.size() - 7;
	}

	private class PlayViewPaintListener implements PaintListener {

		@Override
		public void paintControl(PaintEvent event) {
			// Create the image to fill the canvas
			Image image = new Image(bb.getShell().getDisplay(), bb.canvas.getBounds());

			// Set up the offscreen gc
			GC gcImage = new GC(image);

			gcImage.setBackground(event.gc.getBackground());
			gcImage.fillRectangle(image.getBounds());

			render(gcImage);

			// Draw the offscreen buffer to the screen
			event.gc.drawImage(image, 0, 0);

			image.dispose();
			gcImage.dispose();
		}

	}
}
