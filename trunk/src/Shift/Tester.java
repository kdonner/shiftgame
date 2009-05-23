package Shift;

import java.awt.FileDialog;
import java.awt.Frame;

import ucigame.Sprite;
import ucigame.Ucigame;

public class Tester 
{
	public static Level makeLevel(Shift helper)
	{
		Frame parent = new Frame();
		parent.setVisible(false);
		FileDialog fd = new FileDialog(parent, "Open", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() == null)
            return null;
        String file = fd.getDirectory() + fd.getFile();
		parent.dispose();
		return helper.loadLevelFromDir(file);
	}
}
