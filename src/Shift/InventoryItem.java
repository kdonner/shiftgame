package Shift;
import ucigame.Image;
import ucigame.Sprite;
public class InventoryItem extends Sprite 
{
	public static int WIDTH = 64;
	public static int HEIGHT = 64;
	boolean hasFound;
	Shift parent;
	
	public InventoryItem(Shift parent, String imgDir, int xLoc, int yLoc)
	{
		super(parent.getImage(Constants.IMG_DIR+imgDir));
		this.parent = parent;
		hasFound = false; //TODO: Change default to false, set to true only to ensure drawing correctly
		this.position(xLoc, yLoc);
		this.setOpacity(0.5f);
	}
	
	public void use()
	{
		if(hasFound)
		{
			this.setOpacity(0.1f);
			hasFound = false;
		}
	}
	
	public void found()
	{
		if(!hasFound)
		{
			this.setOpacity(1f);
			hasFound = true;
		}
	}
}
