package Shift;

public class Point implements java.io.Serializable
{
	private static final long serialVersionUID = 3627550956646622212L;
	double xLoc, yLoc;
	
	public Point(double xLoc, double yLoc)
	{
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	
	public String toString()
	{
		return "X: " + xLoc + " Y: " + yLoc;
	}
}
