package Shift;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Shift.LevelSelect.LevelSelectBlock;

import ucigame.*;

@SuppressWarnings("serial")
public class Shift extends Ucigame
{
	private static final int MAX_USER_LENGTH = 20;
	private static final String NONAME = "Noname";
	private static final int CAMERA_MOVE_AMOUNT = 40;
	private static final int TIME_OFFSET = 15;
	public static final int FRAME_RATE = 30;
	public static final int FRAME_WIDTH = 1280;
	public static final int FRAME_HEIGHT = 720;
	protected Player player;
	protected Level currLevel;
	private long startTime;
	private MainMenu mainMenu;
	private EndLevelMenu endMenu;
	private DimensionMenu dimMenu;
	private LevelManager levelManage;
	private LevelSelect selector;
	private boolean displayDimMenu;
	private int mouseX, mouseY;
	private LevelEditor editor;
	public GameState state;
	private LevelObject levelObject;
	protected boolean playerFinishedLevel;
	private long timeForLevel;
	protected String currentUser;

	public void setup()
	{
		window.size(FRAME_WIDTH, FRAME_HEIGHT);
		frameLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - FRAME_WIDTH/2, //Centers Width
					(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - FRAME_HEIGHT/2); //Centers Height
		window.title("Shift");
		window.showFPS();
		state = GameState.MAIN_MENU;
		canvas.background(200, 200, 200);
		canvas.font("Arial", PLAIN, 14);
		framerate(FRAME_RATE);
		mainMenu = new MainMenu(this);
		endMenu = new EndLevelMenu(this);
		endMenu.hide();
		levelManage = LevelManager.getInstance();
		displayDimMenu = false;
		playerFinishedLevel = false;
		timeForLevel = 0;
		mouseX = -1;
		mouseY = -1;
		currentUser = NONAME;
		onClickMainMenu();
	}
	
