package Shift;
import java.util.ArrayList;

import ucigame.Image;
import ucigame.Sprite;

public class Player extends Sprite
{
	private static final int DAMAGE_VELOCITY = 10;
	private static final double RUN_SPEED = 4.5;
	private static final int HEIGHT = 64;
	private static final int WIDTH = 64;
	private static final int JUMP_FORCE = -7;
	Shift parent;
	boolean flipHoriz, flipVertical;
	private boolean jumping, onSurface, collision;
	private Sprite onWhat; //Goes with onSurface
	Inventory inven;
	Actions currentAction;
	private HistoryManager history;
	short health, armor; //These will be between 0 and 100
	Dimensions currDim;
	
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
		collision = false;
		currentAction = Actions.STAND;
		playAction(currentAction);
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
			motion(xspeed(), yspeed() + (currDim.getGravity() < 0? -JUMP_FORCE : JUMP_FORCE));
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
	
	public void lossHealth(int amt)
	{
		System.out.println("Health Loss: " + amt);
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
		if(health <= 0)
			die();
	}
	
	private void die()
	{
		//TODO What happens when you die?
		System.out.println("Die");
	}
	
	public void move(Dimensions dim)
	{
		if(currDim == null)
			currDim = dim;
		if((dim.getGravity() < 0 && currDim.getGravity() > 0) ||
			(dim.getGravity() > 0 && currDim.getGravity() < 0))
		{
			onSurface = false;
			onWhat = null;
			jumping = true;
		}
		currDim = dim;
		if(dim != Dimensions.DIM5)
		{
			history.add(new PlayerHistory(x(), y(), xspeed(), yspeed(), currFrame, this.flipHoriz, this.flipVertical, this.onSurface, currentAction, onWhat));
			super.move(); //If this doesn't occur before collision detection jumping gets screwed
			collision = false;
			if(flipHoriz)
				flipHorizontal();
			if(currDim.getGravity() < 0)
				flipVertical();
			if(onSurface)
			{
				if((x() + this.width()/2) < onWhat.x() || (x() + this.width()/2) > (onWhat.x() + onWhat.width()))
				{
					System.out.println("Fall off Surface: " + x()+width() + " : " + onWhat.x());
					onSurface = false;
					onWhat = null;
				}
			}
			if(!onSurface)
			{
				checkIfCollidesWith(parent.BOTTOMEDGE);
				if(collided())
				{
					stopFall(parent.BOTTOMEDGE);
				}
				checkIfCollidesWith(parent.TOPEDGE);
				if(collided())
				{
					stopRise(parent.TOPEDGE);
				}
			}
				
			Level lev = parent.getCurrLevel();
			for(Sprite s : lev.dimensions.get(0).walls)
			{
				checkSpriteForCollision(s);
			}
			for(Sprite s : lev.dimensions.get(lev.currDimension).walls)
			{
				checkSpriteForCollision(s);
			}
			if(!onSurface)
			{
				double yVel = yspeed() + dim.getGravity();
				if(currDim.gravIsDown)
					motion(xspeed(), (yVel > dim.terminalVelocity? dim.terminalVelocity : yVel));
				else
					motion(xspeed(), (yVel < dim.terminalVelocity? dim.terminalVelocity : yVel));
			}
		}
		else
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
					onSurface = past.onSurface;
					this.onWhat = past.onWhat;
				}
			}
			catch(HistoryEmptyException e)
			{
				System.out.println(e);
			}
		}
	}

	private void checkSpriteForCollision(Sprite s) 
	{
		this.checkIfCollidesWith(s, parent.PIXELPERFECT);
		if(this.collided())
		{
			System.out.println("basic Collision");
			checkCollision(s);
		}
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
			if(collidedWith.y() < this.y() + this.height())
			{
				if((this.x() + this.width()/2) > collidedWith.x() && this.x() < collidedWith.x() + collidedWith.width() - this.width()/4) //Checks to make sure you're on top
				{
					stopFall(collidedWith);
				}
			}
		}
		if(this.yspeed() < 0)
		{
			if(collidedWith.y() < this.y() + this.height()/2)
			{
				if(this.x() + this.width()/2 > collidedWith.x() && this.x() < collidedWith.x() + collidedWith.width() - this.width()/4) //Checks to make sure you're under
				{
					stopRise(collidedWith);
				}
			}
		}
		if(this.xspeed() > 0)
		{
			if(this.y() + this.height()/2 < collidedWith.y() + collidedWith.height() && this.y() + this.height()/2 > collidedWith.y()) //somewhere inbetween vertically
			{
				if(this.x() + this.width() > collidedWith.x() && !(this.x() > collidedWith.x() + this.width()/2))
				{
					stopXMovement(collidedWith, true);
				}
			}
		}
		if(this.xspeed() < 0)
		{
			if(this.y() + this.height()/2 < collidedWith.y() + collidedWith.height() && this.y() + this.height()/2 > collidedWith.y()) //somewhere inbetween vertically
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
		checkFallDamage();
		motion(xspeed(), 0);
		this.position(this.x(), collidedWith.y() + collidedWith.height());
		if(!currDim.gravIsDown)
		{
			onSurface = true;
			onWhat = collidedWith;
			jumping = false;
		}
		else
		{
			jumping = true;
			onSurface = false;
			onWhat = null;
		}
	}

	private void stopFall(Sprite collidedWith)
	{
		collision = true;
		System.out.println("Collision: Stop Fall");
		checkFallDamage();
		motion(xspeed(), 0);
		this.position(this.x(), collidedWith.y() - HEIGHT);
		if(currDim.gravIsDown)
		{
			jumping = false;
			onSurface = true;
			onWhat = collidedWith;
		}
		else
		{
			onSurface = false;
			onWhat = null;
			jumping = true;
		}
	}
	
	private void checkFallDamage() 
	{
		System.out.println("Y Speed: " + yspeed());
		if(Math.abs(yspeed()) > DAMAGE_VELOCITY)
		{
			lossHealth(fallDamage(Math.abs(yspeed()) - DAMAGE_VELOCITY));
		}
	}
	
	private int fallDamage(double velocity) 
	{
		System.out.println("Velocity - Damage Speed : " + velocity * 10);
		return (int) velocity * 10;
	}
	
	public void draw(Dimensions which)
	{
		move(which);
		super.draw();
	}
}


