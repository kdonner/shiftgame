package Shift;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	private LevelEditor editor;
	public GameState state;
	
	private LevelObject levelObject;
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
			if(!mainMenu.isVisible())
				mainMenu.show();
			drawMainMenu();
		}
		else
		{
			mainMenu.hide();
		}
		if(state == GameState.IN_GAME)
		{
			renderGameState();
		}
		if(state == GameState.LEVEL_EDITOR)
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
		editor = new LevelEditor();
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
		if(editor.grabObject)
		{
			mouse.setCursor(mouse.HAND);
		}
		else
		{
			mouse.setCursor(mouse.DEFAULT);
		}
		if(levelObject != null)
		{
			if(!editor.snapToGrid)
			{
				levelObject.position(mouse.x(), mouse.y());
			}
			else
			{
				levelObject.position((mouse.x() - mouse.x() % editor.gridSize), (mouse.y() - mouse.y() % editor.gridSize));
			}
			levelObject.draw();
		}
	}
	
	public void onKeyPress()
	{
		// Controls for the Level Editor
		if(state == GameState.LEVEL_EDITOR)
		{
			keyboard.typematicOff();
			if(keyboard.isDown(keyboard.W))
			{
				if(!editor.grabObject)
				{
					if(levelObject != null && levelObject.type == ObjectType.WALL)
					{
						levelObject = new Wall(this, ((Wall)levelObject).wallType.next());
					}
					else
					{
						levelObject = new Wall(this, Walls.WALL1);
					}
					levelObject.setOpacity(0.33f);
				}
			}
			if(keyboard.isDown(keyboard.M))
			{
				editor.snapToGrid = !editor.snapToGrid;
			}	
			if(keyboard.isDown(keyboard.G))
			{
				levelObject = null;
				editor.grabObject = true;
			}
			else
			{
				editor.grabObject = false;
			}
			if(keyboard.isDown(keyboard.DELETE))
			{
				if(levelObject != null)
				{
					editor.removeObject(levelObject, currLevel);
					levelObject = null;
				}
			}
			if(keyboard.isDown(keyboard.F5))
			{
				saveLevel();
			}
			if(keyboard.isDown(keyboard.F8))
			{
				loadLevel();
			}
		}
		// Controls for in game
		if(state == GameState.IN_GAME)
		{
			keyboard.typematicOn();
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
				if(!editor.grabObject)
				{
					currLevel.addObject(levelObject);
				}
				else
				{
					editor.grabObject = false;
				}
				levelObject = null;
			}
			if(editor.grabObject)
			{
				levelObject = editor.findObject(mouse.sprite(), currLevel);
				if(levelObject != null)
					levelObject.setOpacity(0.33f);
				//editor.grabObject = false;
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
	
	private void saveLevel()
	{
		//TODO Finish Level Saving Function
//		Frame parent = new Frame();
//		parent.setVisible(false);
//		FileDialog fd = new FileDialog(parent, "Save", FileDialog.SAVE);
//        fd.setVisible(true);
//        if (fd.getFile() == null)
//            return;
//        String file = fd.getDirectory() + fd.getFile();
//		try
//		{
//	        FileOutputStream fileOut = new FileOutputStream(file);
//			ObjectOutputStream out = new ObjectOutputStream(fileOut);
//			out.writeObject(currLevel);
//			out.close();
//		}
//		catch(IOException e)
//		{
//			System.err.println(e);
//		}
//		finally
//		{
//			parent.dispose();
//		}
	}
	
	private void loadLevel()
	{
		//TODO Finish Level Saving Function
//		Frame parent = new Frame();
//		parent.setVisible(false);
//		FileDialog fd = new FileDialog(parent, "Open", FileDialog.LOAD);
//        fd.setVisible(true);
//        if (fd.getFile() == null)
//            return;
//        String file = fd.getDirectory() + fd.getFile();
//        
//        if(new File(file).exists())
//        {
//        	try
//        	{
//        		FileInputStream fileIn = new FileInputStream(file);
//    	        ObjectInputStream in = new ObjectInputStream(fileIn);
//    	        Level loading = (Level)in.readObject();
//    	        currLevel = loading;
//    	        in.close();
//        	}
//        	catch(ClassNotFoundException e)
//        	{
//        		System.err.println(e);
//        	}
//        	catch(IOException e)
//        	{
//        		System.err.println(e);
//        	}
//	        finally
//	        {
//	        	parent.dispose();
//	        }
//        }
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
