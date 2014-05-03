package brickBreaker;

import java.util.Random;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public class Brick {
	public void draw(GC gc, Display display) {		
        Color c1 = new Color(display, getRandomInt(), getRandomInt(), getRandomInt());     
		
		gc.setBackground(c1);
        gc.fillRectangle(getRandomInt(), getRandomInt(), 90, 60);
        
        c1.dispose();
	}
	
	public int getRandomInt() {
		Random r = new Random();
		int i = Math.abs(r.nextInt()) % 255;
		return i;
	}
}
