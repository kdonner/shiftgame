package Shift;

import java.util.ArrayList;

import ucigame.Image;
import ucigame.Sprite;

public class Level 
{
	Point start; //Player's start location
	Area end; //The end location of the level
	Image background;
	ArrayList<Dimension> dimensions; //Each dimension has a unique set of Sprites that will be drawn
	Dimension currDim;
	int currDimension;
	
	public Level()
	{
		dimensions = new ArrayList<Dimension>();
		currDim = new Dimension(Dimensions.DIM0);
		dimensions.add(currDim);
		currDim = new Dimension(Dimensions.DIM1);
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
			background.draw(0, 0);
		dimensions.get(0).render();
		dimensions.get(currDimension).render();
	}
	
	public Dimensions getCurrDims()
	{
		return dimensions.get(currDimension).dims;
	}
	
	public void switchDim(int newDim)
	{
		currDimension = newDim;
	}
}
