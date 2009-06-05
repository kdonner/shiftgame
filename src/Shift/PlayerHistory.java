package Shift;

import ucigame.Sprite;

public class PlayerHistory
{
	double xLoc, yLoc, xSpeed, ySpeed;
	int frame, currentDim;
	boolean flipHoriz, flipVert, onSurface, pushing, pushLeft, jumping;
	Sprite onWhat, pushWhat;
	Actions action;
	
	public PlayerHistory(int currentDim, double xLoc, double yLoc, double xSpeed, double ySpeed,
			int frame, boolean flipHoriz, boolean flipVert, boolean onSurface, Actions action, Sprite onWhat,
			boolean pushing, boolean pushLeft, Sprite pushWhat, boolean jumping)
	{
		this.currentDim = currentDim;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.frame = frame;
		this.flipHoriz = flipHoriz;
		this.flipVert = flipVert;
		this.onSurface = onSurface;
		this.action = action;
		this.onWhat = onWhat;
		this.pushing = pushing;
		this.pushLeft = pushLeft;
		this.pushWhat = pushWhat;
		this.jumping = jumping;
	}
}
