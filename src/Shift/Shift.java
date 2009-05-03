package Shift;

import ucigame.*;

@SuppressWarnings("serial")
public class Shift extends Ucigame
{
	private static final int TIME_OFFSET = 15;
	public static final double FRAME_RATE = 30;
	public static final int FRAME_WIDTH = 1280;
	public static final int FRAME_HEIGHT = 720;
	private Player player;
	private Level currLevel;
	private float opac; //for testing opacity changes
	private long startTime;

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
		startTime = System.currentTimeMillis();
		opac = 1f;
	}
	
	public void draw()
	{
		canvas.clear();
		currLevel.render();
		drawUI();
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
	
	private void drawUI()
	{
		drawHealth();
		drawArmor();
		drawTime();
		drawInventory();
	}
	
	private void drawHealth()
	{
		
	}
	
	private void drawArmor()
	{
		
	}
	
	private void drawTime()
	{
		StringBuilder build = Constants.strBuild;
		long elapsed = (System.currentTimeMillis() - startTime) / 1000; //Gets rid of milliseconds for display
		int hours = (int)elapsed / 3600;
		if(hours > 0)
		{
			build.append(hours);
			build.append(":");
		}
		int min = (int)(elapsed % 3600) / 60;
		build.append(min);
		build.append(":");
		int sec = (int)elapsed % 60;
		if(sec <= 9)
		{
			build.append("0");
		}
		build.append(sec);
		canvas.putText(build.toString(), (FRAME_WIDTH / 2), TIME_OFFSET);
		build.delete(0, build.length()); //This clears the string builder for the next call
	}
	
	private void drawInventory()
	{
		
	}
	
	public Level getCurrLevel()
	{
		return currLevel;
	}
}
