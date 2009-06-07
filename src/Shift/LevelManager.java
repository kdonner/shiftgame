package Shift;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

public class LevelManager implements java.io.Serializable
{
	private static LevelManager instance;
	private static final String saveDir = "levelmanager.man";
	private int customIndex;
	static String currLevel;
	protected static boolean loadingLevel;
	Hashtable<String, String> manager; //First String if the level name, second is the locations
	ArrayList<String> orderedKeys;
	
	private LevelManager()
	{
		if(new File(saveDir).exists())
		{
			try
			{
				loadInstance();
			}
			catch(IOException e)
			{
				createNewManager();
			}
			catch(ClassNotFoundException e)
			{
				createNewManager();
			}
		}
		else
		{
			createNewManager();
		}
	}

	private void createNewManager() 
	{
		manager = new Hashtable<String, String>();
		orderedKeys = new ArrayList<String>();
		mapDefaultLevels();
		currLevel = null;
		System.out.println("Making new Manager");
	}
	
	private void loadInstance() throws IOException, ClassNotFoundException
	{
		FileInputStream fileIn = new FileInputStream(saveDir);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		instance = (LevelManager)in.readObject();
		manager = instance.manager;
		orderedKeys = instance.orderedKeys;
		if(manager == null || orderedKeys == null)
		{
			createNewManager();
		}
		in.close();
	}
	
	public static void saveInstance()
	{
		if(instance != null)
		{
			try
			{
				currLevel = null;
				loadingLevel = false;
		        FileOutputStream fileOut = new FileOutputStream(saveDir);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(instance);
				out.close();
			}
			catch(IOException e)
			{
				System.err.println(e);
			}
		}
	}
	
	public static LevelManager getInstance()
	{
		if(instance == null)
			instance = new LevelManager();
		return instance;
	}
	
	private void mapDefaultLevels()
	{
		try
		{
			mapLevel1();
			mapLevel2();
			customIndex = orderedKeys.size(); //This needs to come after all default levels have been entered
		}
		catch(NameTakenException e)
		{
			System.err.println(e + "\n" + "You did something wrong!!!");
		}
	}
	
	private void mapLevel1() throws NameTakenException
	{
		addLevel("1.1", Constants.LEVEL_DIR + "level1.1");
		addLevel("1.2", Constants.LEVEL_DIR + "level1.2");
		addLevel("1.3", Constants.LEVEL_DIR + "level1.3");
		addLevel("1.4", Constants.LEVEL_DIR + "level1.4");
		addLevel("1.5", Constants.LEVEL_DIR + "level1.5");
	}
	
	private void mapLevel2() throws NameTakenException
	{
		addLevel("2.0", Constants.LEVEL_DIR + "Base Jump");
	}
	
	public void addUserLevel(String levelName, String dir) throws NameTakenException
	{
		addLevel(levelName, dir);
	}
	
	public void addLevel(String tag, String dir) throws NameTakenException
	{
		if(manager.containsKey(tag))
		{
			throw new NameTakenException();
		}
		else
		{
			manager.put(tag, dir);
			orderedKeys.add(tag);
		}
	}
	
	protected void clearAllHighScores(Shift parent)
	{
		loadingLevel = true;
		for(String key : orderedKeys)
		{
			Level toClear = loadLevel(key, parent);
			if(toClear != null)
			{
				toClear.scores.clear();
			}
		}
		currLevel = "";
		loadingLevel = false;
	}
	
	public Level loadLevel(String levelTag, Shift parent)
	{
		loadingLevel = true;
		Level retrieved = loadLevelFromDir(manager.get(levelTag), parent);
		if(retrieved == null)
		{
			parent.logWarning("Couldn't locate Level: " + levelTag + " at Dir: " + manager.get(levelTag));
			loadingLevel = false;
			return null;
		}
		currLevel = levelTag;
		loadingLevel = false;
		return retrieved;
	}
	
	protected Level nextLevel(Shift parent)
	{
		loadingLevel = true;
		System.out.println("Next Called");
		if(currLevel == null)
		{
			loadLevel("1.1", parent);
		}
		else
		{
			ListIterator<String> keys = orderedKeys.listIterator();
			while(keys.hasNext())
			{
				if(keys.next().equals(currLevel))
				{
					if(keys.hasNext())
						return loadLevel(keys.next(), parent);
					else
						return null;
				}
			}
		}
		loadingLevel = false;
		return null;
	}
	
	public void saveCurrLevel(Shift parent)
	{
		try
		{
	        FileOutputStream fileOut = new FileOutputStream(manager.get(currLevel));
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(new LevelSaveFile(parent.currLevel));
			out.close();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
	
	private Level loadLevelFromDir(String fileDir, Shift parent)
	{
		if(new File(fileDir).exists())
        {
        	try
        	{
        		FileInputStream fileIn = new FileInputStream(fileDir);
    	        ObjectInputStream in = new ObjectInputStream(fileIn);
    	        LevelSaveFile loading = (LevelSaveFile)in.readObject();
    	        Level level = new Level();
    	        level.unpackSaveFile(parent, loading);
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
}
