package brickBreaker;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;

public class GameString extends GameObject {
	
	private String text;
	private int fontSize;
	
	public GameString(BrickBreaker bb, View screen, Point pos, String s, int fontSize) {
		super(bb, screen, pos, 0, 0);
		
		this.text = s;
		
		this.fontSize = fontSize;		
	}

	@Override
	public void draw(GC gc) {
		
		Color c1 = new Color(bb.getShell().getDisplay(), 0, 255, 255);				
		gc.setForeground(c1);
		c1.dispose();
		
		Color c2 = new Color(bb.getShell().getDisplay(), 0, 0, 0);				
		gc.setBackground(c2);
		c1.dispose();
		
		FontData fontData = new FontData();
		fontData.setHeight(fontSize);
		Font font = new Font(bb.getShell().getDisplay(), fontData);
		gc.setFont(font);	
		
		gc.drawString(text, (int)position.x, (int)position.y);		
		font.dispose();	
	}

	@Override
	public void control() {
		// TODO Auto-generated method stub
		
	}

}
