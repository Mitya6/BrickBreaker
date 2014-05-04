package brickBreaker;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BrickBreaker {

	private static Shell shell;
	private long time;
	private Canvas canvas;
	private ArrayList<GameObject> gameObjects;
	private static final int TIMER_INTERVAL = 10;
	private Display display;
	private ArrayList<Brick> bricksToRemove;

	public static void main(String[] args) {
		final BrickBreaker bb = new BrickBreaker();

		Runnable runnable = new Runnable() {
			public void run() {
				if (!shell.isDisposed()) {
					bb.SimulateWorld(0, 0);
					bb.display.timerExec(TIMER_INTERVAL, this);
				}
			}
		};
		bb.display.timerExec(TIMER_INTERVAL, runnable);

		while (!shell.isDisposed()) {
			if (!bb.display.readAndDispatch()) {
				bb.display.sleep();
			}
		}
		
		bb.release();
	}

	private void release() {
		canvas.dispose();
		display.dispose();
	}

	public BrickBreaker() {		

		display = new Display();

		gameObjects = new ArrayList<GameObject>();
		bricksToRemove = new ArrayList<Brick>();

		time = System.nanoTime();

		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setText("BrickBreaker");
		shell.setSize(600, 600);
		shell.setLocation(600, 200);
		shell.setLayout(new FillLayout());	
		
		shell.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if (arg0.keyCode == SWT.ARROW_RIGHT || arg0.keyCode == SWT.ARROW_LEFT) {
					((Pad)gameObjects.get(2)).setHorDir(0);
				}
				
				if (arg0.keyCode == SWT.ARROW_UP || arg0.keyCode == SWT.ARROW_DOWN) {
					((Pad)gameObjects.get(2)).setVertDir(0);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.keyCode == SWT.ARROW_RIGHT) {
					((Pad)gameObjects.get(2)).setHorDir(1);
				}
				if (arg0.keyCode == SWT.ARROW_LEFT) {
					((Pad)gameObjects.get(2)).setHorDir(-1);
				}
				
				if (arg0.keyCode == SWT.ARROW_UP) {
					((Pad)gameObjects.get(2)).setVertDir(-1);
				}
				if (arg0.keyCode == SWT.ARROW_DOWN) {
					((Pad)gameObjects.get(2)).setVertDir(1);
				}
			}
		});
		

		Color c1 = new Color(shell.getDisplay(), 0, 0, 0); 
		canvas = new Canvas(shell, SWT.NO_BACKGROUND);
		canvas.setBackground(c1);
		c1.dispose();

		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent event) {

				// Create the image to fill the canvas
				Image image = new Image(shell.getDisplay(), canvas.getBounds());
				
				// Set up the offscreen gc
				GC gcImage = new GC(image);

				gcImage.setBackground(event.gc.getBackground());
				gcImage.fillRectangle(image.getBounds());
				
				for (GameObject obj : gameObjects) {
					obj.draw(gcImage);
				}

				// Draw the offscreen buffer to the screen
				event.gc.drawImage(image, 0, 0);

				image.dispose();
				gcImage.dispose();
			}
		});
		
		// Add game objects here
		gameObjects.add(new Ball(this, new Position(200, 350), 25, 25, new Speed(
				3.5)));
		
		Rectangle clientArea = shell.getClientArea();
		gameObjects.add(new Board(this, new Position(0, 0), clientArea.width, clientArea.height));
		gameObjects.add(new Pad(this, new Position(250, 500), 100, 10, new Speed(7)));
		
		//gameObjects.add(new Brick(this, new Position(100, 100)));
		//gameObjects.add(new Brick(this, new Position(180, 130)));
		//gameObjects.add(new Brick(this, new Position(300, 200)));
		
		for (int i = 0; i < clientArea.width / Brick.defWidth; i++) {
			for (int j = 0; j < 8; j++) {
				gameObjects.add(new Brick(this, new Position(20 + i*Brick.defWidth, 20 + j*Brick.defHeight)));
			}
		}

		shell.open();
	}

	private void SimulateWorld(long startTime, long endTime) {

		for (GameObject obj : gameObjects) {
			obj.control();
		}
		
		for (Brick b : bricksToRemove) {
			gameObjects.remove(b);
		}
		
		canvas.redraw();
	}

	public Shell getShell() {
		return shell;
	}
	
	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public ArrayList<Brick> getBricksToRemove() {
		return bricksToRemove;
	}
}
