package Shift;
import java.util.ArrayList;

import ucigame.Image;
import ucigame.Sprite;

public class Player extends Sprite
{
	private static final double RUN_SPEED = 4.5;
	private static final int HEIGHT = 64;
	private static final int WIDTH = 64;
	private static final int JUMP_FORCE = -5;
	Shift parent;
	boolean flipHoriz, flipVertical;
	private boolean jumping, onSurface, collision;
	Actions currentAction;
	private ArrayList<PlayerHistory> history;
	
	public Player(Shift parent)
	{
		super(WIDTH, HEIGHT);
		this.parent = parent;
		Image run = parent.getImage(Constants.IMG_DIR + "player/playerBack.png", 255, 0, 255);
		addFrames(run, 
				0, 0, //Frame 0
				64, 0,
				128, 0,
				192, 0,
				256, 0,
				320, 0,
				384, 0,
				448, 0,
				512, 0,
				576, 0,
				640, 0,
				704, 0, //Frame 11
				576, 0); //Frame 12 Standing  
		defineSequence(Actions.RUN.name, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
		defineSequence(Actions.STAND.name, 12);
		framerate(20);
		
		flipHoriz = false;
		flipVertical = false;
		jumping = false;
		onSurface = false;
		currentAction = Actions.STAND;
		collision = false;
		
		history = new ArrayList<PlayerHistory>();
	}
	
	public void playAction(Actions action)
	{
		currentAction = action;
		play(action.name);
	}
	
	public void jump()
	{
		if(!jumping && onSurface)
		{
			//TODO: Replace with playAction call once jump Action defined
			currentAction = Actions.JUMP;
			motion(xspeed(), yspeed() + JUMP_FORCE);
			jumping = true;
			onSurface = false;
		}
	}

	public void stand()
	{
		playAction(Actions.STAND);
		motion(0, yspeed());
	}
	
	public void run()
	{
		playAction(Actions.RUN);
		motion((flipHoriz? -RUN_SPEED : RUN_SPEED), yspeed());
	}
	
	public void move(Dimensions dim)
	{
		super.move(); //If this doesn't occur before collision detection jumping gets screwed
		collision = false;
		if(flipHoriz)
			flipHorizontal();
		if(flipVertical)
			flipVertical();
		//TODO make real check to see if you are already standing on something
		if(!onSurface)
		{
			checkIfCollidesWith(parent.BOTTOMEDGE);
			if(collided())
			{
				stopFall(parent.BOTTOMEDGE);
			}
			
			Level lev = parent.getCurrLevel();
			for(Sprite s : lev.walls)
			{
				s.checkIfCollidesWith(this); //PixelPerfect doesn't work well
				
				if(s.collided(parent.TOP))
				{
					stopFall(s);
				}
				else if(s.collided(parent.BOTTOM))
				{
					stopRise(s);
				}
				else if(s.collided(parent.LEFT))
				{
					stopXMovement(s, true);
				}
				else if(s.collided(parent.RIGHT))
				{
					stopXMovement(s, false);
				}
			}
			if(!collision)
			{
				double yVel = yspeed() + dim.getGravity();
				motion(xspeed(), (yVel > dim.terminalVelocity? dim.terminalVelocity : yVel));
			}
		}
		else
		{
			//TODO Make sure they are staying on the surface
		}
//		if((y() + HEIGHT) < Shift.FRAME_HEIGHT)
//		{
//			motion(xspeed(), yspeed() + dim.getGravity());
//		}
//		else
//		{
//			if(jumping)
//			{
//				onSurface = false;
//				jumping = false;
//			}
//			else
//			{
//				motion(xspeed(), 0);
//				this.position(x(), Shift.FRAME_HEIGHT - HEIGHT);
//				jumping = false;
//				onSurface = true;
//			}
//		}
	}
	
	private void stopXMovement(Sprite collidedWith, boolean leftSide)
	{
		collision = true;
		System.out.println("Collision: X Movement");
		motion(0, yspeed());
		if(leftSide)
		{
			this.position(collidedWith.x() - this.width(), y());
		}
		else
		{
			this.position(collidedWith.x() + collidedWith.width(), y());
		}
		
	}
	
	private void stopRise(Sprite collidedWith)
	{
		collision = true;
		System.out.println("Collision: Stop Rise");
		motion(xspeed(), 0);
		this.position(x(), collidedWith.y() + collidedWith.height());
		jumping = true;
		onSurface = false;
	}
	
	private void stopFall(Sprite collidedWith)
	{
		collision = true;
		System.out.println("Collision: Stop Fall");
		motion(xspeed(), 0);
		this.position(x(), collidedWith.y() - HEIGHT);
		jumping = false;
		onSurface = true;
	}
	
	public void draw(Dimensions which)
	{
		move(which);
		super.draw();
		//history.add(new PlayerHistory(x(), y(), currFrame, this.flipHoriz, this.flipVertical, currentAction));
	}
	
	class PlayerHistory
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
}


