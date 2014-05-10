package brickBreaker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;


public class MainMenuView extends View {

	public MainMenuView(final BrickBreaker bb, boolean deserialized) {
		super(bb, deserialized);
		
		paintListener = new MainMenuViewPaintListener();
		
		bb.canvas.addPaintListener(paintListener);
	}

	@Override
	public void initGameObjects() {
		gameObjects.add(new GameString(bb, this, new Point(175, 50), "Main Menu", 35));
		
		// New game
		gameObjects.add(new GameString(bb, this, new Point(80, 150), "New Game", 25));
		gameObjects.add(new GameString(bb, this, new Point(350, 150), "Space", 25));
		
		// Load game
		gameObjects.add(new GameString(bb, this, new Point(80, 220), "Load Game", 25));
		gameObjects.add(new GameString(bb, this, new Point(350, 220), "L", 25));
		
		// Highscore
		gameObjects.add(new GameString(bb, this, new Point(80, 290), "Highscore", 25));
		gameObjects.add(new GameString(bb, this, new Point(350, 290), "H", 25));
		
		// Exit
		gameObjects.add(new GameString(bb, this, new Point(80, 360), "Exit", 25));
		gameObjects.add(new GameString(bb, this, new Point(350, 360), "X", 25));

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
			bb.runGame(false);
		} else if (keyCode == 'L' || keyCode == 'l') {
			bb.runGame(true);
		} else if (keyCode == 'H' || keyCode == 'h') {
			bb.goHighscore();
		} else if (keyCode == 'X' || keyCode == 'x') {
			bb.getShell().dispose();
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}
	
	private class MainMenuViewPaintListener implements PaintListener {

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
