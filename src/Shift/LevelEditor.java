package Shift;

import ucigame.Sprite;

public class LevelEditor 
{
	Sprite helpPage;
	Shift parent;
	boolean snapToGrid;
	boolean grabObject;
	boolean showHelp;
	int gridSize;
	
	public LevelEditor(Shift parent)
	{
		this.parent = parent;
		snapToGrid = true;
		grabObject = false;
		showHelp = false;
		gridSize = 5;
		helpPage = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "menu/editHelp.png"));
	}
	
	public void drawHelp()
	{
		helpPage.position(parent.gameCamera.getXOffset(), -parent.gameCamera.getYOffset());
		helpPage.draw();
	}
	
	//TODO optimize these searches so I don't have to keep adding things everytime something new gets added to a level
	public LevelObject findObject(Sprite toFind, Level objectIn)
	{
		for(Wall w : objectIn.dimensions.get(0).walls)
		{
			if(equivalent(w, toFind))
			{
				return w;
			}
		}
		for(Wall w : objectIn.dimensions.get(objectIn.currDimension).walls)
		{
			if(equivalent(w, toFind))
			{
				return w;
			}
		}
		for(PickupItem p : objectIn.dimensions.get(0).pickupItems)
		{
			if(equivalent(p, toFind))
			{
				return p;
			}
		}
		for(PickupItem p : objectIn.dimensions.get(objectIn.currDimension).pickupItems)
		{
			if(equivalent(p, toFind))
			{
				return p;
			}
		}
		for(SentryGun g : objectIn.dimensions.get(0).enemies)
		{
			if(equivalent(g, toFind) || equivalent(g.top, toFind))
			{
				return g;
			}
		}
		for(SentryGun g : objectIn.dimensions.get(objectIn.currDimension).enemies)
		{
			if(equivalent(g, toFind) || equivalent(g.top, toFind))
			{
				return g;
			}
		}
		for(Door d : objectIn.dimensions.get(0).doors)
		{
			if(equivalent(d.door, toFind) || equivalent(d, toFind))
			{
				return d;
			}
		}
		return null;
	}
	
	private boolean equivalent(Sprite obj, Sprite spr)
	{
		return obj.x() == spr.x() && obj.y() == spr.y(); //&& obj.width() == spr.width() && obj.height() == spr.height() && obj.hashCode();
	}
	
	public void removeObject(LevelObject toRemove, Level objectIn)
	{
		for(int i = 0; i < objectIn.dimensions.get(0).walls.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(0).walls.get(i)))
			{
				objectIn.dimensions.get(0).walls.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(objectIn.currDimension).walls.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(objectIn.currDimension).walls.get(i)))
			{
				objectIn.dimensions.get(objectIn.currDimension).walls.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(0).pickupItems.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(0).pickupItems.get(i)))
			{
				objectIn.dimensions.get(0).pickupItems.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(objectIn.currDimension).pickupItems.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(objectIn.currDimension).pickupItems.get(i)))
			{
				objectIn.dimensions.get(objectIn.currDimension).pickupItems.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(0).enemies.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(0).enemies.get(i)))
			{
				objectIn.dimensions.get(0).enemies.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(objectIn.currDimension).enemies.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(objectIn.currDimension).enemies.get(i)))
			{
				objectIn.dimensions.get(objectIn.currDimension).enemies.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(0).doors.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(0).doors.get(i)))
			{
				objectIn.dimensions.get(0).doors.remove(i);
				return;
			}
		}
		for(int i = 0; i < objectIn.dimensions.get(objectIn.currDimension).doors.size(); i++)
		{
			if(toRemove.equals(objectIn.dimensions.get(objectIn.currDimension).doors.get(i)))
			{
				objectIn.dimensions.get(objectIn.currDimension).doors.remove(i);
				return;
			}
		}
	}
}
