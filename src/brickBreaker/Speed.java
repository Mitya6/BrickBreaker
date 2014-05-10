package brickBreaker;

import java.io.Serializable;


public class Speed implements Serializable {

	private static final long serialVersionUID = 1L;
	private double vx;
	private double vy;
	private double magnitude;
	
	public Speed(double magnitude) {
		this.magnitude = magnitude;
		this.vx = this.vy = magnitude / Math.sqrt(2);
	}

	public Speed(double magnitude, double vx) {
		this.magnitude = magnitude;
		if (Math.abs(vy) <= magnitude) {
			this.vx = vx;
			this.vy = Math.sqrt(magnitude*magnitude - vx*vx);
		}
		else
			this.vx = this.vy = magnitude / Math.sqrt(2);
	}
	
	public double vX() {
		return this.vx;
	}
	
	public double vY() {
		return this.vy;
	}
	
	public void negateX() {
		this.vx = -this.vx;
	}
	
	public void negateY() {
		this.vy = -this.vy;
	}
}
