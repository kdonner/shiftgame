package Shift;

public class Inventory 
{
	InventoryItem[] items;
	private static final int IMG_X_OFFSET = 10 + InventoryItem.WIDTH;
	
	public Inventory(Shift parent)
	{
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
		items[0].draw();
		items[1].draw();
		items[2].draw();
	}
	
	public void unlock(int item)
	{
		if(item >= 0 && item <= 2)
			items[item].found();
	}

}
