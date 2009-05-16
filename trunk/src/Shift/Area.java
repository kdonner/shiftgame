package Shift;

public class Area 
{
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
