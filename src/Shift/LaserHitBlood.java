package Shift;

import ucigame.Image;

public class LaserHitBlood extends SpecialEffect 
{
	double rot;
	public LaserHitBlood(Shift parent, double rotation)
	{
		super(30, 30);
		rot = rotation;
		Image dust = parent.getImage(Constants.IMG_DIR + "player/PlayerBlood.png");
		addFrames(dust,
				0, 0,
				30, 0,
				60, 0,
				90, 0,
				120, 0);
		defineSequence("Squirt", 0, 1, 2, 3, 4);
		play("Squirt");
		framerate(Shift.FRAME_RATE);
		lifeSpan = 5;
	}
	
	public void draw()
	{
		rotate(rot);
		super.draw();
	}
}
