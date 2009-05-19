package Shift;

import java.util.ArrayList;

import ucigame.Sprite;

public class Dimension implements java.io.Serializable
{
	private static final long serialVersionUID = 3627550956646622212L;
	Dimensions dims;
	ArrayList<Wall> walls;
	ArrayList<PickupItem> pickupItems;

	public Dimension(Dimensions dims)
	{
		this.dims = dims;
		walls = new ArrayList<Wall>();
		pickupItems = new ArrayList<PickupItem>();
	}
	
	public void render()
	{
//		for(Wall s : walls)
//		{
//			s.draw();
//		}
		for(int i = 0; i < walls.size(); i++)
		{
			walls.get(i).draw();
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
	}
}
