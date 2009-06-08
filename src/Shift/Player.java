package Shift;
import java.util.ArrayList;

import ucigame.Image;
import ucigame.Sound;
import ucigame.Sprite;

public class Player extends Sprite
{
	private static final int LASER_DAMAGE = 15;
	private static final int DAMAGE_VELOCITY = 10;
	private static final double RUN_SPEED = 4.5;
	public static final int HEIGHT = 95;
	public static final int WIDTH = 64;
	private static final int JUMP_FORCE = -7;
	
	private boolean jumping, onSurface, pushing, pushLeft, moveThisFrame;
	boolean flipHoriz, flipVertical;
	protected boolean isAlive;
	short health, armor; //These will be between 0 and 100
	
	private Sprite onWhat, pushingWhat; //Goes with onSurface and pushing
	private HistoryManager history;
	private ArrayList<SpecialEffect> effects;
	private Sound fallWhoosh;
	Shift parent;
	Inventory inven;
	Actions currentAction;
	Dimensions currDim;
	
	public Player(Shift parent)
	{
		super(WIDTH, HEIGHT);
		this.parent = parent;
		
//		Image run = parent.getImage(Constants.IMG_DIR + "player/playerBack.png", 255, 0, 255);
//		addFrames(run, 
//				0, 0, //Frame 0
//				64, 0,
//				128, 0,
//				192, 0,
//				256, 0,
//				320, 0,
//				384, 0,
//				448, 0,
//				512, 0,
//				576, 0,
//				640, 0,
//				704, 0, //Frame 11
//				576, 0); //Frame 12 Standing
		//This is just for testing
		Image run = parent.getImage(Constants.IMG_DIR + "Temp/StickManWorking.png", 255, 255, 255);
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
				0, 95,  //Frame 12 Push
				64, 95, //13 - Stand
				128, 95, //14 - Jump Take Off
				192, 95, //15 - Jump Apex
				256, 95, //16 - Jump Fall
				320, 95, //17 - Fall Fast
				384, 95); //18 - Dead
		defineSequence(Actions.RUN.name, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
		defineSequence(Actions.STAND.name, 13); 
		defineSequence(Actions.JUMP_TAKEOFF.name, 14); 
		defineSequence(Actions.JUMP_FALL.name, 16);
		defineSequence(Actions.JUMP_APEX.name, 15);
		defineSequence(Actions.FALL_FAST.name, 17);
		defineSequence(Actions.PUSH.name, 12); 
		defineSequence(Actions.DEAD.name, 18);
		framerate(20);
		
		fallWhoosh = parent.getSound(Constants.AUDIO_DIR + "whoosh.mp3");
		
		inven = new Inventory(parent);
		effects = new ArrayList<SpecialEffect>();
		flipHoriz = false;
		flipVertical = false;
		jumping = false;
		onSurface = false;
		pushing = false;
		pushLeft = false;
		moveThisFrame = false;
		isAlive = true;
		currentAction = Actions.STAND;
		playAction(currentAction);
		onWhat = null;
		pushingWhat = null;
		health = 100;
		armor = 0;
		
		history = new HistoryManager();
	}
	
	public void playAction(Actions action)
	{
		if(!parent.playerFinishedLevel && isAlive)
		{
			currentAction = action;
			play(action.name);
		}
	}
	
	public void jump()
	{
		moveThisFrame = true;
		if(!jumping && onSurface)
		{
			if(!pushing)
			{
				playAction(Actions.JUMP_TAKEOFF);
				addJumpDust();
			}
			if(isAlive)
			{
				motion(xspeed(), yspeed() + (currDim.getGravity() < 0? -JUMP_FORCE : JUMP_FORCE));
				jumping = true;
				onSurface = false;
				onWhat = null;
			}
		}
	}

	private void addJumpDust() 
	{
		DustJumpEffect dust = new DustJumpEffect(parent);
		dust.flipVertical = flipVertical;
		dust.position(this.x() - (flipHoriz? -20 : 20), (flipVertical? this.y() : this.y() + HEIGHT - dust.height()));
		effects.add(dust);
	}
	
