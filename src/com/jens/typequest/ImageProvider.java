package com.jens.typequest;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageProvider {

	public static Image getImage(String name){
		try {
			return new Image(Main.IMAGE_FOLDER + name + ".png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
