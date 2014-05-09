package brickBreaker;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class Ball extends GameObject {

	private Speed speed;
	private boolean lastCollisionWithPad = false;

	public Ball(BrickBreaker bb, Point pos, double width, double height,
			Speed speed) {
		super(bb, pos, width, height);
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

		// Check board
		Board board = (Board) (bb.getGameObjects().get(1));
		if (this.top() < board.top()) {
			resetPosition = true;
			this.speed.negateY();
			lastCollisionWithPad = false;
		}
		if (this.left() < board.left()) {
			resetPosition = true;
			this.speed.negateX();
			lastCollisionWithPad = false;
		}
		if (this.right() > board.right()) {
			resetPosition = true;
			this.speed.negateX();
			lastCollisionWithPad = false;
		}

		// Check pad
		if (!lastCollisionWithPad) {
			Pad pad = (Pad) (bb.getGameObjects().get(2));
			if (rectCollide(pad)) {
				resetPosition = true;
				lastCollisionWithPad = true;
			}
		}

		// Check bricks
		Brick brick = null;
		double minDist = Double.MAX_VALUE;
		
		// Find closest brick
		for (int i = 3; i < this.bb.getGameObjects().size(); i++) {
			Brick b = (Brick) (bb.getGameObjects().get(i));
			if (this.getCenter().distance(b.getCenter()) < minDist) {
				
				minDist = this.getCenter().distance(b.getCenter());
				brick = b;
			}
			
		}
		
		if (brick != null && rectCollide(brick)) {
			resetPosition = true;
			bb.getBricksToRemove().add(brick);
			lastCollisionWithPad = false;
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
