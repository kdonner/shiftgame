package Shift;

public enum Pickups 
{
	HEALTH_PACK(Constants.IMG_DIR + "items/healthpack.png"),
	ARMOR_PACK(Constants.IMG_DIR + "items/armorpack.png"),
	SILVER_KEY(Constants.IMG_DIR + "items/FindSilverKey.png"),
	GOLD_KEY(Constants.IMG_DIR + "items/FindGoldKey.png");
	
	String img;
	Pickups(String imgLoc)
	{
		img = imgLoc;
	}

}
