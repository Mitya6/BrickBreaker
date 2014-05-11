package brickBreaker;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class HighscoreView extends View {

	public HighscoreView(BrickBreaker bb, boolean deserialized) {
		super(bb, deserialized);

		paintListener = new HighscoreViewPaintListener();

		bb.canvas.addPaintListener(paintListener);
	}
	
	private ArrayList<Score> getHighscores() {
		ArrayList<Score> highscores = new ArrayList<Score>();
		
		String host = "localhost";
		try {
		    Registry registry = LocateRegistry.getRegistry(host);
		    HighscoreManager stub = (HighscoreManager) registry.lookup("regEntry_HighscoreManager");		    
		    highscores = stub.getScores();
		} catch (Exception e) {
			highscores = null;
		}
		
		return highscores;
	}
	
	private static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	@Override
	public void initGameObjects() {
		
		gameObjects.add(new GameString(bb, this, new Point(140, 50), "Highscore", 50));	
						
		ArrayList<Score> highscores = getHighscores();
		if (highscores == null) {
			
			gameObjects.add(new GameString(bb, this, new Point(100, 150 + 35), "Server unreachable.", 20));
			return;
		}		
		
		
		for (int i = 0; i < highscores.size(); i++) {
			
			String s = String.format("%s%s%s", 
					padRight(highscores.get(i).player, 15), 
					padRight(Integer.toString(highscores.get(i).bricksRemaining), 15),
					convertTime(highscores.get(i).timeMillis));
			gameObjects.add(new GameString(bb, this, new Point(100, 150 + i*35), s, 20));
		}
	}

	private String convertTime(long timeMillis) {		
		DateFormat formatter = new SimpleDateFormat("mm:ss.SSS");

		return formatter.format(timeMillis);
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

	private class HighscoreViewPaintListener implements PaintListener {

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
