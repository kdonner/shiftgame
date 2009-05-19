package Shift;

public enum Pickups 
{
	HEALTH_PACK(Constants.IMG_DIR + "items/healthpack.png"),
	ARMOR_PACK(Constants.IMG_DIR + "items/armorpack.png");
	
	String img;
	Pickups(String imgLoc)
	{
		img = imgLoc;
	}

}
