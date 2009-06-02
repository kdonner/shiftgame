package Shift;

import ucigame.Sprite;

public class EndLevelMenu 
{
	Shift parent;
	Sprite mainMenu, nextLevel, win;
	private boolean isShown;

	public EndLevelMenu(Shift parent)
	{
		this.parent = parent;
		isShown = true;
		win = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "menu/youwin.png"));
		mainMenu = parent.makeButton("MainMenu", parent.getImage(Constants.IMG_DIR + "menu/mainmenu.png"), 150, 48);
		nextLevel = parent.makeButton("NextLevel", parent.getImage(Constants.IMG_DIR + "menu/nextlevel.png"), 150, 48);

	}
	
	public void draw()
	{
		mainMenu.position(Shift.FRAME_WIDTH/2 - mainMenu.width() - 20 + parent.gameCamera.getXOffset(), 
				Shift.FRAME_HEIGHT/2 + mainMenu.height() - parent.gameCamera.getYOffset());
		nextLevel.position(Shift.FRAME_WIDTH/2 + 20 + parent.gameCamera.getXOffset(), 
				Shift.FRAME_HEIGHT/2 + nextLevel.height() - parent.gameCamera.getYOffset());
		win.position(Shift.FRAME_WIDTH/2 - win.width()/2 + parent.gameCamera.getXOffset(),
				Shift.FRAME_HEIGHT/2 - win.height()/2 - parent.gameCamera.getYOffset());
		mainMenu.draw();
		nextLevel.draw();
		win.draw();
	}
	
	public void hide()
	{
		if(isShown)
		{
			isShown = !isShown;
			mainMenu.hide();
			nextLevel.hide();
		}
	}
	
	public void show()
	{
		if(!isShown)
		{
			isShown = !isShown;
			mainMenu.show();
			nextLevel.show();
		}
	}

}