	protected void laserHit(double xLoc, double yLoc, double angle)
	{
		System.out.println("Laser Hit!!!  Angle: " + angle);
		addBlood(xLoc, yLoc, angle);
		lossHealth(LASER_DAMAGE);
	}

	private void addBlood(double xLoc, double yLoc, double angle) 
	{
		LaserHitBlood blood = new LaserHitBlood(parent, angle);
		blood.position(xLoc - blood.width()/2, yLoc - blood.height());
		effects.add(blood);
	}

	public void stand()
	{
		if(!pushing && !jumping)
			playAction(Actions.STAND);
		motion(0, yspeed());
	}
	
	public void run()
	{
		moveThisFrame = true;
		checkPush();
		if(!pushing)
		{
			if(!jumping)
				playAction(Actions.RUN);
			if(isAlive)
				motion((flipHoriz? -RUN_SPEED : RUN_SPEED), yspeed());
			else
				motion(xspeed()/2, yspeed());
		}
	}
	
	private void checkPush()
	{
		if(pushingWhat != null)
		{
			if((y() + height()) < pushingWhat.y() || y() > (pushingWhat.y() + pushingWhat.height()))
			{
				System.out.println("Push off Surface: " + x()+width() + " : " + pushingWhat.x());
				pushing = false;
				pushingWhat = null;
				if(jumping)
				{
					checkJumpAnimation();
				}
			}
			if(pushing)
			{
				if(!((pushLeft && flipHoriz) || (!pushLeft && !flipHoriz)))
				{
					System.out.println("Walk away from Push");
					pushing = false;
					pushingWhat = null;
				}
			}
		}
		else
		{
			pushing = false;
		}
	}
	
	private void checkJumpAnimation() //Should only be called when jumping
	{
		if(!pushing)
		{
			double yVel = Math.abs(yspeed());
			if(yVel < 0.5)
			{
				playAction(Actions.JUMP_APEX);
			}
			else if((yspeed() < 0 && currDim.gravIsDown) || (yspeed() > 0 && !currDim.gravIsDown))
			{
				playAction(Actions.JUMP_TAKEOFF);
			}
			else
			{
				if(yVel > DAMAGE_VELOCITY)
				{
					playAction(Actions.FALL_FAST);
					if(yVel < DAMAGE_VELOCITY + 1)
						fallWhoosh.play();
				}
				else
				{
					playAction(Actions.JUMP_FALL);
				}
				if(yVel > currDim.terminalVelocity - 1 && yVel < currDim.terminalVelocity)
				{
					fallWhoosh.play();
				}
			}
		}
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
		playAction(Actions.DEAD);
		isAlive = false; //Must come after the play action
		System.out.println("Die");
	}
	
	public void move(Dimensions dim)
	{
		adjustDimension(dim);
			
		if(dim != Dimensions.DIM5)
		{
			history.add(new PlayerHistory(parent.currLevel.currDimension, x(), y(), xspeed(), yspeed(), currFrame, 
					this.flipHoriz, this.flipVertical, this.onSurface, currentAction, onWhat, pushing, pushLeft, pushingWhat, jumping));
			super.move(); //If this doesn't occur before collision detection jumping gets screwed
			checkOrientation();
			checkFallOff();
			checkEdges();
			if(jumping)
			{
				checkJumpAnimation();
			}
			checkLevelCollision();
			addGravity(dim);
		}
		else
		{
			rewindPlayerState();
		}
	}

	private void rewindPlayerState() 
	{
		try
		{
			if(!history.isEmpty())
			{
				PlayerHistory past = history.remove();
				parent.currLevel.dimensions.get(past.currentDim).render();
				this.position(past.xLoc, past.yLoc);
				if(past.jumping)
					this.motion(past.xSpeed, past.ySpeed);
				else
					this.motion(0, 0);
				if(past.flipHoriz)
					flipHorizontal();
				if(past.flipVert)
					flipVertical();
				playAction(past.action);
				this.setToFrame(past.frame);
				onSurface = past.onSurface;
				this.onWhat = past.onWhat;
				this.pushing = past.pushing;
				this.pushLeft = past.pushLeft;
				this.pushingWhat = past.pushWhat;
				this.jumping = past.jumping;
			}
		}
		catch(HistoryEmptyException e)
		{
			System.out.println(e);
		}
	}

