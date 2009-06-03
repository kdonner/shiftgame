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
		mainMenu.position(Shift.FRAME_WIDTH/2 - mainMenu.width() - 20, 
				Shift.FRAME_HEIGHT/2 + mainMenu.height());
		nextLevel.position(Shift.FRAME_WIDTH/2 + 20, 
				Shift.FRAME_HEIGHT/2 + nextLevel.height());
		win.position(Shift.FRAME_WIDTH/2 - win.width()/2,
				Shift.FRAME_HEIGHT/2 - win.height()/2);
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
