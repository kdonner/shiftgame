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
	private Sprite onWhat; //Goes with onSurface
	Inventory inven;
	Actions currentAction;
	private HistoryManager history;
	short health, armor; //These will be between 0 and 100
	
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
		defineSequence(Actions.STAND.name, 12); //TODO: make real stand sprite
		defineSequence(Actions.JUMP.name, 12); //TODO: make real jump animation
		defineSequence(Actions.PUSH.name, 11); //TODO: make real push sprite
		framerate(20);
		
		inven = new Inventory(parent);
		flipHoriz = false;
		flipVertical = false;
		jumping = false;
		onSurface = false;
		currentAction = Actions.STAND;
		collision = false;
		onWhat = null;
		health = 100;
		armor = 0;
		
		history = new HistoryManager();
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
			onWhat = null;
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
	
	public void lossHealth(short amt)
	{
		if(armor > 0)
		{
			if(armor < amt)
			{
				amt -= armor;
				armor = 0;
			}
			else
			{
				armor -= amt;
				amt = 0;
			}
		}
		if(amt > 0)
		{
			if(health < amt)
			{
				health = 0;
				die();
			}
			else
			{
				health -= amt;
			}
		}
	}
	
	private void die()
	{
		//TODO send death signals, start level over, etc.
	}
	
	public void move(Dimensions dim)
	{
		//TODO: This is temorary for the reverse time functionality, should actually check dimension 
		if(dim != Dimensions.DIM2)
		{
			history.add(new PlayerHistory(x(), y(), xspeed(), yspeed(), currFrame, this.flipHoriz, this.flipVertical, currentAction));
			super.move(); //If this doesn't occur before collision detection jumping gets screwed
			collision = false;
			if(flipHoriz)
				flipHorizontal();
			if(flipVertical)
				flipVertical();
			if(onSurface)
			{
				if((x() + this.width()) < onWhat.x() || x() > (onWhat.x() + onWhat.width()))
				{
					System.out.println("Fall off Surface: " + x()+width() + " : " + onWhat.x());
					onSurface = false;
					onWhat = null;
				}
			}
			//TODO make real check to see if you are already standing on something
			if(!onSurface)
			{
				checkIfCollidesWith(parent.BOTTOMEDGE);
				if(collided())
				{
					stopFall(parent.BOTTOMEDGE);
				}
			}
				
			Level lev = parent.getCurrLevel();
			for(Sprite s : lev.walls)
			{
				this.checkIfCollidesWith(s, parent.PIXELPERFECT); //PixelPerfect doesn't work well
				//Commented out for self created collision direction/side detection
				if(this.collided())
				{
					System.out.println("basic Collision");
					checkCollision(s);
				}
//				if(!onSurface)
//				{
//					if(this.collided(parent.TOP))
//					{
//						stopFall(s);
//					}
//					else if(this.collided(parent.BOTTOM))
//					{
//						stopRise(s);
//					}
//				}
//				if(this.collided(parent.LEFT))
//				{
//					stopXMovement(s, true);
//				}
//				else if(this.collided(parent.RIGHT))
//				{
//					stopXMovement(s, false);
//				}
			}
			if(!onSurface)
			{
				double yVel = yspeed() + dim.getGravity();
				motion(xspeed(), (yVel > dim.terminalVelocity? dim.terminalVelocity : yVel));
			}
			
			else
			{
				//TODO Make sure they are staying on the surface
			}
		}
		else //for testing the rewind
		{
			try
			{
				if(!history.isEmpty())
				{
					PlayerHistory past = history.remove();
					this.position(past.xLoc, past.yLoc);
					this.motion(past.xSpeed, past.ySpeed);
					if(past.flipHoriz)
						flipHorizontal();
					if(past.flipVert)
						flipVertical();
					playAction(past.action);
					this.setToFrame(past.frame);
					onSurface = false;
				}
			}
			catch(HistoryEmptyException e)
			{
				System.out.println(e);
			}
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
	
	/**
	 * This self made collision detection is meant to aid with PIXELPERFECT
	 * @param collidedWith
	 */
	private void checkCollision(Sprite collidedWith)
	{
		//Doesn't check yspeed == 0 because it could never collide vertically
		if(this.yspeed() > 0)
		{
			if(collidedWith.y() > this.y() + this.height())
			{
				stopFall(collidedWith);
			}
		}
		if(this.yspeed() < 0)
		{
			if(this.x() + this.width()/2 > collidedWith.x() && this.x() < collidedWith.x() + collidedWith.width())
			{
				if(collidedWith.y() < this.y())
				{
					stopRise(collidedWith);
				}
			}
		}
		if(this.xspeed() > 0)
		{
			if(this.y() < collidedWith.y() + collidedWith.height() && !(this.x() + this.width()/2 > collidedWith.x() + collidedWith.width()))
			{
				if(this.x() + this.width() > collidedWith.x() && !(this.x() > collidedWith.x() + this.width()/2))
				{
					stopXMovement(collidedWith, true);
				}
			}
		}
		if(this.xspeed() < 0)
		{
			if(this.y() < collidedWith.y() + collidedWith.height() && !(this.x() < collidedWith.x() + collidedWith.width()/8))
			{
				if(this.x() < collidedWith.x() + collidedWith.width())
				{
					stopXMovement(collidedWith, false);
				}
			}
		}
	}
	
	private void stopXMovement(Sprite collidedWith, boolean leftSide)
	{
		System.out.println("Collision: X Movement");
		motion(0, yspeed());
		if(!collidedWith.equals(onWhat))
		{
			playAction(Actions.PUSH);
			if(leftSide)
			{
				this.position(collidedWith.x() - this.width(), y());
			}
			else
			{
				this.position(collidedWith.x() + collidedWith.width(), y());
			}
		}
		
	}
	
	private void stopRise(Sprite collidedWith)
	{
		collision = true;
		System.out.println("Collision: Stop Rise");
		motion(xspeed(), 0);
		this.position(this.x(), collidedWith.y() + collidedWith.height());
		jumping = true;
		onSurface = false;
		onWhat = null;
	}
	
	private void stopFall(Sprite collidedWith)
	{
		collision = true;
		System.out.println("Collision: Stop Fall");
		motion(xspeed(), 0);
		this.position(this.x(), collidedWith.y() - HEIGHT);
		jumping = false;
		onSurface = true;
		onWhat = collidedWith;
	}
	
	public void draw(Dimensions which)
	{
		move(which);
		inven.draw();
		super.draw();
		inven.draw();
	}
}


