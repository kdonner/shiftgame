package Shift;

import ucigame.Sprite;

public class Background 
{
	private Shift parent;
	private Sprite topLeft, topRight, bottomLeft, bottomRight;
	
	public Background(Shift par, Backgrounds type)
	{
		parent = par;
		topLeft = parent.makeSprite(parent.getImage(type.img));
		topRight = parent.makeSprite(parent.getImage(type.img));
		bottomLeft = parent.makeSprite(parent.getImage(type.img));
		bottomRight = parent.makeSprite(parent.getImage(type.img));
		bottomLeft.position(0, 0);
		bottomRight.position(parent.FRAME_WIDTH, 0);
		topLeft.position(0, -parent.FRAME_HEIGHT);
		topRight.position(parent.FRAME_WIDTH, -parent.FRAME_HEIGHT);
	}
	
	public void draw()
	{
		updatePositions();
		topLeft.draw();
		topRight.draw();
		bottomLeft.draw();
		bottomRight.draw();
	}
	
	private void updatePositions()
	{
		//TODO update positions of background tiles with correct positions according to game camera
	}
	
	public void setFilters(float[] filters)
	{
		topLeft.setFilters(filters);
		topRight.setFilters(filters);
		bottomLeft.setFilters(filters);
		bottomRight.setFilters(filters);
	}
}
