package Shift;

import java.util.ArrayList;

import ucigame.Sprite;

public class EndLevelMenu 
{
	Shift parent;
	Sprite mainMenu, playAgain, nextLevel, win;
	String endTime;
	ArrayList<String> highScores;
	private boolean isShown;

	public EndLevelMenu(Shift parent)
	{
		this.parent = parent;
		isShown = true;
		win = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "menu/youwin.png"));
		mainMenu = parent.makeButton("MainMenu", parent.getImage(Constants.IMG_DIR + "menu/mainmenu.png"), 150, 48);
		playAgain = parent.makeButton("PlayAgain", parent.getImage(Constants.IMG_DIR + "menu/playagain.png"), 150, 48);
		nextLevel = parent.makeButton("NextLevel", parent.getImage(Constants.IMG_DIR + "menu/nextlevel.png"), 150, 48);
		highScores = new ArrayList<String>();
		endTime = "";
	}
	
	public void draw()
	{
		if(isShown)
		{
			win.position(Shift.FRAME_WIDTH/2 - win.width()/2,
					20);
			mainMenu.position(Shift.FRAME_WIDTH/2 - mainMenu.width() - 20, 
					win.y() + win.height());
			nextLevel.position(Shift.FRAME_WIDTH/2 + 20, 
					mainMenu.y());
			
			mainMenu.draw();
			nextLevel.draw();
			win.draw();
			drawHighScores(Shift.FRAME_WIDTH/2 - 80, (int)mainMenu.y() + 70);
		}
	}
	
	private void drawHighScores(int xPos, int yOff)
	{
		parent.canvas.putText("Your Time: " + endTime, xPos - 20, yOff);
		yOff += 40;
		parent.canvas.putText("Best Times", xPos + 20, yOff);
		for(String s : highScores)
		{
			yOff += 40;
			parent.canvas.putText(s, xPos, yOff);
		}
	}
	
	public void hide()
	{
		if(isShown)
		{
			isShown = !isShown;
			mainMenu.hide();
			nextLevel.hide();
			win.hide();
		}
	}
	
	public void show()
	{
		if(!isShown)
		{
			isShown = !isShown;
			mainMenu.show();
			nextLevel.show();
			win.show();
		}
	}

}
