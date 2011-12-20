package com.jens.typequest.loaders;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.jens.typequest.Main;

public class ImageProvider {

	public static Image getImage(String name){
		try {
			String s = Main.IMAGE_FOLDER + name + ".png";
			System.out.println(s);
			
			return new Image(s);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
