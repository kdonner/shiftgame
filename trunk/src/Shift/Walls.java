package Shift;

public enum Walls 
{
	WALL1(Constants.IMG_DIR + "levels/wall1.gif"),
	WALL2(Constants.IMG_DIR + "levels/wall2.gif"),
	PLATFORM1(Constants.IMG_DIR + "levels/platform1.gif"),
	PLATFORM2(Constants.IMG_DIR + "levels/platform2.gif"),
	BLOCK1(Constants.IMG_DIR + "levels/block1.gif");
	
	String img;
	Walls(String img)
	{
		this.img = img;
	}
	
	public Walls next()
	{
		if(this == WALL1)
			return WALL2;
		if(this == WALL2)
			return PLATFORM1;
		if(this == PLATFORM1)
			return PLATFORM2;
		if(this == PLATFORM2)
			return BLOCK1;
		return WALL1;
	}
}
