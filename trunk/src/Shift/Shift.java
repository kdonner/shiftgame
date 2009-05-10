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
		//Everything below this is just for testing purposes TODO: Remove this when everything runs well
		player.armor = 100; 
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
		//The following controls are just for testing
		if(keyboard.isDown(keyboard.J))
		{
			currLevel.switchDim(1); 
		}
		if(keyboard.isDown(keyboard.K))
		{
			currLevel.switchDim(0); 
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
		if(keyboard.isDown(keyboard.Y))
		{
			player.lossHealth((short)1);
		}
		if(keyboard.isDown(keyboard.Q))
		{
			player.inven.items[0].found();
		}
		if(keyboard.isDown(keyboard.TAB))
		{
			player.inven.items[0].use();
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
		if(player.health > 0)
		{
			int x = 10;
			int y = 10;
			Sprite heal = makeSprite(getImage(Constants.IMG_DIR + "player/HealthLeft.png"));
			heal.position(x, y);
			heal.draw();
			if(player.health > 20)
			{
				heal = makeSprite(getImage(Constants.IMG_DIR + "player/HealthFiller.png"));
				for(short i = player.health; i > 20; i -=10)
				{
					x += 25;
					heal.position(x, y);
					heal.draw();
				}
			}
			heal = makeSprite(getImage(Constants.IMG_DIR + "player/HealthRight.png"));
			heal.position((player.health > 10? x + 25 : x + 1), y);
			heal.draw();
		}
	}
	
	private void drawArmor()
	{
		if(player.armor > 0)
		{
			int x = 10;
			int y = 30;
			Sprite armor = makeSprite(getImage(Constants.IMG_DIR + "player/ArmorLeft.png"));
			armor.position(x, y);
			armor.draw();
			if(player.armor > 20)
			{
				armor = makeSprite(getImage(Constants.IMG_DIR + "player/ArmorFiller.png"));
				for(short i = player.armor; i > 20; i -=10)
				{
					x += 25;
					armor.position(x, y);
					armor.draw();
				}
			}
			armor = makeSprite(getImage(Constants.IMG_DIR + "player/ArmorRight.png"));
			armor.position((player.armor > 10? x + 25 : x + 1), y);
			armor.draw();
		}
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
		Constants.clearStringBuilder(); //This clears the string builder for the next call to the string builder
	}
	
	private void drawInventory()
	{
		
	}
	
	public Level getCurrLevel()
	{
		return currLevel;
	}
}
