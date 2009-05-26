package ucigame;

public class GameCamera 
{
	private static GameCamera instance;
	int xOffset, yOffset;
	
	public int getXOffset() {
		return xOffset;
	}

	public void setXOffset(int offset) {
		xOffset = offset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public void setYOffset(int offset) {
		yOffset = offset;
	}

	private GameCamera()
	{
		xOffset = 0;
		yOffset = 0;
	}
	
	public static GameCamera getInstance()
	{
		if(instance == null)
			instance = new GameCamera();
		return instance;
	}
}
