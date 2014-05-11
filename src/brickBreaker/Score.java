package brickBreaker;

import java.io.Serializable;

public class Score implements Serializable {
	public String player;
	public int bricksRemaining;
	public long timeMillis;
	
	public Score(String player, int bricksRemaining, long timeMillis) {
		this.player = player;
		this.bricksRemaining = bricksRemaining;
		this.timeMillis = timeMillis;
	}
}
