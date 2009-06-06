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
	HighScores scores;
	Backgrounds bkgType;
	
	public LevelSaveFile(Level toSave)
	{
		this.width = toSave.width;
		this.height = toSave.height;
		this.start = toSave.start;
		this.end = toSave.end;
		this.scores = toSave.scores;
		this.bkgType = toSave.bkgType;
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
			boolean flip;
			
			public WallSave(Wall toSave)
			{
				this.type = toSave.wallType;
				xPos = toSave.x();
				yPos = toSave.y();
				flip = toSave.flipVert;
			}
		}
		
		class PickupSave implements Serializable
		{
			private static final long serialVersionUID = 3627550956646622212L;
			Pickups type;
			double xPos, yPos;
			boolean flip;
			
			public PickupSave(PickupItem toSave)
			{
				this.type = toSave.itemType;
				xPos = toSave.x();
				yPos = toSave.y();
				flip = toSave.flipVert;
			}
		}
		
		class EnemySave implements Serializable
		{
			private static final long serialVersionUID = 3627550956646622212L;
			double xPos, yPos;
			boolean flip;
			
			public EnemySave(SentryGun toSave)
			{
				this.xPos = toSave.x();
				this.yPos = toSave.y();
				flip = toSave.flipVert;
			}
		}
		
		class DoorSave implements Serializable
		{
			private static final long serialVersionUID = 3627550956646622212L;
			double xPos, yPos;
			boolean flip;
			
			public DoorSave(Door toSave)
			{
				this.xPos = toSave.x();
				this.yPos = toSave.y();
				flip = toSave.flipVert;
			}
		}
		
		Dimensions dims;
		ArrayList<WallSave> walls;
		ArrayList<PickupSave> pickups;
		ArrayList<EnemySave> enemies;
		ArrayList<DoorSave> doors;
		
		public DimensionSave(Dimension toSave)
		{
			this.dims = toSave.dims;
			walls = new ArrayList<WallSave>();
			pickups = new ArrayList<PickupSave>();
			enemies = new ArrayList<EnemySave>();
			doors = new ArrayList<DoorSave>();
			
			for(Wall w : toSave.walls)
			{
				walls.add(new WallSave(w));
			}
			for(PickupItem p : toSave.pickupItems)
			{
				pickups.add(new PickupSave(p));
			}
			for(SentryGun g : toSave.enemies)
			{
				enemies.add(new EnemySave(g));
			}
			for(Door d : toSave.doors)
			{
				doors.add(new DoorSave(d));
			}
		}
	}
}
