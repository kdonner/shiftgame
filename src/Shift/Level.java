package Shift;

import java.util.ArrayList;

import Shift.LevelSaveFile.DimensionSave;
import Shift.LevelSaveFile.DimensionSave.DoorSave;
import Shift.LevelSaveFile.DimensionSave.EnemySave;
import Shift.LevelSaveFile.DimensionSave.PickupSave;
import Shift.LevelSaveFile.DimensionSave.WallSave;

import ucigame.Image;
import ucigame.Sprite;

public class Level 
{
	Point start; //Player's start location
	Area end; //The end location of the level
	Sprite endZone;
	Background background;
	Backgrounds bkgType;
	ArrayList<Dimension> dimensions; //Each dimension has a unique set of Sprites that will be drawn
	protected ArrayList<SpecialEffect> effects;
	protected HighScores scores;
	Dimension currDim;
	int currDimension;
	int width, height;
	
	public Level()
	{
		dimensions = new ArrayList<Dimension>();
		effects = new ArrayList<SpecialEffect>();
		scores = new HighScores();
		currDim = new Dimension(Dimensions.DIM0);
		dimensions.add(currDim);
		currDimension = 0;
		width = Shift.FRAME_WIDTH;
		height = Shift.FRAME_HEIGHT;
	}
	
	public void addObject(LevelObject obj)
	{
		dimensions.get(currDimension).addObject(obj);
	}
	
	public void drawBkg()
	{
		if(background != null)
			background.draw();
	}
	
	public void render(Shift renderTo)
	{	
		drawBkg();
		
		if(end != null && endZone == null)
		{
			updateExit(renderTo);
		}
		else
		{
			if(endZone != null)
			{
				if(!currDim.dims.gravIsDown)
				{
					endZone.flipHorizontal();
					endZone.flipVertical();
				}
				endZone.draw();
			}
		}
		
		if(currDim.dims != Dimensions.DIM0)
		{
			dimensions.get(0).render();
		}
		currDim.render();
		for(int i = 0; i < effects.size(); i++)
		{
			effects.get(i).draw();
			if(effects.get(i).isDead())
				effects.remove(i);
		}
	}
	
	public void updateExit(Shift help)
	{
		if(end != null && endZone == null)
		{
			endZone = help.makeSprite(help.getImage(Constants.IMG_DIR + "levels/exit.png"));
		}
		if(end != null && endZone != null)
		{
			endZone.position(end.xy.xLoc, end.xy.yLoc);
		}
	}
	
	public void addBackground(Shift parent, Backgrounds type)
	{
		bkgType = type;
		background = new Background(parent, type);
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
	
	public void switchDim(int newDim, boolean addNew, Shift parent)
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
					return;
				}
				if(dimensions.get(i).dims.dimNum > searchingFor.dimNum)
				{
					if(addNew)
					{
						currDimension = i;
						createNewDim(parent, searchingFor);
						dimensions.add(i, currDim);
					}
					return;
				}
			}
			if(addNew)
			{
				createNewDim(parent, searchingFor);
				dimensions.add(currDim);
				currDimension = dimensions.size()-1;
			}
		}
	}

	private void createNewDim(Shift parent, Dimensions searchingFor) 
	{
		currDim = new Dimension(searchingFor);
	}
	
	public void unpackSaveFile(Shift help, LevelSaveFile data)
	{
		this.width = data.width;
		this.height = data.height;
		this.start = data.start;
		this.end = data.end;
		this.bkgType = data.bkgType;
		this.background = new Background(help, bkgType);
		updateExit(help);
		if(data.scores == null)
			this.scores = new HighScores();
		else
			this.scores = data.scores;
		dimensions = new ArrayList<Dimension>();
		
		for(DimensionSave dim : data.dimensions)
		{
			Dimension newDim = new Dimension(dim.dims);
			
			for(WallSave w : dim.walls)
			{
				Wall wall = new Wall(help, w.type);
				wall.position(w.xPos, w.yPos);
				wall.flipVert = w.flip;
				wall.setFilters(dim.dims.filters);
				newDim.walls.add(wall);
			}
			
			for(PickupSave p : dim.pickups)
			{
				PickupItem pick = new PickupItem(help, p.type);
				pick.position(p.xPos, p.yPos);
				pick.flipVert = p.flip;
				newDim.pickupItems.add(pick);
			}
			
			for(EnemySave e : dim.enemies)
			{
				SentryGun gun = new SentryGun(help);
				gun.position(e.xPos, e.yPos);
				gun.flipVert = e.flip;
				newDim.enemies.add(gun);
			}
			
			for(DoorSave d : dim.doors)
			{
				Door door = new Door(help);
				door.position(d.xPos, d.yPos);
				door.setFilters(dim.dims.filters);
				door.flipVert = d.flip;
				newDim.doors.add(door);
			}
			
			dimensions.add(newDim);
		}
		
	}
}
