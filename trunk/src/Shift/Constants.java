package Shift;

public class Constants 
{
	public static final double GRAVITY = 9.8;
	//TODO Check to make sure these directories are correct
	//If you are receiving img not found or audio not found errors this might be why
	public static String IMG_DIR = "../img/";
	public static String AUDIO_DIR = "../audio/";
	public static StringBuilder strBuild = new StringBuilder();
	
	public static void clearStringBuilder()
	{
		strBuild.delete(0, strBuild.length());
	}
}
