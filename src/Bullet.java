import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Bullet extends MovingObject
{
	double damage;
	int distanceTraveled;
	boolean exploded;
	boolean ready;
	int accel;
	int speed;

	Bullet(String name)
	{
		super(name);
		x = -200;
		y = -200;
		damage = 2;
		exploded = false;
		ready = true;
		distanceTraveled = 0;

		accel = 1;
		speed = 7;
	}

	//give the player and number of the rocket ready
	public void fire(int fromX, int fromY, String direction)
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
			picture =  new ImageIcon("../data/bullet.png");
			dy = -1 * speed;
			ddy = -1 * accel;
		}

		//right
		if(direction.contains("RIGHT"))
		{
			picture =  new ImageIcon("../data/bullet.png");
			dx = speed;
			ddx = accel;
		}

		//bottom
		if(direction.contains("DOWN"))
		{
			picture =  new ImageIcon("../data/bullet.png");
			dy = speed;
			ddy = accel;
		}

		//left
		if(direction.contains("LEFT"))
		{
			picture =  new ImageIcon("../data/bullet.png");
			dx = -1 * speed;
			ddx = -1 * accel;
		}

		ready = false;
	}

	public void explode()
	{
		System.out.println("explode");
		exploded = true;
		//explode the rocket
		picture =  new ImageIcon("../data/blank.png");

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
