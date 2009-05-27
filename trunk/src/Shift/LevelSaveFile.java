package Shift;

import java.util.ArrayList;
import java.io.Serializable;

public class LevelSaveFile implements Serializable
{
	private static final long serialVersionUID = 3627550956646622212L;
	
	int width, height;
	Point start;
	Area end;
	ArrayList<DimensionSave> dimensions;
	
	public LevelSaveFile(Level toSave)
	{
		this.width = toSave.width;
		this.height = toSave.height;
		this.start = toSave.start;
		this.end = toSave.end;
		dimensions = new ArrayList<DimensionSave>();
		for(Dimension d : toSave.dimensions)
		{
			dimensions.add(new DimensionSave(d));
		}
	}
	
	class DimensionSave implements Serializable
	{
		private static final long serialVersionUID = 3627550956646622212L;
		
		class WallSave implements Serializable
		{
			private static final long serialVersionUID = 3627550956646622212L;
			
			Walls type;
			double xPos, yPos;
			
			public WallSave(Wall toSave)
			{
				this.type = toSave.wallType;
				xPos = toSave.x();
				yPos = toSave.y();
			}
		}
		
		class PickupSave implements Serializable
		{
			private static final long serialVersionUID = 3627550956646622212L;
			Pickups type;
			double xPos, yPos;
			
			public PickupSave(PickupItem toSave)
			{
				this.type = toSave.itemType;
				xPos = toSave.x();
				yPos = toSave.y();
			}
		}
		
		Backgrounds bkgType;
		Dimensions dims;
		ArrayList<WallSave> walls;
		ArrayList<PickupSave> pickups;
		
		public DimensionSave(Dimension toSave)
		{
			this.bkgType = toSave.bkgType;
			this.dims = toSave.dims;
			walls = new ArrayList<WallSave>();
			pickups = new ArrayList<PickupSave>();
			
			for(Wall w : toSave.walls)
			{
				walls.add(new WallSave(w));
			}
			for(PickupItem p : toSave.pickupItems)
			{
				pickups.add(new PickupSave(p));
			}
		}
	}
}
