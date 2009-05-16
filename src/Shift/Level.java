package Shift;

import java.util.ArrayList;
import ucigame.Sprite;

public class Level implements java.io.Serializable 
{
	private static final long serialVersionUID = 3627550956646622212L;
	Point start; //Player's start location
	Area end; //The end location of the level
	ArrayList<Sprite> walls; //These walls will always be drawn
	ArrayList<Dimension> dimensions; //Each dimension has a unique set of Sprites that will be drawn
	Dimension currDim;
	int currDimension;
	
	public Level()
	{
		walls = new ArrayList<Sprite>();
		dimensions = new ArrayList<Dimension>();
		currDim = new Dimension(Dimensions.DIM1);
		dimensions.add(currDim);
		currDimension = 0;
	}
	
	public void render()
	{
		for(Sprite s : walls)
		{
			s.draw();
		}
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
