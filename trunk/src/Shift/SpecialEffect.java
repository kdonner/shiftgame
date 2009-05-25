package Shift;

import ucigame.Image;
import ucigame.Sprite;

public class SpecialEffect extends Sprite 
{
	protected int lifeSpan;
	boolean flipVertical;
	
	public SpecialEffect(Image img)
	{
		super(img);
		flipVertical = false;
	}
	
	public SpecialEffect(int width, int height)
	{
		super(width, height);
	}
	
	public boolean isDead()
	{
		return lifeSpan <= 0;
	}
	
	public void draw()
	{
		if(flipVertical)
			super.flipVertical();
		super.draw();
		lifeSpan--;
	}
}
