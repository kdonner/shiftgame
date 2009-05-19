package Shift;

import ucigame.Image;
import ucigame.Sprite;

public class LevelObject extends Sprite
{
	ObjectType type;
	Shift parent;
	
	public LevelObject(Shift parent, ObjectType type, Image obj)
	{
		super(obj);
		this.parent = parent;
		this.type = type;
	}
	
	public boolean equals(LevelObject otherObj)
	{
		return (this.hashCode() == otherObj.hashCode());
	}
}
