import javax.swing.*;
import java.awt.*;

public class MovingObject extends Rectangle
{
	//object's velocity
	int dx;
	int dy;

	//acceleration..... for the texter
	double ddx;
	double ddy;

	//picture of object
	ImageIcon picture;


	MovingObject(String icon)
	{
		picture = new ImageIcon(icon);
		width = picture.getIconWidth();
		height = picture.getIconHeight();
	}
	
	MovingObject(int thex, int they, String icon)
	{
		x = thex;
		y = they;
		picture = new ImageIcon(icon);
		width = picture.getIconWidth();
		height = picture.getIconHeight();
	}

	public void setSpeed(int sx, int sy)
	{
		dx = sx;
		dy = sy;
	}

	public void setAccel(int ax, int ay)
	{
		ddx = ax;
		ddy = ay;
	}

	public void update()
	{
		x += dx;
		y += dy;

		dx += ddx;
		dy += ddy;
	}

	public void draw(Graphics g, Component c)
	{
		//System.out.println("Draw moving object");
		//g.drawImage(picture.getImage(), x% c.getWidth() ,y% c.getHeight(), picture.getIconWidth(), picture.getIconHeight(), c);
		g.drawImage(picture.getImage(), x ,y, picture.getIconWidth(), picture.getIconHeight(), c);
	}

}