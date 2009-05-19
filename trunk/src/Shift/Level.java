package Shift;

import java.util.ArrayList;

import Shift.LevelSaveFile.DimensionSave;
import Shift.LevelSaveFile.DimensionSave.PickupSave;
import Shift.LevelSaveFile.DimensionSave.WallSave;

import ucigame.Image;
import ucigame.Sprite;

public class Level 
{
	Point start; //Player's start location
	Area end; //The end location of the level
	Image background;
	Backgrounds bkgType;
	ArrayList<Dimension> dimensions; //Each dimension has a unique set of Sprites that will be drawn
	Dimension currDim;
	int currDimension;
	
	public Level()
	{
		dimensions = new ArrayList<Dimension>();
		currDim = new Dimension(Dimensions.DIM0);
		dimensions.add(currDim);
		dimensions.add(new Dimension(Dimensions.DIM1));
		currDimension = 0;
	}
	
	public void addObject(LevelObject obj)
	{
		//TODO Create actual logic for adding object to current dimension
		dimensions.get(currDimension).addObject(obj);
	}
	
	public void render()
	{
//		for(Wall s : walls)
//		{
//			s.draw();
//		}
		if(background != null)
			background.draw(0, 0);
		dimensions.get(0).render();
		currDim.render();
	}
	
	public void addBackground(Shift parent, Backgrounds type)
	{
		this.bkgType = type;
		background = parent.getImage(type.img);
	}
	
	public Dimensions getCurrDims()
	{
		return dimensions.get(currDimension).dims;
	}
	
	public void switchDim(int newDim)
	{ 
		if(newDim >= 0 && newDim < dimensions.size())
		{
			currDimension = newDim;
			currDim = dimensions.get(currDimension);
		}
	}
	
	public void unpackSaveFile(Shift help, LevelSaveFile data)
	{
		this.start = data.start;
		this.end = data.end;
		this.bkgType = data.bkgType;
		if(bkgType != null)
			background = help.getImage(bkgType.img);
		dimensions = new ArrayList<Dimension>();
		
		for(DimensionSave dim : data.dimensions)
		{
			Dimension newDim = new Dimension(dim.dims);
			
			for(WallSave w : dim.walls)
			{
				Wall wall = new Wall(help, w.type);
				wall.position(w.xPos, w.yPos);
				newDim.walls.add(wall);
			}
			
			for(PickupSave p : dim.pickups)
			{
				PickupItem pick = new PickupItem(help, p.type);
				pick.position(p.xPos, p.yPos);
				newDim.pickupItems.add(pick);
			}
			
			dimensions.add(newDim);
		}
		
	}
}
