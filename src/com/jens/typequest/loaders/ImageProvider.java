package com.jens.typequest.loaders;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.jens.typequest.Main;

public class ImageProvider {

	static Map<String, Image> cache = new HashMap<String, Image>();
	
	public static Image getImage(String name){
		if(cache.containsKey(name)){
			return cache.get(name);
		}
		try {
			String s = Main.IMAGE_FOLDER + name + ".png";
			Image image = new Image(s);
			cache.put(name, image);
			return image;
		} catch (SlickException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading file: " + name);
		}
	}

	public static Image[] getImages(String[] strings) {
		Image[] ret = new Image[strings.length];
		
		for (int i = 0; i < strings.length; i++) {
			ret[i] = getImage(strings[i]);
		}
		
		return ret;
	}
}