	public void draw()
	{
		canvas.clear();
		if(state == GameState.MAIN_MENU || state == GameState.OPTIONS)
		{
			resetGameCamera();
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
		if(state == GameState.LEVEL_SELECT)
		{
			drawLevelSelect();
		}
	}
	
	private void drawMainMenu()
	{
		mainMenu.draw();
	}
	
	public void onClickNewGame()
	{
		loadLevelIntoSystem(levelManage.loadLevel("1.1", this));
		if(currLevel != null)
			state = GameState.IN_GAME;
	}
	
	private void drawLevelSelect()
	{
		selector.draw();
	}
	
	public void onClickLoadGame()
	{
		
	}
	
	public void onClickTimeTrial()
	{
		resetGameCamera();
		selector = new LevelSelect(levelManage, this);
		state = GameState.LEVEL_SELECT;
	}
	
	public void onClickLevelEdit()
	{
		currLevel = new Level();
		currLevel.switchDim(0, false, this);
		editor = new LevelEditor(this);
		levelManage.currLevel = "";
		state = GameState.LEVEL_EDITOR;
	}
	
	public void onClickOptions()
	{
		currentUser = NONAME;
		state = GameState.OPTIONS;
	}
	
	public void onClickQuit()
	{
		exit();
	}
	
	protected void exit()
	{
		saveImportantResources();
		super.exit();
	}
	
	private void saveImportantResources()
	{
		//TODO: Enable Important Saves
		//LevelManager.saveInstance();
		System.out.println("Save Resources");
	}
	
	public void onClickMainMenu()
	{
		player = null;
		currLevel = null;
		canvas.font("Arial", PLAIN, 24);
		resetGameCamera();
		endMenu.hide();
		System.gc();
		state = GameState.MAIN_MENU;
	}
	
	public void onClickPlayAgain()
	{
		if(!levelManage.loadingLevel)
		{
			loadLevelIntoSystem(levelManage.loadLevel(levelManage.currLevel, this));
			endMenu.hide();
		}
	}
	
	public void onClickNextLevel()
	{
		if(!levelManage.loadingLevel)
		{
			loadLevelIntoSystem(levelManage.nextLevel(this));
			endMenu.hide();
		}
	}
	
	private void loadLevelIntoSystem(Level toLoad)
	{
		if(toLoad != null)
		{
			currLevel = toLoad;
			currLevel.switchDim(currLevel.dimensions.get(1).dims.dimNum, false, this);
			currLevel.switchDim(1, false, this);
			updateEdgeSprites();
			canvas.font("Arial", PLAIN, 14);
			dimMenu = new DimensionMenu(this, currLevel.dimLabels());
			endMenu.hide();
			endMenu.playerDied = false;
			player = new Player(this);
			player.position(currLevel.start.xLoc, currLevel.start.yLoc);
			playerFinishedLevel = false;
			centerCameraOnPlayer();
			canvas.font("Arial", PLAIN, 14);
			System.gc();
			state = GameState.IN_GAME;
			mouseX = mouse.x();
			mouseY = mouse.y();
			startTime = System.currentTimeMillis();
		}
	}

	private void resetGameCamera() 
	{
		gameCamera.setXOffset(0);
		gameCamera.setYOffset(0);
	}
	
	private void centerCameraOnPlayer()
	{
		boolean cameraMoved = false;
		if(player.centerY() < FRAME_HEIGHT/2 + FRAME_HEIGHT - currLevel.height)
		{
			gameCamera.setYOffset(currLevel.height - FRAME_HEIGHT);
		}
		if(player.centerY() > FRAME_HEIGHT/2)
		{
			gameCamera.setYOffset(0);
		}
		if(player.centerX() < FRAME_WIDTH/2)
		{
			gameCamera.setXOffset(0);
		}
		if(player.centerX() > currLevel.width - FRAME_WIDTH/2)
		{
			gameCamera.setXOffset(currLevel.width - FRAME_WIDTH);
		}
		if(player.centerX() - FRAME_WIDTH/2 >=0 && player.centerX() + FRAME_WIDTH/2 <= currLevel.width)
		{
			gameCamera.setXOffset((int)player.centerX() - FRAME_WIDTH/2);
			cameraMoved = true;
		}
		if((player.centerY() >= FRAME_HEIGHT/2 + FRAME_HEIGHT - currLevel.height) && (player.centerY() <= FRAME_HEIGHT/2))
		{
			gameCamera.setYOffset((int)-(player.centerY() - FRAME_HEIGHT/2));
			cameraMoved = true;
		}
		if(cameraMoved)
			updateEdgeSprites();
	}

	private void renderGameState() 
	{
		if(!playerFinishedLevel)
		{
			checkEndState();
			centerCameraOnPlayer();
			currLevel.render(this);
			drawUI();
			player.draw(currLevel.getCurrDims());
		}
		if(playerFinishedLevel)
		{
			resetGameCamera();
			endMenu.show();
			endMenu.draw();
		}
	}

	private void checkEndState() 
	{
		if(!player.isAlive && player.xspeed() == 0 && player.yspeed() == 0)
		{
			endMenu.show();
			playerFinishedLevel = true;
			endMenu.playerDied = true;
			canvas.font("Arial", PLAIN, 24);
		}
		Point playerLoc = new Point(player.centerX(), player.centerY());
		if(currLevel.end.isInArea(playerLoc))
		{
			endMenu.show();
			playerFinishedLevel = true;
			timeForLevel = System.currentTimeMillis() - startTime;
			currLevel.scores.addScore(timeForLevel, formatTime(timeForLevel, true), currentUser);
			levelManage.saveCurrLevel(this);
			endMenu.highScores = currLevel.scores.getTopScores();
			endMenu.endTime = formatTime(timeForLevel, true);
			canvas.font("Arial", PLAIN, 24);
		}
	}
	
	private void drawEditorWindow()
	{
		if(!editor.showHelp)
		{
			currLevel.render(this);
			if(currLevel.start != null)
			{
				Sprite start = makeSprite(getImage(Constants.IMG_DIR + "levels/start.png"));
				start.position(currLevel.start.xLoc, currLevel.start.yLoc);
				start.draw();
			}
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
				double xOffset = gameCamera.getXOffset() - levelObject.width()/2;
				double yOffset = gameCamera.getYOffset() + levelObject.height()/2;
				if(!editor.snapToGrid)
				{
					levelObject.position(mouse.x() + xOffset, mouse.y() - yOffset);
				}
				else
				{
					xOffset -= levelObject.width()/2 % editor.gridSize;
					yOffset -= levelObject.height()/2 % editor.gridSize;
					levelObject.position((mouse.x() - mouse.x() % editor.gridSize) + xOffset, (mouse.y() - mouse.y() % editor.gridSize) - yOffset);
				}
				levelObject.draw();
			}
		}
		else
		{
			editor.drawHelp();
		}

	}
	
