package brickBreaker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class EndView extends View {

	public EndView(BrickBreaker bb, boolean win) {
		super(bb);

		paintListener = new EndViewPaintListener();

		bb.canvas.addPaintListener(paintListener);
		
		if (win) {
			gameObjects.add(new GameString(bb, this, new Point(160, 230), "You Won", 50));
		}
		else {
			gameObjects.add(new GameString(bb, this, new Point(120, 230), "Game Over", 50));
		}
	}

	@Override
	public void initGameObjects() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GC gcImage) {
		
		for (GameObject obj : gameObjects) {
			obj.draw(gcImage);
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		if (keyCode == SWT.SPACE) {
			bb.goMainMenu();
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	private class EndViewPaintListener implements PaintListener {

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
