package Shift;

import ucigame.Image;

public class DustJumpEffect extends SpecialEffect 
{
	public DustJumpEffect(Shift parent)
	{
		super(80, 20);
		Image dust = parent.getImage(Constants.IMG_DIR + "Temp/JumpDust.png");
		addFrames(dust,
				0, 0,
				80, 0,
				160, 0,
				240, 0,
				320, 0);
		defineSequence("Poof", 0, 1, 2, 3, 4);
		play("Poof");
		framerate(Shift.FRAME_RATE);
		lifeSpan = 5;
	}
}
