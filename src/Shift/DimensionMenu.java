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
	
	private ArrayList<DimensionWedge> wedges;
	private Shift parent;
	
	public DimensionMenu(Shift parent, int numDims)
	{
		this.parent = parent;
		//These Simply check to make sure the numDims is within the expected range
		//If it isn't it forces the value to the nearest bound
		if(numDims < 2)
			numDims = 2;
		if(numDims > 8)
			numDims = 8;
		wedges = new ArrayList<DimensionWedge>(numDims);
		
		
		switch(numDims)
		{
			case 2:
				create2(numDims);
				break;
			case 3:
				create3(numDims);
				break;
			case 4:
				create4(numDims);
				break;
			case 5:
				create5(numDims);
				break;
			case 6:
				create6(numDims);
				break;
			case 7:
				create7(numDims);
				break;
			case 8:
				break;
			default:
				break;
		}
	}
	
	private void create2(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			yLoc += reference.height();
			//xLoc += reference.width()/2;
			rotationAmt += rotation;
		}
	}
	
	private void create3(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height();
				xLoc += reference.width()/4 + 7;
			}
			if(i == 1)
			{
				xLoc -= 2 * (reference.width()/4 + 7) + 2;
				yLoc -= 1;
			}
			rotationAmt += rotation;
		}
	}
	
	private void create4(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height() - 20;
				xLoc += reference.width()/2 - 11;
			}
			if(i == 1)
			{
				yLoc += wedge.height() - 20;
				xLoc -= reference.width()/2 - 11;
			}
			if(i == 2)
			{
				yLoc -= wedge.height() - 20;
				xLoc -= reference.width()/2 - 11;
			}
			rotationAmt += rotation;
		}
	}
	
	private void create5(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height()/2 + 6;
				xLoc += reference.width()/2;
			}
			if(i == 1)
			{
				yLoc += wedge.height() - 8;
				xLoc -= wedge.width()/4 - 8;
			}
			if(i == 2)
			{
//				yLoc -= wedge.height() - 20;
				xLoc -= wedge.width()/2 + 18;
			}
			if(i == 3)
			{
				yLoc -= wedge.height() - 8;
				xLoc -= wedge.width()/4 - 8;
			}
			rotationAmt += rotation;
		}
	}
	
	private void create6(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height()/2 - 5;
				xLoc += reference.width()/2 + 6;
			}
			if(i == 1)
			{
				yLoc += wedge.height() - 12;
//				xLoc -= wedge.width()/4 - 8;
			}
			if(i == 2)
			{
				yLoc += wedge.height()/2 - 6;
				xLoc -= reference.width()/2 + 6;
			}
			if(i == 3)
			{
				yLoc -= wedge.height()/2 - 6;
				xLoc -= reference.width()/2 + 6;
			}
			if(i == 4)
			{
				yLoc -= wedge.height() - 12;
			}
			rotationAmt += rotation;
		}
	}
	
	private void create7(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; //TODO: Add calculation to position wedges properly
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir);
			wedge.rotation = rotationAmt;
			wedge.position(xLoc, yLoc);
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height()/4 + 6;
				xLoc += reference.width()/2 + 7;
			}
			if(i == 1)
			{
				yLoc += wedge.height()/2 + 18;
				xLoc += wedge.width()/4 - 8;
			}
			if(i == 2)
			{
				yLoc += wedge.height()/2 + 6;
				xLoc -= reference.width()/4 + 14;
			}
			if(i == 3)
			{
				yLoc += 2;
				xLoc -= wedge.width()/2 + 14;
			}
			if(i == 4)
			{
				yLoc -= wedge.height()/2 + 5;
				xLoc -= reference.width()/4 + 16;
			}
			if(i == 5)
			{
				yLoc -= wedge.height()/2 + 16;
				xLoc += 10;
			}
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
