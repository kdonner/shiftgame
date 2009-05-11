package Shift;

import ucigame.Sprite;
import ucigame.Ucigame;

public class Tester 
{
	public static Level makeLevel(Ucigame helper)
	{
		Level lev = new Level(1);
		
		Sprite wall = helper.makeSprite(helper.getImage(Constants.IMG_DIR + "levels/wall.gif"));
		wall.position(Shift.FRAME_WIDTH / 2, Shift.FRAME_HEIGHT - wall.height());
		lev.walls.add(wall);
		
		wall = helper.makeSprite(helper.getImage(Constants.IMG_DIR + "levels/wall.gif"));
		wall.position(Shift.FRAME_WIDTH / 2 - 350, Shift.FRAME_HEIGHT - wall.height());
		lev.walls.add(wall);
		
		Sprite platform = helper.makeSprite(helper.getImage(Constants.IMG_DIR + "levels/platform.gif"));
		platform.position(Shift.FRAME_WIDTH / 2 - 200, Shift.FRAME_HEIGHT - wall.height());
		lev.walls.add(platform);
		
		wall = helper.makeSprite(helper.getImage(Constants.IMG_DIR + "levels/wall.gif"));
		wall.position(Shift.FRAME_WIDTH / 2 + 350, Shift.FRAME_HEIGHT - wall.height());
		Dimension dim = new Dimension(Dimensions.DIM1);
		dim.walls.add(wall);
		
		lev.dimensions.add(dim);
		
		dim = new Dimension(Dimensions.DIM2);
		
		lev.dimensions.add(dim);
		lev.dimensions.add(dim);
		lev.dimensions.add(dim);
		lev.dimensions.add(dim);
		lev.dimensions.add(dim);
		lev.dimensions.add(dim);
		
		return lev;
	}
}
