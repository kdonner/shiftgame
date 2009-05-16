package Shift;

public class Area implements java.io.Serializable
{
	private static final long serialVersionUID = 3627550956646622212L;
	Point xy;
	double width, height;
	
	Area(Point xy, double width, double height)
	{
		this.xy = xy;
		this.width = width;
		this.height = height;
	}
	
	public boolean isInArea(Point compXY)
	{
		return (xy.xLoc < compXY.xLoc && xy.xLoc + width > compXY.xLoc &&
				xy.yLoc < compXY.yLoc && xy.yLoc + height > compXY.yLoc);
	}
}
