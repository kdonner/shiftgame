package Shift;

import ucigame.Sprite;

public class Door extends LevelObject 
{
	Sprite door;
	private boolean retracting;
	
	public Door(Shift parent) 
	{
		super(parent, ObjectType.INTERACTIVE, parent.getImage(Constants.IMG_DIR + "levels/doorWall.png"));
		door = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "levels/door.png"));
		retracting = false;
	}
	
	protected void retract()
	{
		retracting = true;
	}
	
	public void draw()
	{
		if(retracting)
		{
			if(door.y() > this.y())
				door.position(door.x(), door.y()-2);
		}
		else
		{
			door.position(this.x() + 10, this.y() + this.height());
		}
		door.draw();
		super.draw();
	}
}
