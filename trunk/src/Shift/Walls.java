package Shift;

public enum Walls 
{
	WALL1(Constants.IMG_DIR + "levels/wall1.gif"),
	PLATFORM1(Constants.IMG_DIR + "levels/platform1.gif"),
	BLOCK1(Constants.IMG_DIR + "levels/block1.gif");
	
	String img;
	Walls(String img)
	{
		this.img = img;
	}
	
	public Walls next()
	{
		if(this == WALL1)
			return PLATFORM1;
		if(this == PLATFORM1)
			return BLOCK1;
		return WALL1;
	}
}