	private void addGravity(Dimensions dim) 
	{
		if(!onSurface)
		{
			double yVel = yspeed() + dim.getGravity();
			if(currDim.gravIsDown)
				motion(xspeed(), (yVel > dim.terminalVelocity? dim.terminalVelocity : yVel));
			else
				motion(xspeed(), (yVel < dim.terminalVelocity? dim.terminalVelocity : yVel));
		}
	}

	private void checkLevelCollision() 
	{
		Level lev = parent.getCurrLevel();
		for(Wall w : lev.dimensions.get(0).walls)
		{
			checkSpriteForCollision(w, (w.wallType == Walls.ELECTRIC1 || w.wallType == Walls.ELECTRIC2));
		}
		for(Wall w : lev.currDim.walls)
		{
			checkSpriteForCollision(w, (w.wallType == Walls.ELECTRIC1 || w.wallType == Walls.ELECTRIC2));
		}
		for(PickupItem p : lev.dimensions.get(0).pickupItems)
		{
			if(checkForPickup(p))
			{
				p.acquired();
			}
		}
		for(PickupItem p : lev.currDim.pickupItems)
		{
			if(checkForPickup(p))
			{
				p.acquired();
			}
		}
		for(Door d : lev.dimensions.get(0).doors)
		{
			checkDoorOpen(d);
		}
		for(Door d : lev.currDim.doors)
		{
			checkDoorOpen(d);
		}
	}
	
	private void checkDoorOpen(Door d)
	{
		checkSpriteForCollision(d, false);
		checkSpriteForCollision(d.door, false);
		if(inven.items[0].hasFound && collided())
		{
			parent.getSound(Constants.AUDIO_DIR + "doorOpen.mp3").play();
			d.retract();
			inven.items[0].use();
		}
	}

	private void checkOrientation() 
	{
		if(flipHoriz)
			flipHorizontal();
		if(flipVertical)
			flipVertical();
	}

