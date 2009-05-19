package Shift;

import ucigame.Sprite;
import ucigame.Ucigame;

public class Tester 
{
	public static Level makeLevel(Shift helper)
	{
		Level lev = new Level();
		
		lev.start = new Point(20, Shift.FRAME_HEIGHT - 80);
		
		Wall wall = new Wall(helper, Walls.WALL1);
		wall.position(Shift.FRAME_WIDTH / 2, Shift.FRAME_HEIGHT - wall.height());
		lev.addObject(wall);
		
		wall = new Wall(helper, Walls.WALL1);
		wall.position(Shift.FRAME_WIDTH / 2 - 350, Shift.FRAME_HEIGHT - wall.height());
		lev.addObject(wall);
		
		wall = new Wall(helper, Walls.PLATFORM1);
		wall.position(Shift.FRAME_WIDTH / 2 - 200, Shift.FRAME_HEIGHT - 100);
		lev.addObject(wall);
		
		wall = new Wall(helper, Walls.WALL1);
		wall.position(Shift.FRAME_WIDTH / 2 + 350, Shift.FRAME_HEIGHT - wall.height());
		Dimension dim = new Dimension(Dimensions.DIM1);
		dim.addObject(wall);
		
		lev.dimensions.add(dim);
		
		dim = new Dimension(Dimensions.DIM2);
		
		lev.dimensions.add(dim);
//		lev.background = helper.getImage(Constants.IMG_DIR + "levels/bkg/Background2.png");
		
		return lev;
	}
}
