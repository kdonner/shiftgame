package Shift;

public class PlayerHistory
{
	double xLoc, yLoc, xSpeed, ySpeed;
	int frame;
	boolean flipHoriz, flipVert;
	Actions action;
	
	public PlayerHistory(double xLoc, double yLoc, double xSpeed, double ySpeed,
			int frame, boolean flipHoriz, boolean flipVert, Actions action)
	{
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.frame = frame;
		this.flipHoriz = flipHoriz;
		this.flipVert = flipVert;
		this.action = action;
	}
}
