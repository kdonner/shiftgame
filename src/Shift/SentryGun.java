package Shift;

import java.util.ArrayList;

import ucigame.Sound;
import ucigame.Sprite;
import ucigame.Ucigame;

public class SentryGun extends LevelObject 
{
	class Laser extends Sprite
	{
		private static final double LASER_SPEED = 20;
		private double angle;
		private Shift parent;
		protected boolean rewinding;
		protected SentryGun creator;
		
		public Laser(Shift parent, double angle, double xPos, double yPos, SentryGun maker)
		{
			super(parent.getImage(Constants.IMG_DIR + "items/STLaser.png"));
			this.parent = parent;
			rewinding = false;
			creator = maker;
			
			this.angle = angle % 360;
	
			if(this.angle < 0)
			{
				this.angle = 360 + this.angle;
			}
			position(xPos, yPos);

			double relativeAngle = 90 - (this.angle % 90);
			//System.out.println("Angle: " + this.angle +  " Rel Angle: " + relativeAngle);
			double x = xMotion(relativeAngle, LASER_SPEED);
			double y = yMotion(relativeAngle, LASER_SPEED);
			//System.out.println("Angle: " + this.angle);

			if(this.angle < 90)
			{
				y = -y;
			}
			else if(this.angle < 180)
			{
				double temp = x;
				x = y;
				y = temp;
			}
			else if(this.angle < 270)
			{
				x = -x;
			}
			else
			{
				double temp = -x;
				x = -y;
				y = temp;
			}
			this.motion(x, y);
		}

		public double xMotion(double angle, double force)
		{
			double x = Math.cos(Math.toRadians(angle)) * force;
			return x;
		}

		public double yMotion(double angle, double force)
		{
			double y = Math.sin(Math.toRadians(angle)) * force;
			return y;
		}
		
		protected boolean outOfBounds()
		{
			double x = this.x() - parent.gameCamera.getXOffset();
			double y = this.y() + parent.gameCamera.getYOffset();
			return (x < -10 || x > Shift.FRAME_WIDTH + 10 || y < -10 || y > Shift.FRAME_HEIGHT + 10);
		}
		
		protected void reverse()
		{
			motion(-xspeed(), -yspeed());
		}

		public void draw()
		{
			super.move();
			super.rotate(angle);
			super.draw();
		}
	}
	
	private static final int ENGAGE_SPEED = 40;
	private static final int ATTACK_DISTANCE = 400;
	private static final int COOL_DOWN_TIME = 5; //Number of Frames to cool down
	private static final double FIRE_PROB = 0.05;
	private Sound laser; 
	Sprite top;
	Shift parent;
	ArrayList<Laser> lasers;
	private boolean rotateRight;
	private double centerX, centerY, rotation;
	private int heat;
	protected boolean alive;
	
	
	public SentryGun(Shift parent)
	{
		super(parent, ObjectType.ENEMY, parent.getImage(Constants.IMG_DIR + "items/STBottom.png"));
		this.parent = parent;
		lasers = new ArrayList<Laser>();
		top = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "items/STTop.png"));
		centerX = width()/2;
		centerY = -top.height()/2;
		pin(top, 0, -top.height()/2);
		rotation = parent.randomInt(180) - 180;
		rotateRight = false;
		heat = parent.randomInt(COOL_DOWN_TIME);
		alive = true;
		laser = parent.getSound(Constants.AUDIO_DIR + "laser.mp3");
	}
	
	protected void die()
	{
		alive = false;
		parent.currLevel.effects.add(new SentryExplosion(parent));
	}
	
	public void draw()
	{
		heat--;
		calculateRotation();
		drawLasers();
		super.draw();
	}

	private void drawLasers() 
	{
		cleanLasers();
		for(int i = 0; i < lasers.size(); i++)
		{
			lasers.get(i).draw();
		}
	}

	private void cleanLasers() 
	{
		for(int i = 0; i < lasers.size(); i++)
		{
			boolean removed = false;
			if(lasers.get(i).outOfBounds())
			{
				lasers.remove(i);
				removed = true;
			}
			else
			{
				Laser las = lasers.get(i);
				if(checkLevelCollision(parent.currLevel, las))
				{
					lasers.remove(i);
					removed = true;
				}
				else
				{
					las.checkIfCollidesWith(parent.player, parent.PIXELPERFECT);
					if(las.collided())
					{
						parent.player.laserHit(las.x(), las.y(), las.angle);
						lasers.remove(i);
						removed = true;
					}
				}
			}
			if(!removed)
			{
				if(parent.currLevel.currDim.dims == Dimensions.DIM5)
				{
					Laser las = lasers.get(i);
					if(!las.rewinding)
					{
						las.rewinding = true;
						las.reverse();
					}
				}
				else
				{
					Laser las = lasers.get(i);
					if(las.rewinding)
					{
						las.rewinding = false;
						las.reverse();
					}
				}
			}
		}
	}
	
	private boolean checkLevelCollision(Level lev, Laser las)
	{
		return checkDimCollision(lev.dimensions.get(0), las) || checkDimCollision(lev.currDim, las);
	}
	
	private boolean checkDimCollision(Dimension dim, Laser las)
	{
		for(Wall w : dim.walls)
		{
			las.checkIfCollidesWith(w, parent.PIXELPERFECT);
			if(las.collided())
			{
				return true;
			}
		}
		
		for(SentryGun s : dim.enemies)
		{
			if(!s.equals(las.creator))
			{
				las.checkIfCollidesWith(s, parent.PIXELPERFECT);
				if(las.collided())
				{
					s.die();
					return true;
				}
			}
		}
		
		return false;
	}

	private void calculateRotation() 
	{
		double xOff = 0;
		double yOff = 0;
		if(parent.player != null)
		{
			xOff = parent.player.centerX() - (this.x() + centerX);
			yOff = -((parent.player.centerY() - 15) - (this.y() + this.centerY));
		}
		
		if(parent.player != null && (parent.player.xspeed() != 0 || parent.player.yspeed() != 0) && Constants.pythagorean(xOff, yOff) < ATTACK_DISTANCE)
		{
			if(parent.currLevel.currDim.dims != Dimensions.DIM5)
			{
				rotation = Constants.angleFromZero(xOff, yOff) - 90;
				if(heat < 0)
				{
					laser.play();
					lasers.add(new Laser(this.parent, rotation + 90, this.x() + this.width()/2, this.y() - 6, this));
					heat = COOL_DOWN_TIME;
				}
			}
		}
		else
		{
			if(rotateRight && rotation >= 0)
				rotateRight = false;
			if(!rotateRight && rotation <= -180)
				rotateRight = true;
			
			rotation += (rotateRight? 1 : -1);
		}
		top.rotate(rotation);
	}
}
