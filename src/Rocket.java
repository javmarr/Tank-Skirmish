import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Rocket extends MovingObject
{
	double damage;
	int distanceTraveled;
	boolean shouldExplode;
	boolean exploded;
	boolean ready;
	int accel;
	int speed;
	
	ImageIcon explosionPic;
	
	Rocket(String name)
	{
		super(name);
		x = -200;
		y = -200;
		damage = 10;
		exploded = false;
		shouldExplode = false;
		ready = true;
		distanceTraveled = 0;
		
		explosionPic = new ImageIcon("data/rocket_explosion.gif");
		
		accel = 1;
		speed = 7;
	}
	
	public void draw(Graphics g, Component c)
	{
		//draw the rocket
		super.draw(g, c);
		
		if(shouldExplode)
		{
			System.out.println("BOOM@");
			shouldExplode = false;
			exploded = true;
			//draw the explosion if it supposed to
			g.drawImage(explosionPic.getImage(), x ,y, explosionPic.getIconWidth(), explosionPic.getIconHeight(), c);
	
		}
	}
	//give the player and number of the rocket ready
	public void launch(int fromX, int fromY, String direction)
	{
		//set position
		x = fromX;
		y = fromY;
		
		//set direction
		//top
		dx = 0;
		dy = 0;
		ddx = 0;
		ddy = 0;
		
		if(direction.contains("UP"))
		{
			picture =  new ImageIcon("data/rocket1UP.png");
			dy = -1 * speed;
			ddy = -1 * accel;
		}
		
		//right
		if(direction.contains("RIGHT"))
		{
			picture =  new ImageIcon("data/rocket1RIGHT.png");
			dx = speed;
			ddx = accel;
		}
		
		//bottom
		if(direction.contains("DOWN"))
		{
			picture =  new ImageIcon("data/rocket1DOWN.png");
			dy = speed;
			ddy = accel;
		}
		
		//left
		if(direction.contains("LEFT"))
		{
			picture =  new ImageIcon("data/rocket1LEFT.png");
			dx = -1 * speed;
			ddx = -1 * accel;
		}
				
		ready = false;
	}
	
	public void explode()
	{
		System.out.println("explode");
		shouldExplode = true;
		
		explosionPic = new ImageIcon("data/rocket_explosion.gif");
		explosionPic.getImage().flush();
		
		//explode the rocket
		picture =  new ImageIcon("data/blank.png");
		
		//refill player's rocket count
		ready = true;
	}
	
	public void update()
	{
		super.update(); //update the posititon,speed,etc
		
		//update the distance traveled
		if(!ready)
			distanceTraveled += Math.abs(dx + dy); // on both the x and y axis
		else
			distanceTraveled = 0;
		//System.out.println("Distance = " + distanceTraveled);
		
	
	}

}