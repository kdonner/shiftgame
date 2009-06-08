package Shift;

import java.util.ArrayList;

import ucigame.Image;
import ucigame.Sprite;

public class DimensionMenu 
{
	private static final int MIN_MOUSE_MOVE_DIST = 2;

	class DimensionWedge extends Sprite
	{
		double rotation, origX, origY;
		int label;
		private Shift parent;
		
		public DimensionWedge(Shift parent, String imgDir, int label, double x, double y)
		{
			super(parent.getImage(imgDir));
			this.parent = parent;
			this.label = label;
			origX = x;
			origY = y;
			font("Arial", parent.BOLD, 16);
		}
		
		public void draw()
		{
			this.position(origX + parent.gameCamera.getXOffset(), origY - parent.gameCamera.getYOffset());
			this.rotate(rotation);
			putText(label, width()/2 - 4 - parent.gameCamera.getXOffset(), height()/2 - 4 + parent.gameCamera.getYOffset());
			super.draw();
		}
	}
	
	private ArrayList<DimensionWedge> wedges;
	private Shift parent;
	private int[] labels;
	
	public DimensionMenu(Shift parent, int[] labels)
	{
		this.parent = parent;
		this.labels = labels;
		//These Simply check to make sure the numDims is within the expected range
		//If it isn't it forces the value to the nearest bound
		if(labels.length < 2 || labels.length > 8)
			return;
		
		wedges = new ArrayList<DimensionWedge>(labels.length);
		
		switch(labels.length)
		{
			case 2:
				create2(labels.length);
				break;
			case 3:
				create3(labels.length);
				break;
			case 4:
				create4(labels.length);
				break;
			case 5:
				create5(labels.length);
				break;
			case 6:
				create6(labels.length);
				break;
			case 7:
				create7(labels.length);
				break;
			case 8:
				create8(labels.length);
				break;
			default:
				break;
		}
	}
	
	public int calcSwitch(int oldX, int newX, int oldY, int newY) throws MouseBarelyMovedException
	{
		double x = newX - oldX;
		double y = -(newY - oldY);
		double degrees = 1;
		
		if(Constants.pythagorean(x, y) < MIN_MOUSE_MOVE_DIST)
			throw new MouseBarelyMovedException();
		
		degrees = Constants.angleFromZero(x, y);
		
		double inc = 360 / labels.length;
		if(degrees < inc/2 || degrees > (360 - inc/2))
			return labels[0];
		int i = 0;
		for(double deg = inc/2; deg < 360; deg += inc)
		{
			if(degrees < deg)
				return labels[i];
			i++;
		}
		return 1;
	}
	
	private void create2(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
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
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
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
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
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
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
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
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
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
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height()/4 + 7;
				xLoc += reference.width()/2 + 8;
			}
			if(i == 1)
			{
				yLoc += wedge.height()/2 + 20;
				xLoc += wedge.width()/4 - 10;
			}
			if(i == 2)
			{
				yLoc += wedge.height()/2 + 9;
				xLoc -= reference.width()/4 + 15;
			}
			if(i == 3)
			{
				yLoc += 2;
				xLoc -= wedge.width()/2 + 16;
			}
			if(i == 4)
			{
				yLoc -= wedge.height()/2 + 7;
				xLoc -= reference.width()/4 + 18;
			}
			if(i == 5)
			{
				rotationAmt += 2.2;
				yLoc -= wedge.height()/2 + 22;
				xLoc += 14;
			}
			rotationAmt += rotation;
		}
	}
	
	private void create8(int numDims)
	{
		String menuImgDir = Constants.IMG_DIR + "menu/Select" + numDims + ".png";
		double rotation = 360 / numDims;
		double rotationAmt = 0;
		Image reference = parent.getImage(menuImgDir);
		double xLoc = Shift.FRAME_WIDTH/2 - reference.width()/2, yLoc = Shift.FRAME_HEIGHT/2 - reference.height()/2 - reference.height()/2; 
		for(int i = 0; i < numDims; i++)
		{
			DimensionWedge wedge = new DimensionWedge(parent, menuImgDir, labels[i], xLoc, yLoc);
			wedge.rotation = rotationAmt;
			wedges.add(wedge);
			if(i == 0)
			{
				yLoc += wedge.height()/4 + 1;
				xLoc += reference.width()/2 + 9;
			}
			if(i == 1)
			{
				yLoc += wedge.height()/2 + 12;
				xLoc += wedge.width()/4 + 1;
			}
			if(i == 2)
			{
				yLoc += wedge.height()/2 + 12;
				xLoc -= reference.width()/4;
			}
			if(i == 3)
			{
				yLoc += wedge.height()/4 + 2;
				xLoc -= reference.width()/2 + 10;
			}
			if(i == 4)
			{
				yLoc -= wedge.height()/4 + 2;
				xLoc -= reference.width()/2 + 10;
			}
			if(i == 5)
			{
				yLoc -= wedge.height()/2 + 12;
				xLoc -= reference.width()/4;
			}
			if(i == 6)
			{
				yLoc -= wedge.height()/2 + 11;
				xLoc += wedge.width()/4;
			}
			rotationAmt += rotation;
		}
	}
	
	public void draw()
	{
		if(wedges != null)
		{
			for(int i = 0; i < wedges.size(); i++)
			{
				wedges.get(i).draw();
			}
		}
	}
}
