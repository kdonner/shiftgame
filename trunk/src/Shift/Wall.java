package Shift;

public class Wall extends LevelObject
{
	Walls wallType;
	
	public Wall(Shift parent, Walls type)
	{
		super(parent, ObjectType.WALL, parent.getImage(type.img));
		this.wallType = type;
	}
}
