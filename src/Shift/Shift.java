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
	private long startTime;
	private MainMenu mainMenu;
	private DimensionMenu dimMenu;
	private boolean displayDimMenu;
	private double mouseX, mouseY;
	public GameState state;
	
	private Sprite levelObject;
//	Sprite testSprite; TODO: Remove test Sprite when done

	public void setup()
	{
		window.size(FRAME_WIDTH, FRAME_HEIGHT);
		window.title("Shift");
		window.showFPS();
		state = GameState.MAIN_MENU;
		canvas.background(200, 200, 200);
		framerate(FRAME_RATE);
		player = new Player(this);
		player.position(30, FRAME_HEIGHT - 100);
		mainMenu = new MainMenu(this);
		displayDimMenu = false;
		mouseX = -1;
		mouseY = -1;
	}
	
	public void draw()
	{
		canvas.clear();
		if(state == GameState.MAIN_MENU)
		{
			drawMainMenu();
		}
		else if(state == GameState.IN_GAME)
		{
			renderGameState();
		}
		else if(state == GameState.LEVEL_EDITOR)
		{
			drawEditorWindow();
		}
	}
	
	private void drawMainMenu()
	{
		mainMenu.draw();
	}
	
	public void onClickNewGame()
	{
		loadLevelIntoSystem(Tester.makeLevel(this));
		state = GameState.IN_GAME;
	}
	
	public void onClickLoadGame()
	{
		
	}
	
	public void onClickLevelEdit()
	{
		currLevel = new Level();
		state = GameState.LEVEL_EDITOR;
	}
	
	public void onClickOptions()
	{
		
	}
	
	public void onClickQuit()
	{
		System.exit(0);
	}
	
	private void loadLevelIntoSystem(Level toLoad)
	{
		currLevel = toLoad;
		dimMenu = new DimensionMenu(this, currLevel.dimensions.size());
		player.position(currLevel.start.xLoc, currLevel.start.yLoc);
		startTime = System.currentTimeMillis();
	}

	private void renderGameState() 
	{
		currLevel.render();
		drawUI();
		player.draw(currLevel.getCurrDims());
	}
	
	private void drawEditorWindow()
	{
		currLevel.render();
		if(levelObject != null)
		{
			levelObject.position(mouse.x(), mouse.y());
			levelObject.draw();
		}
	}
	
	public void onKeyPress()
	{
		if(state == GameState.LEVEL_EDITOR)
		{
			if(keyboard.isDown(keyboard.W))
			{
				levelObject = makeSprite(getImage(Constants.IMG_DIR + "levels/wall.gif"));
				levelObject.setOpacity(0.33f);
			}
		}
		if(state == GameState.IN_GAME)
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
			if(keyboard.isDown(keyboard.SHIFT))
			{
				if(!displayDimMenu)
				{
					displayDimMenu = true;
					mouseX = mouse.x();
					mouseY = mouse.y();
				}
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
			if(keyboard.isDown(keyboard.Y))
			{
				player.lossHealth((short)1);
			}
		}
	}
	
	public void onMousePressed()
	{
		if(state == GameState.LEVEL_EDITOR)
		{
			if(levelObject != null)
			{
				levelObject.setOpacity(1f);
				currLevel.walls.add(levelObject);
				levelObject = null;
			}
		}
	}
	
	public void onKeyRelease()
	{
		if(state == GameState.IN_GAME)
		{
			if(!(keyboard.isDown(keyboard.RIGHT, keyboard.D) || keyboard.isDown(keyboard.LEFT, keyboard.A)))
			{
				player.stand();
			}
			if(displayDimMenu)
			{
				if(!keyboard.isDown(keyboard.SHIFT))
				{
					displayDimMenu = false;
					//TODO ShiftDimensions shiftDimension(startX, endX, startY, endY)
					// shiftDimension(mouseX, mouse.x(), mouseY, mouse.y());
				}
			}
		}
	}
	
	private void drawUI()
	{
		drawHealth();
		drawArmor();
		drawTime();
		drawInventory();
		if(displayDimMenu)
		{
			dimMenu.draw();
		}
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
