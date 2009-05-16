package Shift;

import java.util.ArrayList;

import ucigame.Sprite;

public class Dimension implements java.io.Serializable
{
	private static final long serialVersionUID = 3627550956646622212L;
	Dimensions dims;
	ArrayList<Wall> walls;

	public Dimension(Dimensions dims)
	{
		this.dims = dims;
		walls = new ArrayList<Wall>();
	}
	
	public void render()
	{
		for(Wall s : walls)
		{
			s.draw();
		}
	}
	
	public void addObject(LevelObject obj)
	{
		if(obj.type == ObjectType.WALL)
		{
			walls.add(((Wall)obj));
		}
	}
}
