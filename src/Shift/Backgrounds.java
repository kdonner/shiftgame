package Shift;

public enum Backgrounds 
{
	BKG1(Constants.IMG_DIR + "levels/bkg/Background1.png"),
	BKG2(Constants.IMG_DIR + "levels/bkg/Background2.png");
	
	String img;
	
	Backgrounds(String imgLoc)
	{
		img = imgLoc;
	}
}
