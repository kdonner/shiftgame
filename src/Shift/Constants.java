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
	
	public static double pythagorean(double x, double y)
	{
		return Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2));
	}
	
	public static double angleFromZero(double x, double y)
	{
		double degrees = 1;
		if(x > 0 && y > 0) //Quad 1
		{
			degrees = 90 - Math.toDegrees(Math.atan(y/x));
		}
		else if(x > 0 && y < 0) //Quad 2
		{
			degrees = 90 + Math.toDegrees(Math.atan(-y/x));
		}
		else if(x < 0 && y < 0) //Quad 3
		{
			degrees = 270 - Math.toDegrees(Math.atan(-y/-x));
		}
		else if(x < 0 && y > 0) //Quad 4
		{
			degrees = 270 + Math.toDegrees(Math.atan(y/-x));
		}
		else if(x == 0)
		{
			if(y > 0)
				degrees = 0;
			else
				degrees = 180;
		}
		else if(y == 0)
		{
			if(x > 0)
				degrees = 90;
			else
				degrees = 270;
		}
		else
		{
			System.out.println("What?");
		}
		return degrees;
	}
}
