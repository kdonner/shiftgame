package Shift;

public class PlayerHistory
{
	double xLoc, yLoc;
	int frame;
	boolean flipHoriz, flipVert;
	Actions action;
	
	public PlayerHistory(double xLoc, double yLoc, int frame, boolean flipHoriz, boolean flipVert, Actions action)
	{
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.frame = frame;
		this.flipHoriz = flipHoriz;
		this.flipVert = flipVert;
		this.action = action;
	}
}
