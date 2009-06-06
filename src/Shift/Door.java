package Shift;

import ucigame.Sprite;

public class Door extends LevelObject 
{
	Sprite door;
	private boolean retracting;
	private int movement;
	
	public Door(Shift parent) 
	{
		super(parent, ObjectType.INTERACTIVE, parent.getImage(Constants.IMG_DIR + "levels/doorWall.png"));
		door = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "levels/door.png"));
		retracting = false;
		movement = 0;
	}
	
	protected void retract()
	{
		retracting = true;
	}
	
	public void draw()
	{
		door.position(this.x() + 10, (flipVert? this.y() - super.height() : this.y() + super.height()));
		if(retracting)
		{
			if(movement < super.height())
			{
				movement += 2;
			}
			door.position(door.x(), door.y() - (flipVert? -movement : movement));
		}
		door.draw();
		super.draw();
	}
	
	public int height()
	{
		return super.height() + door.height();
	}
}
