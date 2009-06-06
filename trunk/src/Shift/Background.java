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
		int x = parent.gameCamera.getXOffset();
		int y = parent.gameCamera.getYOffset();
		System.out.println(Shift.FRAME_WIDTH + x);
		if(bottomLeft.y() > Shift.FRAME_HEIGHT - y)
		{
			System.out.println("Move Up");
			moveUp();
		}
		if(bottomLeft.y() + bottomLeft.height() < Shift.FRAME_HEIGHT - y)
		{
			System.out.println("Move Down");
			moveDown();
		}
		if(bottomRight.x() < x)
		{
			moveRight();
		}
		if(bottomRight.x() > Shift.FRAME_WIDTH + x)
		{
			moveLeft();
		}
		//TODO update positions of background tiles with correct positions according to game camera
	}
	
	private void moveLeft()
	{
		Sprite temp = bottomRight;
		bottomRight = bottomLeft;
		bottomLeft = temp;
		bottomLeft.position(bottomRight.x() - Shift.FRAME_WIDTH, bottomLeft.y());
		temp = topRight;
		topRight = topLeft;
		topLeft = temp;
		topLeft.position(topRight.x() - Shift.FRAME_WIDTH, topLeft.y());
		temp = null;
	}
	
	private void moveRight()
	{
		Sprite temp = bottomLeft;
		bottomLeft = bottomRight;
		bottomRight = temp;
		bottomRight.position(bottomLeft.x() + Shift.FRAME_WIDTH, bottomRight.y());
		temp = topLeft;
		topLeft = topRight;
		topRight = temp;
		topRight.position(topLeft.x() + Shift.FRAME_WIDTH, topRight.y());
		temp = null;
	}
	
	private void moveUp()
	{
		Sprite temp = bottomLeft;
		bottomLeft = topLeft;
		topLeft = temp;
		topLeft.position(topLeft.x(), bottomLeft.y() - Shift.FRAME_HEIGHT);
		temp = bottomRight;
		bottomRight = topRight;
		topRight = temp;
		topRight.position(topRight.x(), bottomRight.y() - Shift.FRAME_HEIGHT);
		temp = null;
	}
	
	private void moveDown()
	{
		Sprite temp = topLeft;
		topLeft = bottomLeft;
		bottomLeft = temp;
		bottomLeft.position(bottomLeft.x(), topLeft.y() + Shift.FRAME_HEIGHT);
		temp = topRight;
		topRight = bottomRight;
		bottomRight = temp;
		bottomRight.position(bottomRight.x(), topRight.y() + Shift.FRAME_HEIGHT);
		temp = null;
	}
}
