package Shift;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;

public class LevelManager 
{
	private static LevelManager instance;
	private Shift parent;
	private String currLevel;
	protected boolean loadingLevel;
	Hashtable<String, String> manager; //First String if the level name, second is the locations
	LinkedList<String> orderedKeys;
	
	private LevelManager(Shift parent)
	{
		this.parent = parent;
		manager = new Hashtable<String, String>();
		orderedKeys = new LinkedList<String>();
		mapDefaultLevels();
		currLevel = null;
	}
	
	public static LevelManager getInstance(Shift par)
	{
		if(instance == null)
			instance = new LevelManager(par);
		return instance;
	}
	
	private void mapDefaultLevels()
	{
		try
		{
			mapLevel1();
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
	
	public Level loadLevel(String levelTag)
	{
		loadingLevel = true;
		Level retrieved = loadLevelFromDir(manager.get(levelTag));
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
	
	protected Level nextLevel()
	{
		loadingLevel = true;
		System.out.println("Next Called");
		if(currLevel == null)
		{
			loadLevel("1.1");
		}
		else
		{
			ListIterator<String> keys = orderedKeys.listIterator();
			while(keys.hasNext())
			{
				if(keys.next().equals(currLevel))
				{
					if(keys.hasNext())
						return loadLevel(keys.next());
					else
						return null;
				}
			}
		}
		loadingLevel = false;
		return null;
	}
	
	private Level loadLevelFromDir(String fileDir)
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
