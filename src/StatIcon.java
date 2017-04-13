import javax.swing.*;
import java.awt.*;
import java.util.*;

public class StatIcon
{
	ImageIcon picture;
	int x; 
	int y;
	int width;
	int height;
	
	StatIcon(String loc)
	{
		picture = new ImageIcon(loc);
		width = picture.getIconWidth();
		height = picture.getIconHeight();
	}
	
	public void draw(Graphics g, Component c, int theX, int theY)
	{
		x = theX;
		y = theY;
		
		g.drawImage(picture.getImage(), x ,y, picture.getIconWidth(), picture.getIconHeight(), c);
	}
	

}