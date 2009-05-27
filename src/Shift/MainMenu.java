package Shift;

import ucigame.Sprite;

public class MainMenu 
{
	Shift parent;
	Sprite newGame, loadGame, levelEdit, options, quit, bkg;
	private boolean isShown;
	
	public MainMenu(Shift parent)
	{
		this.parent = parent;
		isShown = true;
		bkg = parent.makeSprite(parent.getImage(Constants.IMG_DIR + "levels/bkg/Background2.png"));
		newGame = parent.makeButton("NewGame", parent.getImage(Constants.IMG_DIR + "menu/newgame.png"), 300, 72);
		loadGame = parent.makeButton("LoadGame", parent.getImage(Constants.IMG_DIR + "menu/loadgame.png"), 300, 72);
		levelEdit = parent.makeButton("LevelEdit", parent.getImage(Constants.IMG_DIR + "menu/leveledit.png"), 300, 72);
		options = parent.makeButton("Options", parent.getImage(Constants.IMG_DIR + "menu/options.png"), 300, 72);
		quit = parent.makeButton("Quit", parent.getImage(Constants.IMG_DIR + "menu/quit.png"), 300, 72);
		int xLoc = Shift.FRAME_WIDTH / 2 - newGame.width()/2;
		int yLoc = Shift.FRAME_HEIGHT / 2 - 2 * newGame.height();
		newGame.position(xLoc, yLoc);
		yLoc += newGame.height();
		loadGame.position(xLoc, yLoc);
		yLoc += newGame.height();
		levelEdit.position(xLoc, yLoc);
		yLoc += newGame.height();
		options.position(xLoc, yLoc);
		yLoc += newGame.height();
		quit.position(xLoc, yLoc);
	}
	
	public void draw()
	{
		bkg.draw();
		newGame.draw();
		loadGame.draw();
		levelEdit.draw();
		options.draw();
		quit.draw();
	}
	
	public void hide()
	{
		if(isShown)
		{
			isShown = false;
			bkg.hide();
			newGame.hide();
			loadGame.hide();
			levelEdit.hide();
			options.hide();
			quit.hide();
		}
	}
	
	public void show()
	{
		if(!isShown)
		{
			isShown = true;
			bkg.show();
			newGame.show();
			loadGame.show();
			levelEdit.show();
			options.show();
			quit.show();
		}
	}
	
	public boolean isVisible()
	{
		return isShown;
	}

}
