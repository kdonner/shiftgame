package Shift;

import java.util.ArrayList;

import ucigame.Sprite;

public class Dimension 
{
	Dimensions dims;
	ArrayList<Sprite> walls;

	public Dimension(Dimensions dims)
	{
		this.dims = dims;
		walls = new ArrayList<Sprite>();
	}
	
	public void render()
	{
		for(Sprite s : walls)
		{
			s.draw();
		}
	}
}
