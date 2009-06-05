package Shift;

import java.util.ArrayList;

import ucigame.Sprite;

public class Dimension implements java.io.Serializable
{
	Dimensions dims;
	Sprite background;
	Backgrounds bkgType;
	ArrayList<Wall> walls;
	ArrayList<PickupItem> pickupItems;
	ArrayList<SentryGun> enemies;
	ArrayList<Door> doors;

	public Dimension(Dimensions dims)
	{
		this.dims = dims;
		walls = new ArrayList<Wall>();
		pickupItems = new ArrayList<PickupItem>();
		enemies = new ArrayList<SentryGun>();
		doors = new ArrayList<Door>();
	}
	
	public static Dimensions getDims(int forNum)
	{
		switch(forNum)
		{
		case 0: return Dimensions.DIM0;
		case 1: return Dimensions.DIM1;
		case 2: return Dimensions.DIM2;
		case 3: return Dimensions.DIM3;
		case 4: return Dimensions.DIM4;
		case 5: return Dimensions.DIM5;
		case 6: return Dimensions.DIM6;
		case 7: return Dimensions.DIM7;
		case 8: return Dimensions.DIM8;
		default: return null;
		}
	}
	
	public void drawBkg()
	{
		if(background != null)
			background.draw();
	}
	
	public void render()
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).alive)
				enemies.get(i).draw();
			else
				enemies.remove(i);
		}
		for(int i = 0; i < walls.size(); i++)
		{
			walls.get(i).draw();
		}
		for(int i = 0; i < doors.size(); i++)
		{
			doors.get(i).draw();
		}
		for(int i = 0; i < pickupItems.size(); i++)
		{
			pickupItems.get(i).draw();
		}
	}
	
	public void addObject(LevelObject obj)
	{
		if(obj.type == ObjectType.WALL)
		{
			walls.add(((Wall)obj));
		}
		if(obj.type == ObjectType.ITEM)
		{
			pickupItems.add(((PickupItem)obj));
		}
		if(obj.type == ObjectType.ENEMY)
		{
			enemies.add(((SentryGun)obj));
		}
		if(obj.type == ObjectType.INTERACTIVE)
		{
			doors.add(((Door)obj));
		}
	}
}
