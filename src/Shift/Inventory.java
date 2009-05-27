package Shift;

public class Inventory 
{
	InventoryItem[] items;
	private static final int IMG_X_OFFSET = 10 + InventoryItem.WIDTH;
	private Shift parent;
	
	public Inventory(Shift parent)
	{
		this.parent = parent;
		items = new InventoryItem[3];
		int yLoc = 10;
		int xLoc = Shift.FRAME_WIDTH - IMG_X_OFFSET;
		items[0] = new InventoryItem(parent, "items/SilverKey.png", xLoc, yLoc);
		xLoc -= IMG_X_OFFSET;
		items[1] = new InventoryItem(parent, "items/GoldKey.png", xLoc, yLoc);
		xLoc -= IMG_X_OFFSET;
		items[2] = new InventoryItem(parent, "items/keyInventory.png", xLoc, yLoc);
	}
	
	public void draw()
	{
		updateLocations();
		items[0].draw();
		items[1].draw();
		items[2].draw();
	}
	
	private void updateLocations()
	{
		items[0].position(Shift.FRAME_WIDTH - IMG_X_OFFSET + parent.gameCamera.getXOffset(), 10 - parent.gameCamera.getYOffset());
		items[1].position(Shift.FRAME_WIDTH - IMG_X_OFFSET * 2 + parent.gameCamera.getXOffset(), 10 - parent.gameCamera.getYOffset());
		items[2].position(Shift.FRAME_WIDTH - IMG_X_OFFSET * 3 + parent.gameCamera.getXOffset(), 10 - parent.gameCamera.getYOffset());
	}
	
	public void unlock(int item)
	{
		if(item >= 0 && item <= 2)
			items[item].found();
	}

}
