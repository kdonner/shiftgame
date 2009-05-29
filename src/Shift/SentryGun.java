package Shift;

import ucigame.Sprite;

public class SentryGun extends LevelObject 
{
	private static final int ENGAGE_SPEED = 40;
	private static final int ATTACK_DISTANCE = 400;
	Sprite top;
	private boolean rotateRight;
	private double centerX, centerY, rotation;
	
	public SentryGun(Shift parent)
	{
		super(parent, ObjectType.ENEMY, parent.getImage(Constants.IMG_DIR + "items/STBottom.png"));
		top = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "items/STTop.png"));
		centerX = width()/2;
		centerY = -top.height()/2;
		pin(top, 0, -top.height()/2);
		rotation = parent.randomInt(180) - 180;
		rotateRight = false;
	}
	
	public void draw()
	{
		calculateRotation();
		super.draw();
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
		
		if(parent.player != null && Constants.pythagorean(xOff, yOff) < ATTACK_DISTANCE)
		{
			rotation = Constants.angleFromZero(xOff, yOff) - 90;
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
