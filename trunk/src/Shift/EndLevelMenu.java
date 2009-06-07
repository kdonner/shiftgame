package Shift;

import java.util.ArrayList;

import ucigame.Sprite;

public class EndLevelMenu 
{
	Shift parent;
	Sprite mainMenu, playAgain, nextLevel, win, die;
	String endTime;
	ArrayList<String> highScores;
	private boolean isShown;
	protected boolean playerDied;

	public EndLevelMenu(Shift parent)
	{
		this.parent = parent;
		isShown = true;
		playerDied = false;
		win = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "menu/youwin.png"));
		die = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "menu/youdie.png"));
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
			die.position(win.x(), win.y());
			mainMenu.position(Shift.FRAME_WIDTH/2 - mainMenu.width() - 20, 
					win.y() + win.height());
			nextLevel.position(Shift.FRAME_WIDTH/2 + 20, 
					mainMenu.y());
			playAgain.position(nextLevel.x(), nextLevel.y());
			
			mainMenu.draw();
			
			if(!playerDied)
			{
				playAgain.hide();
				die.hide();
				win.show();
				nextLevel.show();
				win.draw();
				nextLevel.draw();
				drawHighScores(Shift.FRAME_WIDTH/2 - 80, (int)mainMenu.y() + 70);
			}
			else
			{
				win.hide();
				nextLevel.hide();
				playAgain.show();
				die.show();
				playAgain.draw();
				die.draw();
			}
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
			playAgain.hide();
			win.hide();
			die.hide();
		}
	}
	
	public void show()
	{
		if(!isShown)
		{
			isShown = !isShown;
			mainMenu.show();
			nextLevel.show();
			playAgain.show();
			win.show();
			die.show();
		}
	}

}