	private void adjustDimension(Dimensions dim) 
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
		if(dim != currDim)
		{
			currDim = dim;
			flipVertical = !currDim.gravIsDown;
		}
	}

	private void checkEdges() 
	{
		if(!onSurface)
		{
			checkIfCollidesWith(parent.BOTTOMEDGE, parent.PIXELPERFECT);
			if(collided())
			{
				stopFall(parent.BOTTOMEDGE);
			}
			checkIfCollidesWith(parent.TOPEDGE, parent.PIXELPERFECT);
			if(collided())
			{
				stopRise(parent.TOPEDGE);
			}
		}
		checkIfCollidesWith(parent.RIGHTEDGE, parent.PIXELPERFECT);
		if(collided())
		{
			stopXMovement(parent.RIGHTEDGE, true);
		}
		checkIfCollidesWith(parent.LEFTEDGE, parent.PIXELPERFECT);
		if(collided())
		{
			stopXMovement(parent.LEFTEDGE, false);
		}
	}

	private void checkFallOff() 
	{
		if(onSurface)
		{
			if((x() + width()/2) < onWhat.x() || (x() + width()/2) > (onWhat.x() + onWhat.width()))
			{
				System.out.println("Fall off Surface: " + x()+width() + " : " + onWhat.x());
				onSurface = false;
				onWhat = null;
			}
		}
	}
	
	private boolean checkForPickup(PickupItem item)
	{
		this.checkIfCollidesWith(item);
		if(this.collided())
		{
			pickupItem(item);
			return true;
		}
		return false;
	}
	
	private void pickupItem(PickupItem item)
	{
		if(item.itemType == Pickups.ARMOR_PACK)
		{
			armor = 100;
		}
		else if(item.itemType == Pickups.HEALTH_PACK)
		{
			health = 100;
		}
		else if(item.itemType == Pickups.SILVER_KEY)
		{
			inven.unlock(0);
		}
		else if(item.itemType == Pickups.GOLD_KEY)
		{
			inven.unlock(1);
		}
	}

	private void checkSpriteForCollision(Sprite s, boolean deathWall) 
	{
		this.checkIfCollidesWith(s, parent.PIXELPERFECT);
		if(this.collided())
		{
			if(deathWall)
			{
				parent.getSound(Constants.AUDIO_DIR + "electricCurrent.mp3").play();
				lossHealth(200);
			}
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
		if(this.yspeed() > 0)
		{
			if(this.x() + this.width() - 10 >= collidedWith.x() && this.x() - 8 < collidedWith.x() + collidedWith.width())
			{
				if(this.y() + this.height() - 5 < collidedWith.y() || xspeed() == 0)
				{
					stopFall(collidedWith);
				}
			}
		}
		if(this.yspeed() < 0)
		{
			if(this.x() + this.width() - 10 >= collidedWith.x() && this.x() + this.width()/4 - 6 < collidedWith.x() + collidedWith.width())
			{
				if(this.y() > collidedWith.y() + collidedWith.height() - 10 || xspeed() == 0)
				{
					stopRise(collidedWith);
				}
			}
		}
		if(this.xspeed() > 0)
		{
			if(this.x() + this.width() > collidedWith.x() && this.x() + this.width()/2 < collidedWith.x() + 6)
			{
				if(this.y() - 5 < collidedWith.y() + collidedWith.height() && this.y() + this.height() > collidedWith.y() || yspeed() == 0)
				{
					stopXMovement(collidedWith, true);
				}
			}
		}
		if(this.xspeed() < 0)
		{
			if(this.x() < collidedWith.x() + collidedWith.width() && this.x() + this.width()/2 + 6 > collidedWith.x() + collidedWith.width())
			{
				if(this.y() - 5 < collidedWith.y() + collidedWith.height() && this.y() + this.height() > collidedWith.y() || yspeed() == 0)
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
			pushing = true;
			pushingWhat = collidedWith;
			currentAction = Actions.PUSH;
			playAction(Actions.PUSH);
			if(leftSide)
			{
				pushLeft = false;
				this.position(collidedWith.x() - this.width(), y());
			}
			else
			{
				pushLeft = true;
				this.position(collidedWith.x() + collidedWith.width(), y());
			}
		}
		
	}
	
	private void stopRise(Sprite collidedWith)
	{
		System.out.println("Collision: Stop Rise");
		checkFallDamage();
		motion(xspeed(), 0);
		this.position(this.x(), collidedWith.y() + collidedWith.height() + 1);
		if(!pushing)
		{
			playAction(Actions.STAND);
		}
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
		System.out.println("Collision: Stop Fall");
		checkFallDamage();
		motion(xspeed(), 0);
		this.position(this.x(), collidedWith.y() - HEIGHT);
		if(!pushing)
		{
			playAction(Actions.STAND);
		}
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
			int damage = fallDamage(Math.abs(yspeed()) - DAMAGE_VELOCITY);
			lossHealth(damage);
			if(!isAlive)
			{
				parent.getSound(Constants.AUDIO_DIR + "groundSmack.mp3").play();
			}
			else
			{
				parent.getSound(Constants.AUDIO_DIR + "groundSmackSoft.mp3").play();
			}
		}
	}
	
	private int fallDamage(double velocity) 
	{
		System.out.println("Velocity - Damage Speed : " + velocity * 10);
		return (int) velocity * 10;
	}
	
	public double centerX()
	{
		return x() + width()/2;
	}
	
	public double centerY()
	{
		return y() + height()/2;
	}
	
	public void draw(Dimensions which)
	{
		if(!parent.playerFinishedLevel)
		{
			move(which);
		}
		for(int i = 0; i < effects.size(); i++)
		{
			effects.get(i).draw();
			if(effects.get(i).isDead())
				effects.remove(i);
		}
		super.draw();
		if(!moveThisFrame)
		{
			playAction(Actions.STAND);
		}
		moveThisFrame = false;
	}
}