	public void onKeyPress()
	{
		if(keyboard.isDown(keyboard.BACKSLASH)) //Go To Main Menu
		{
			onClickMainMenu();
		}
		if(keyboard.isDown(keyboard.CONTROL) && keyboard.isDown(keyboard.SHIFT) && keyboard.isDown(keyboard.F9))  //Ctrl+Shift+F9 = Clear High Scores 
		{
			levelManage.clearAllHighScores(this);
		}
		if(state == GameState.OPTIONS)
		{
			keyboard.typematicOff();
			if(currentUser.equals(NONAME))
				currentUser = keyboard.lastCharacter();
			else if(currentUser.length() < MAX_USER_LENGTH)
				currentUser += keyboard.lastCharacter();
			if(keyboard.isDown(keyboard.BACKSPACE))
			{
				if(currentUser.length() > 1)
					currentUser = currentUser.substring(0, currentUser.length()-2);
			}
		}
		// Controls for the Level Editor
		if(state == GameState.LEVEL_EDITOR)
		{
			keyboard.typematicOff();
			if(keyboard.isDown(keyboard.W)) //Create Wall object
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
					levelObject.setFilters(currLevel.currDim.dims.filters);
					levelObject.setOpacity(0.33f);
				}
			}
			if(keyboard.isDown(keyboard.S)) //Set Start Point
			{
				currLevel.start = new Point(mouse.x() + gameCamera.getXOffset(), mouse.y() - gameCamera.getYOffset());
			}
			if(keyboard.isDown(keyboard.E))
			{
				currLevel.end = new Area(new Point(mouse.x() + gameCamera.getXOffset(), mouse.y() - gameCamera.getYOffset()), 76, 140);
				currLevel.updateExit(this);
			}
			if(keyboard.isDown(keyboard.B)) //Set a background
			{
				currLevel.addBackground(this, Backgrounds.BKG2);
			}
			if(keyboard.isDown(keyboard.H)) //Create health pack
			{
				levelObject = new PickupItem(this, Pickups.HEALTH_PACK);
			}
			if(keyboard.isDown(keyboard.A)) //Create armor pack
			{
				levelObject = new PickupItem(this, Pickups.ARMOR_PACK);
			}
			if(keyboard.isDown(keyboard.K)) //Create a silver key
			{
				levelObject = new PickupItem(this, Pickups.SILVER_KEY);
			}
			if(keyboard.isDown(keyboard.M)) //Enable/Disable magnatism
			{
				editor.snapToGrid = !editor.snapToGrid;
			}
			if(keyboard.isDown(keyboard.I)) //Invert held object
			{
				if(levelObject != null)
				{
					levelObject.flipVert = !levelObject.flipVert;
				}
			}
			if(keyboard.isDown(keyboard.T))
			{
				levelObject = new SentryGun(this);
			}
			if(keyboard.isDown(keyboard.D))
			{
				levelObject = new Door(this);
			}
			if(keyboard.isDown(keyboard.G)) //Grab Object
			{
				if(!editor.grabObject)
				{
					levelObject = null;
					editor.grabObject = true;
				}
			}
			else
			{
				if(levelObject == null)
					editor.grabObject = false;
			}
			if(keyboard.isDown(keyboard.DELETE) || keyboard.isDown(keyboard.BACKSPACE)) //Delete grabbed object
			{
				if(levelObject != null)
				{
					editor.removeObject(levelObject, currLevel);
					levelObject = null;
				}
			}
			if(keyboard.isDown(keyboard.K0)) //These all switch dimension to the numbers they reference
			{
				currLevel.switchDim(0, true, this);
			}
			if(keyboard.isDown(keyboard.K1))
			{
				currLevel.switchDim(1, true, this);
			}
			if(keyboard.isDown(keyboard.K2))
			{
				currLevel.switchDim(2, true, this);
			}
			if(keyboard.isDown(keyboard.K3))
			{
				currLevel.switchDim(3, true, this);
			}
			if(keyboard.isDown(keyboard.K4))
			{
				currLevel.switchDim(4, true, this);
			}
			if(keyboard.isDown(keyboard.K5))
			{
				currLevel.switchDim(5, true, this);
			}
			if(keyboard.isDown(keyboard.K6))
			{
				currLevel.switchDim(6, true, this);
			}
			if(keyboard.isDown(keyboard.K7))
			{
				currLevel.switchDim(7, true, this);
			}
			if(keyboard.isDown(keyboard.K8))
			{
				currLevel.switchDim(8, true, this);
			}
			if(keyboard.isDown(keyboard.F1)) //Help
			{
				editor.showHelp = !editor.showHelp;
			}
			if(keyboard.isDown(keyboard.F5)) //Save
			{
				saveLevel();
			}
			if(keyboard.isDown(keyboard.F8)) //Load
			{
				loadLevel();
			}
			if(keyboard.isDown(keyboard.RIGHT))
			{
				if(FRAME_WIDTH + gameCamera.getXOffset() + CAMERA_MOVE_AMOUNT > currLevel.width)
				{
					currLevel.width = FRAME_WIDTH + gameCamera.getXOffset() + CAMERA_MOVE_AMOUNT;
				}
				gameCamera.setXOffset(gameCamera.getXOffset() + CAMERA_MOVE_AMOUNT);
			}
			if(keyboard.isDown(keyboard.LEFT))
			{
				if(gameCamera.getXOffset() - CAMERA_MOVE_AMOUNT >= 0)
				{
					gameCamera.setXOffset(gameCamera.getXOffset() - CAMERA_MOVE_AMOUNT);
				}
			}
			if(keyboard.isDown(keyboard.DOWN))
			{
				System.out.println("YOFfset: " + gameCamera.getYOffset());
				if(gameCamera.getYOffset() - CAMERA_MOVE_AMOUNT >= 0)
				{
					gameCamera.setYOffset(gameCamera.getYOffset() - CAMERA_MOVE_AMOUNT);
				}
			}
			if(keyboard.isDown(keyboard.UP))
			{
				if(FRAME_HEIGHT + gameCamera.getYOffset() + CAMERA_MOVE_AMOUNT > currLevel.height)
				{
					currLevel.height = FRAME_HEIGHT + gameCamera.getYOffset() + CAMERA_MOVE_AMOUNT;
				}
				gameCamera.setYOffset(gameCamera.getYOffset() + CAMERA_MOVE_AMOUNT);
			}
		}
		// Controls for in game
		if(state == GameState.IN_GAME)
		{
			keyboard.typematicOn();
			if(keyboard.isDown(keyboard.RIGHT, keyboard.D))
			{
				if(player.isAlive)
					player.flipHoriz = false;
				player.run();
			}
			if(keyboard.isDown(keyboard.UP, keyboard.W))
			{
				player.jump();
			}
			if(keyboard.isDown(keyboard.LEFT, keyboard.A))
			{
				if(player.isAlive)
					player.flipHoriz = true;
				player.run();
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
		if(state == GameState.IN_GAME)
		{
			if(!displayDimMenu)
			{
				displayDimMenu = true;
				mouseX = mouse.x();
				mouseY = mouse.y();
			}
		}
		if(state == GameState.LEVEL_SELECT)
		{
			String levelSelect = selector.getTagAtMouse(mouse.x(), mouse.y()).trim();
			if(keyboard.isDown(keyboard.SHIFT))
			{
				if(levelManage.attemptToRemove(levelSelect))
				{
					selector.assignBlocks(this);
				}
			}
			else
			{
				if(levelSelect != LevelSelectBlock.BLANK_LEVEL)
				{
					loadLevelIntoSystem(levelManage.loadLevel(levelSelect, this));
				}
			}
		}
	}
	
	public void onMouseReleased()
	{
		if(state == GameState.IN_GAME)
		{
			if(displayDimMenu && !playerFinishedLevel)
			{
				displayDimMenu = false;
				try
				{
					int newDim = dimMenu.calcSwitch(mouseX, mouse.x(), mouseY, mouse.y());
					currLevel.switchDim(newDim, false, this);
				}
				catch(MouseBarelyMovedException e)
				{
					System.err.println(e);
				}
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
					try
					{
						int newDim = dimMenu.calcSwitch(mouseX, mouse.x(), mouseY, mouse.y());
						currLevel.switchDim(newDim, false, this);
					}
					catch(MouseBarelyMovedException e)
					{
						System.err.println(e);
					}
				}
			}
		}
	}
	
	private void saveLevel()
	{
		//TODO Finish Level Saving Function
		if(currLevel.start == null || currLevel.end == null)
		{
			logWarning("A Level requires a start and end point.", "Needs to be playable.");
		}
		else if(currLevel.dimensions.size() == 1)
		{
			logWarning("A Level requires a dimension that is not the zeroth.", "Matter must exist.");
		}
		else
		{
			LevelSaveFile saving = new LevelSaveFile(currLevel);
			Frame parent = new Frame();
			parent.setVisible(false);
			FileDialog fd = new FileDialog(parent, "Save", FileDialog.SAVE);
	        fd.setVisible(true);
	        if (fd.getFile() == null)
	            return;
	        String file = fd.getDirectory() + fd.getFile();
			try
			{
				if(!fd.getFile().equals(levelManage.currLevel)) //If you loaded this level and want to save over it, it's fine.
					levelManage.addUserLevel(fd.getFile(), file);
				levelManage.currLevel = fd.getFile();
		        FileOutputStream fileOut = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(saving);
				out.close();
			}
			catch(IOException e)
			{
				System.err.println(e);
			} 
			catch (NameTakenException e) 
			{
				this.logWarning("That name is already taken for a level", "Name in use.");
			}
			finally
			{
				parent.dispose();
			}
		}
	}
	
	private void loadLevel()
	{
		Frame parent = new Frame();
		parent.setVisible(false);
		FileDialog fd = new FileDialog(parent, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() == null)
            return;
        String file = fd.getDirectory() + fd.getFile();
        levelManage.currLevel = fd.getFile();
        Level temp = loadLevelFromDir(file);
        if(temp != null)
        	currLevel = temp;
        parent.dispose();
	}
	
	public Level loadLevelFromDir(String fileDir)
	{
		if(new File(fileDir).exists())
        {
        	try
        	{
        		FileInputStream fileIn = new FileInputStream(fileDir);
    	        ObjectInputStream in = new ObjectInputStream(fileIn);
    	        LevelSaveFile loading = (LevelSaveFile)in.readObject();
    	        Level level = new Level();
    	        level.unpackSaveFile(this, loading);
    	        in.close();
    	        return level;
        	}
        	catch(ClassNotFoundException e)
        	{
        		System.err.println(e);
        	}
        	catch(IOException e)
        	{
        		System.err.println(e);
        	}
        }
		return null;
	}
	
	private void drawUI()
	{
		drawHealth();
		drawArmor();
		if(!playerFinishedLevel)
		{
			drawTime();
			if(displayDimMenu)
			{
				dimMenu.draw();
			}
		}
		drawInventory();
	}
	
	private void drawHealth()
	{
		if(player.health > 0)
		{
			int x = gameCamera.getXOffset() + 10;
			int y = -gameCamera.getYOffset() + 10;
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
			int x = gameCamera.getXOffset() + 10;
			int y = -gameCamera.getYOffset() + 30;
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
		String time = formatTime((System.currentTimeMillis() - startTime), false);
		canvas.putText(time, (FRAME_WIDTH / 2), TIME_OFFSET);
		Constants.clearStringBuilder(); //This clears the string builder for the next call to the string builder
	}

	private String formatTime(long time, boolean displayMillis) 
	{
		Constants.clearStringBuilder();
		StringBuilder build = Constants.strBuild;
		long elapsed = time;
		long millis = time % 1000;
		elapsed = elapsed / 1000;
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
		if(displayMillis)
		{
			build.append(":");
			build.append(String.format("%03d", millis));
		}
		return build.toString();
	}
	
	
	private void drawInventory()
	{
		player.inven.draw();
	}
	
	public Level getCurrLevel()
	{
		return currLevel;
	}
}
