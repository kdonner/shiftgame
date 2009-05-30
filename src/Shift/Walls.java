package Shift;

public enum Walls 
{
	WALL1(Constants.IMG_DIR + "levels/wall1.png"),
	WALL2(Constants.IMG_DIR + "levels/wall2.png"),
	PLATFORM1(Constants.IMG_DIR + "levels/platform1.png"),
	PLATFORM2(Constants.IMG_DIR + "levels/platform2.png"),
	BLOCK1(Constants.IMG_DIR + "levels/block1.png"),
	BLOCK2(Constants.IMG_DIR + "levels/block2.png");
	
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
		if(this == BLOCK1)
			return BLOCK2;
		return WALL1;
	}
}
