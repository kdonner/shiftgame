package Shift;
import java.util.ArrayList;

import ucigame.*;

@SuppressWarnings("serial")
public class Shift extends Ucigame
{
	public static final double FRAME_RATE = 30;
	public static final int FRAME_WIDTH = 1280;
	public static final int FRAME_HEIGHT = 720;
	private Player player;
	private Level currLevel;
	private float opac; //for testing opacity changes

	public void setup()
	{
		window.size(FRAME_WIDTH, FRAME_HEIGHT);
		window.title("Shift");
		window.showFPS();
		canvas.background(200, 200, 200);
		framerate(FRAME_RATE);
		player = new Player(this);
		player.position(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
		player.stand();
		currLevel = Tester.makeLevel(this);
		opac = 1f;
	}
	
	public void draw()
	{
		canvas.clear();
		currLevel.render();
		player.draw(currLevel.getCurrDims());
	}
	
	public void onKeyPress()
	{
		if(keyboard.isDown(keyboard.RIGHT, keyboard.D))
		{
			player.flipHoriz = false;
			player.run();
		}
		if(keyboard.isDown(keyboard.UP, keyboard.W))
		{
			player.jump();
		}
		if(keyboard.isDown(keyboard.LEFT, keyboard.A))
		{
			player.run();
			player.flipHoriz = true;
		}
		if(keyboard.isDown(keyboard.DOWN, keyboard.S))
		{
			
		}
		if(keyboard.isDown(keyboard.RIGHT, keyboard.D) && keyboard.isDown(keyboard.LEFT, keyboard.A))
		{
			player.stand();
		}
		if(keyboard.isDown(keyboard.J))
		{
			currLevel.switchDim(1); //just for testing
		}
		if(keyboard.isDown(keyboard.K))
		{
			currLevel.switchDim(0); //just for testing
		}
		if(keyboard.isDown(keyboard.EQUALS))
		{
			opac += 0.01f;
			player.setOpacity(opac);
		}
		if(keyboard.isDown(keyboard.DASH))
		{
			opac -= 0.01f;
			player.setOpacity(opac);
		}
	}
	
	public void onKeyRelease()
	{
		if(!(keyboard.isDown(keyboard.RIGHT, keyboard.D) || keyboard.isDown(keyboard.LEFT, keyboard.A)))
		{
			player.stand();
		}
	}
	
	public Level getCurrLevel()
	{
		return currLevel;
	}
}
