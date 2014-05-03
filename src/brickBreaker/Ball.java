package brickBreaker;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class Ball extends GameObject{
	
	private Speed speed;
	private boolean ballDead;
	
	public Ball(BrickBreaker bb, Position pos, double width, double height, Speed speed) {
		super(bb, pos, width, height);
		this.speed = speed;
		this.ballDead = false;
	}
	
	public void draw(GC gc) {
        Color c1 = new Color(this.bb.getShell().getDisplay(), 255, 255, 100);     
		
		gc.setBackground(c1);
        gc.fillOval((int)position.x, (int)position.y, (int)width, (int)height);
        
        c1.dispose();
	}
	
	public void control() {
		
		double vx = this.speed.vx;
		double vy = this.speed.vy;
		
		this.position.x += vx;
		this.position.y += vy;
		
		if (checkCollision(vx, vy)) {
			this.position.x += this.speed.vx;
			this.position.y += this.speed.vy;
		}		
	}

	public boolean checkCollision(double vx, double vy) {
		
		boolean resetPosition = false;
		
		// Check board
		Board board = (Board)(bb.getGameObjects().get(1));
		if (this.top() < board.top()) {
			resetPosition = true;
			this.speed.vy = -this.speed.vy;
		}
		if (this.left() < board.left()) {
			resetPosition = true;
			this.speed.vx = -this.speed.vx;
		}
		if (this.right() > board.right()) {
			resetPosition = true;
			this.speed.vx = -this.speed.vx;
		}
		
		// Check pad
		if (!ballDead) {
			Pad pad = (Pad)(bb.getGameObjects().get(2));
			
			// Ball (partially) over the pad
			if ((this.left() < pad.right() && this.left() > pad.left()) ||
					(this.right() < pad.right() && this.right() > pad.left())) {
				
				// Collision with pad
				if (this.bottom() > pad.top()) {
					
					resetPosition = true;					
					Position center = this.getCenter();
					double altitude = pad.top() - center.y;
					
					// bounce right
					if (center.x - pad.right() > altitude) {
						ballDead = true;
						this.speed.vx = -this.speed.vx;
					}
					
					// bounce left
					else if (pad.left() - center.x > altitude) {
						ballDead = true;
						this.speed.vx = -this.speed.vx;
					}
					
					// bounce up
					else {
						this.speed.vy = -this.speed.vy;
					}
				}
				
			}
		}
		
		
		
		// Check bricks

		
		if (resetPosition) {
			this.position.x -= vx;
			this.position.y -= vy;
		}
		
		return resetPosition;
	}
	
	private Position getCenter() {
		return new Position(this.position.x + this.width / 2, this.position.y + this.height / 2);
	}
}
