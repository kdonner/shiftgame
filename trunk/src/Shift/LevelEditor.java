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
		LevelObject obj = null;
		for(Wall w : objectIn.walls)
		{
			if(w.x() == toFind.x() && w.y() == toFind.y())
			{
				obj = w;
				return obj;
			}
		}
		for(Wall w : objectIn.dimensions.get(objectIn.currDimension).walls)
		{
			if(w.x() == toFind.x() && w.y() == toFind.y())
			{
				obj = w;
				return obj;
			}
		}
		return obj;
	}
	
	public void removeObject(LevelObject toRemove, Level objectIn)
	{
		for(int i = 0; i < objectIn.walls.size(); i++)
		{
			if(toRemove.equals(objectIn.walls.get(i)))
			{
				objectIn.walls.remove(i);
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
	}
}
