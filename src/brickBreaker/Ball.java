package brickBreaker;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class Ball extends GameObject {

	private Speed speed;
	private boolean lastCollisionWithPad = false;
	private static Logger collisionLogger = null;

	public Ball(BrickBreaker bb, View screen, Point pos, double width, double height,
			Speed speed) {
		super(bb, screen, pos, width, height);
		this.speed = speed;
	}

	public void draw(GC gc) {
		Color c1 = new Color(this.bb.getShell().getDisplay(), 255, 255, 100);

		gc.setBackground(c1);
		gc.fillOval((int) position.x, (int) position.y, (int) width,
				(int) height);

		c1.dispose();
	}

	public void control() {
		
		if (this.top() > bb.getShell().getClientArea().height) {
			bb.endGame();
		}

		double vx = this.speed.vX();
		double vy = this.speed.vY();

		this.position.x += vx;
		this.position.y += vy;

		if (checkCollision(vx, vy)) {
			this.position.x += this.speed.vX();
			this.position.y += this.speed.vY();
		}
	}

	public boolean checkCollision(double vx, double vy) {

		boolean resetPosition = false;
		
		// Set up the collisionLogger
		if (collisionLogger == null) {
			collisionLogger = Logger.getRootLogger();
			
			FileAppender appender = null;
			try {
				appender = new FileAppender(new PatternLayout("%d{HH:mm:ss} - %p - collision: %m%n"), ".\\log\\collisionLog.log", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (appender != null) {
				collisionLogger.addAppender(appender);
			}
		}
		
		

		// Check board
		Board board = (Board) (screen.getGameObjects().get(1));
		if (this.top() < board.top()) {
			resetPosition = true;
			this.speed.negateY();
			lastCollisionWithPad = false;
			
			collisionLogger.info("board top");
		}
		if (this.left() < board.left()) {
			resetPosition = true;
			this.speed.negateX();
			lastCollisionWithPad = false;
			
			collisionLogger.info("board left");
		}
		if (this.right() > board.right()) {
			resetPosition = true;
			this.speed.negateX();
			lastCollisionWithPad = false;
			
			collisionLogger.info("board right");
		}

		// Check pad
		if (!lastCollisionWithPad) {
			Pad pad = (Pad) (screen.getGameObjects().get(2));
			if (rectCollide(pad)) {
				resetPosition = true;
				lastCollisionWithPad = true;
				
				collisionLogger.info("pad");
			}
		}

		// Check bricks
		Brick brick = null;
		double minDist = Double.MAX_VALUE;
		
		// Find closest brick
		for (int i = 7; i < screen.getGameObjects().size(); i++) {
			Brick b = (Brick) (screen.getGameObjects().get(i));
			if (this.getCenter().distance(b.getCenter()) < minDist) {
				
				minDist = this.getCenter().distance(b.getCenter());
				brick = b;
			}
			
		}
		
		if (brick != null && rectCollide(brick)) {
			resetPosition = true;
			((PlayView)screen).getBricksToRemove().add(brick);
			lastCollisionWithPad = false;
			
			collisionLogger.info(String.format("brick (%.2f ; %.2f)", brick.left(), brick.top()));
		}

		if (resetPosition) {
			this.position.x -= vx;
			this.position.y -= vy;
		}

		return resetPosition;
	}

	private boolean rectCollide(GameObject go) {

		boolean resetPosition = false;

		// Ball (partially) over the GameObject
		if ((this.left() < go.right() && this.left() > go.left())
				|| (this.right() < go.right() && this.right() > go.left())) {

			// Collision with GameObject
			if ((this.top() < go.bottom() && this.top() > go.top())
					|| (this.bottom() > go.top() && this.bottom() < go.bottom())
					|| (this.bottom() > go.bottom() && this.top() < go.top())) {

				resetPosition = true;
				Point center = this.getCenter();

				boolean topleftAbove = false;
				boolean bottomleftAbove = false;
				boolean bottomrightAbove = false;
				boolean toprightAbove = false;

				// y-y0 = m(x-x0) => y =  x-x0 + y0
				// 				  => y = -x+x0 + y0

				// topleft
				if (center.x - go.left() + go.top() > center.y)
					topleftAbove = true;

				// bottomleft
				if (-center.x + go.left() + go.bottom() > center.y)
					bottomleftAbove = true;

				// bottomright
				if (center.x - go.right() + go.bottom() > center.y)
					bottomrightAbove = true;

				// topright
				if (-center.x + go.right() + go.top() > center.y)
					toprightAbove = true;

				if ((topleftAbove && toprightAbove)
						|| (!bottomleftAbove && !bottomrightAbove))
					this.speed.negateY();

				else if ((bottomleftAbove && !topleftAbove)
						|| (bottomrightAbove && !toprightAbove))
					this.speed.negateX();
			}

		}

		return resetPosition;
	}
}
