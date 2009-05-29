package Shift;

import ucigame.Sprite;

public class LevelEditor 
{
	
	boolean snapToGrid;
	boolean grabObject;
	int gridSize;
	
	public LevelEditor()
	{
		snapToGrid = true;
		grabObject = false;
		gridSize = 5;
	}
	
	//TODO optimize these searches so I don't have to keep adding things everytime something new gets added to a level
	public LevelObject findObject(Sprite toFind, Level objectIn)
	{
		for(Wall w : objectIn.dimensions.get(0).walls)
		{
			if(equivalent(w, toFind))
			{
				return w;
			}
		}
		for(Wall w : objectIn.dimensions.get(objectIn.currDimension).walls)
		{
			if(equivalent(w, toFind))
			{
				return w;
			}
		}
		for(PickupItem p : objectIn.dimensions.get(0).pickupItems)
		{
			if(equivalent(p, toFind))
			{
				return p;
			}
		}
		for(PickupItem p : objectIn.dimensions.get(objectIn.currDimension).pickupItems)
		{
			if(equivalent(p, toFind))
			{
				return p;
			}
		}
		for(SentryGun g : objectIn.dimensions.get(0).enemies)
		{
			if(equivalent(g, toFind) || equivalent(g.top, toFind))
			{
				return g;
			}
		}
		for(SentryGun g : objectIn.dimensions.get(objectIn.currDimension).enemies)
		{
			if(equivalent(g, toFind) || equivalent(g.top, toFind))
			{
				return g;
			}
		}
		return null;
	}
	
	private boolean equivalent(LevelObject obj, Sprite spr)
	{
		return obj.x() == spr.x() && obj.y() == spr.y() && obj.width() == spr.width() && obj.height() == spr.height();
	}
	
	private boolean equivalent(Sprite obj, Sprite spr)
	{
		return obj.x() == spr.x() && obj.y() == spr.y() && obj.width() == spr.width() && obj.height() == spr.height();
	}
	
	public void removeObject(LevelObject toRemove, Level objectIn)
	{
		for(int i = 0; i < objectIn.dimensions.get(0).walls.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(0).walls.get(i)))
			{
				objectIn.dimensions.get(0).walls.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(objectIn.currDimension).walls.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(objectIn.currDimension).walls.get(i)))
			{
				objectIn.dimensions.get(objectIn.currDimension).walls.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(0).pickupItems.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(0).pickupItems.get(i)))
			{
				objectIn.dimensions.get(0).pickupItems.remove(i);
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(objectIn.currDimension).pickupItems.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(objectIn.currDimension).pickupItems.get(i)))
			{
				objectIn.dimensions.get(objectIn.currDimension).pickupItems.remove(i);
			}
		}
	}
}
