package Shift;

import java.util.ArrayList;

import ucigame.Image;
import ucigame.Sprite;

public class DimensionMenu 
{
	class DimensionWedge extends Sprite
	{
		double rotation;
		
		public DimensionWedge(Shift parent, String imgDir)
		{
			super(parent.getImage(imgDir));
		}
		
		public void draw()
		{
			this.rotate(rotation);
			super.draw();
		}
	}
	
	ArrayList<DimensionWedge> wedges;
	
	public DimensionMenu(Shift parent, int numDims)
	{
		//These Simply check to make sure the numDims is within the expected range
		//If it isn't it forces the value to the nearest bound
		if(numDims < 2)
			numDims = 2;
		if(numDims > 8)
			numDims = 8;
		wedges = new ArrayList<DimensionWedge>(numDims);
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			rotationAmt += rotation;
		}
	}
	
	public void draw()
	{
		for(int i = 0; i < wedges.size(); i++)
		{
			wedges.get(i).draw();
		}
	}
}
