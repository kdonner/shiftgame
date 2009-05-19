package Shift;

import ucigame.Sprite;

public class LevelEditor 
{
	
	boolean snapToGrid;
	boolean grabObject;
	int gridSize;
	EditorToolBox toolBox;
	
	public LevelEditor()
	{
		snapToGrid = true;
		grabObject = false;
		gridSize = 5;
		
		toolBox = new EditorToolBox();
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
		return null;
	}
	
	private boolean equivalent(LevelObject obj, Sprite spr)
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
