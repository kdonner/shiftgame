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
	Sprite background;
	Backgrounds bkgType;
	ArrayList<Dimension> dimensions; //Each dimension has a unique set of Sprites that will be drawn
	Dimension currDim;
	int currDimension;
	
	public Level()
	{
		dimensions = new ArrayList<Dimension>();
		currDim = new Dimension(Dimensions.DIM0);
		dimensions.add(currDim);
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
			background.draw();
		dimensions.get(0).render();
		currDim.render();
	}
	
	public void addBackground(Shift parent, Backgrounds type)
	{
		this.bkgType = type;
		background = parent.makeSprite(parent.getImage(type.img));
		background.position(0, 0);
	}
	
	public Dimensions getCurrDims()
	{
		return dimensions.get(currDimension).dims;
	}
	
	public int numDimensions()
	{
		return dimensions.size() - 1; // Because dimension 0 doesn't count
	}
	
	public int[] dimLabels()
	{
		int[] labels = new int[numDimensions()];
		for(int i = 1; i < dimensions.size(); i++)
		{
			labels[i-1] = dimensions.get(i).dims.dimNum;
		}
		return labels;
	}
	
	public void switchDim(int newDim, boolean addNew)
	{ 
		if(newDim >= 0 && newDim <= 8)
		{
			System.out.println("Switch to Dim: " + newDim);
			Dimensions searchingFor = Dimension.getDims(newDim);
			for(int i = 0; i < dimensions.size(); i++)
			{
				if(dimensions.get(i).dims == searchingFor)
				{
					currDimension = i;
					currDim = dimensions.get(currDimension);
					if(background != null)
						background.setFilters(currDim.dims.filters);
					return;
				}
				if(dimensions.get(i).dims.dimNum > searchingFor.dimNum)
				{
					if(addNew)
					{
						currDimension = i;
						currDim = new Dimension(searchingFor);
						dimensions.add(i, currDim);
					}
					return;
				}
			}
			if(addNew)
			{
				currDim = new Dimension(searchingFor);
				dimensions.add(currDim);
				currDimension = dimensions.size()-1;
			}
		}
	}
	
	public void unpackSaveFile(Shift help, LevelSaveFile data)
	{
		this.start = data.start;
		this.end = data.end;
		this.bkgType = data.bkgType;
		if(bkgType != null)
			background = help.makeSprite(help.getImage(bkgType.img));
		dimensions = new ArrayList<Dimension>();
		
		for(DimensionSave dim : data.dimensions)
		{
			Dimension newDim = new Dimension(dim.dims);
			
			for(WallSave w : dim.walls)
			{
				Wall wall = new Wall(help, w.type);
				wall.position(w.xPos, w.yPos);
				wall.setFilters(dim.dims.filters);
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
