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
	
	public LevelObject findObject(Sprite toFind, Level objectIn)
	{
		LevelObject obj = null;
		for(Wall w : objectIn.walls)
		{
			if(w.x() == toFind.x() && w.y() == toFind.y())
			{
				obj = w;
			}
		}
		for(Wall w : objectIn.dimensions.get(objectIn.currDimension).walls)
		{
			if(w.x() == toFind.x() && w.y() == toFind.y())
			{
				obj = w;
			}
		}
		return obj;
	}
}
