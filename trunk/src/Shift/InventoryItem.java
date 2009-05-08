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
		super(WIDTH, HEIGHT);
		this.parent = parent;
		Image item = parent.getImage(Constants.IMG_DIR+imgDir);
		this.addFrame(item, 0, 0);
		hasFound = false;
		this.position(xLoc, yLoc);
	}
	
	public void use()
	{
		if(hasFound)
		{
			this.setOpacity(0f);
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
	
	public void draw()
	{
		super.draw();
	}
}
