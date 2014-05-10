package brickBreaker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BrickBreaker {

	private static Shell shell;
	private static final int TIMER_INTERVAL = 10;
	private Display display;
	private GameState state;
	private Runnable runnable;
	public Canvas canvas;

	private View screen;

	public static void main(String[] args) {
		final BrickBreaker bb = new BrickBreaker();

		while (!shell.isDisposed()) {
			if (!bb.display.readAndDispatch()) {
				bb.display.sleep();
			}
		}

		bb.release();
	}

	public void runGame() {

		if (state == GameState.MAINMENU) {
			screen.release();
			screen = new PlayView(this);
		}

		state = GameState.RUNNING;

		runnable = new Runnable() {
			public void run() {
				if (!shell.isDisposed()) {
					((PlayView) screen).SimulateWorld();
					display.timerExec(TIMER_INTERVAL, this);
					
					if (((PlayView)screen).getRemainingBricks() == 0) {
						endGame();
					}
				}
			}
		};
		display.timerExec(TIMER_INTERVAL, runnable);
	}

	public void pauseGame() {
		state = GameState.PAUSED;

		display.timerExec(-1, runnable);
		
		canvas.redraw();
	}
	
	public void endGame() {
		state = GameState.ENDED;
		
		display.timerExec(-1, runnable);
		
		int remainingBricks = ((PlayView)screen).getRemainingBricks();
		
		screen.release();
		screen = new EndView(this, remainingBricks == 0);
		
		canvas.redraw();
	}
	
	public void goMainMenu() {
		state = GameState.MAINMENU;
		
		if (screen != null) {
			screen.release();
		}
		screen = new MainMenuView(this);
		
		canvas.redraw();
	}
	
	public void goHighscore() {
		state = GameState.HIGHSCORE;
		
		screen.release();
		screen = new HighscoreView(this);
		
		canvas.redraw();
	}

	public BrickBreaker() {

		state = GameState.MAINMENU;

		display = new Display();

		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setText("BrickBreaker");
		shell.setSize(600, 600);
		shell.setLocation(600, 200);
		shell.setLayout(new FillLayout());

		shell.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				screen.keyReleased(arg0.keyCode);
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				screen.keyPressed(arg0.keyCode);
			}
		});

		Color c1 = new Color(display, 0, 0, 0);

		canvas = new Canvas(shell, SWT.NO_BACKGROUND);
		canvas.setBackground(c1);
		c1.dispose();

		shell.open();
		
		goMainMenu();
	}

	public Shell getShell() {
		return shell;
	}

	public GameState getState() {
		return this.state;
	}

	private void release() {
		if (!shell.isDisposed()) {
			screen.release();
		}
		display.dispose();
	}
}
