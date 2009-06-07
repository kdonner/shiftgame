package Shift;

import java.util.ListIterator;

import ucigame.Sprite;

public class LevelSelect 
{
	class LevelSelectBlock extends Sprite
	{
		public static final String BLANK_LEVEL = "----------------";
		private static final int MAX_TITLE = 16;
		protected static final int WIDTH = 160;
		protected static final int HEIGHT = 90;
		String title;
		String fastestTime;
		public LevelSelectBlock(Shift parent, int xLoc, int yLoc)
		{
			super(parent.getImage(Constants.IMG_DIR + "menu/levelselectblock.png"));
			position(xLoc, yLoc);
			this.font("Arial", parent.BOLD, 20, 0, 0, 0);
			title = BLANK_LEVEL;
		}
		
		public void draw()
		{
			if(title.trim().length() < 8)
			{
				putText(title, 25, HEIGHT/2 + 10);
			}
			else
			{
				putText(title, 10, HEIGHT/2 + 10);
			}
			super.draw();
		}
		
		public void setTitle(String title)
		{
			if(title.length() < MAX_TITLE)
			{
				for(int i = (MAX_TITLE - title.length())/2; i >= 0; i--)
				{
					title = " " + title;
				}
				this.title = title;
			}
		}
	}
	
	LevelSelectBlock[][] grid;
	private LevelManager man;
	
	public LevelSelect(LevelManager man, Shift parent)
	{
		this.man = man;
		grid = new LevelSelectBlock[8][8];
		assignBlocks(parent);
	}
	
	protected void assignBlocks(Shift parent)
	{
		ListIterator<String> keys = man.orderedKeys.listIterator();
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[i].length; j++)
			{
				grid[i][j] = new LevelSelectBlock(parent, i * LevelSelectBlock.WIDTH, j * LevelSelectBlock.HEIGHT);
				if(keys.hasNext())
				{
					grid[i][j].setTitle(keys.next());
				}
			}
		}
	}
	
	public String getTagAtMouse(int x, int y)
	{
		int i = x / LevelSelectBlock.WIDTH;
		int j = y / LevelSelectBlock.HEIGHT;
		return grid[i][j].title;
	}
	
	public void draw()
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[i].length; j++)
			{
				grid[i][j].draw();
			}
		}
	}
}
